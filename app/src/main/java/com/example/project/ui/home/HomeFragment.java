package com.example.project.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.login.LoginDialogFragment;
import com.example.project.login.LoginManager;
import com.example.project.login.OnLoginListener;
import com.example.project.model.Entry;
import com.example.project.model.Task;
import com.example.project.networking.HttpRequest;
import com.example.project.networking.HttpResponse;
import com.example.project.ui.TaskApplication;
import com.example.project.ui.task_overview.TaskOverviewActivity;
import com.example.project.ui.task_overview.TaskOverviewFragment;
import com.example.project.ui.user_stats.UserStatsActivity;
import com.example.project.ui.user_stats.UserStatsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HomeFragment extends Fragment {

    //Fields
    private View root;
    private HomeAdapter adapter;
    private HomeActivity activity;
    private List<Task> tasks;

    //Views
    FloatingActionButton addFloatingButton;
    RecyclerView tasksRecycleView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        activity = (HomeActivity) getActivity();
        activity.fragment = this;

        addFloatingButton = root.findViewById(R.id.add_FloatingActionButton);
        tasksRecycleView = root.findViewById(R.id.tasks_RecyclerView);

        tasks = new ArrayList<>();
        adapter = new HomeAdapter(tasks);

        tasksRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        addFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask();
            }
        });

        final LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        TaskApplication taskApplication = (TaskApplication) getActivity().getApplication();
        LoginManager loginManager = taskApplication.getLoginManager();
        loginDialogFragment.show(getChildFragmentManager(), "Login");
        if(loginManager.isLoggedIn()){
            loginDialogFragment.dismiss();
            setAdapter((ArrayList) tasks);
        }
        else {
            loginManager.setOnLoginListener(new OnLoginListener() {
                @Override
                public void onLogin(String UUID) {
                    loginDialogFragment.dismiss();
                    setAdapter((ArrayList) tasks);
                }

                @Override
                public void onLogout() {

                }

                @Override
                public void onRegister(String UUID) {

                }

                @Override
                public void onError(String message) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Error when login.")
                            .setMessage(message)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            });
        }
        return root;
    }

    private void setAdapter(ArrayList tasks){
        //Build the string to get the user's tasks.
        TaskApplication taskApplication = (TaskApplication) getActivity().getApplication();
        String urlTasks = String.format("http://%s:%s/user/%s/tasks",
                taskApplication.getHost(), taskApplication.getPort(), taskApplication.getLoginManager().getUUID());
        try{
            //Get the tasks as a response after making a request for them.
            HttpResponse response = new HttpRequest(urlTasks).perform();
            String tasksJson = response.getResponseBody();
            Task[] tasksResponse = Task.parseArray(tasksJson);
            //Add the tasks to the adapter.
            tasks.addAll(Arrays.asList(tasksResponse));
            tasksRecycleView.setAdapter(adapter);
        }
        catch(IOException e){
            Log.d("Get user tasks error: ", e.getMessage());
        }
    }

    private void createTask() {
        Task task = new Task();
        String uuid = serverCreateTask(task);
        task.setUuid(uuid);
        tasks.add(task);
        editTask(task);
    }

    private String serverCreateTask(Task task){
        //First add the task to the server.
        TaskApplication taskApplication = (TaskApplication) getActivity().getApplication();
        String urlTask = String.format("http://%s:%s/task", taskApplication.getHost(), taskApplication.getPort());
        String uuid = null;
        try{
            //Before creating the note, kae sure to set which user created it.
            task.setUser_uuid(taskApplication.getLoginManager().getUUID());
            //Creating this sends a request, creating the task one the server, and gets back the response from the server.
            HttpResponse response = new HttpRequest(urlTask)
                    .method(HttpRequest.Method.POST)
                    .contentType("application/json")
                    .body(task.format())
                    .perform();
            //In the responses header, it will contain the uuid of the newly added note.
            String[] header = response.getHeaders().get("Location").get(0).split("/");
            uuid = header[header.length - 1];
            //Now add the user who created the task.
            String urlUser = String.format("http://%s:%s/task", taskApplication.getHost(), taskApplication.getPort());
        }
        catch(IOException e){
            Log.d("Task from Sever error: ", e.getMessage());
        }
        //Afte the  task has been added, get the task's uuid from the response and return it.
        return uuid;
    }

    private void editTask(Task task){
        this.activity.sendTaskToTaskPageAndBack(this.root, task);
    }

    public void saveTask(Task task){
        //Find the task by uuid and then update it in the adapter.
        for (int i = 0; i < this.tasks.size(); i++) {
            if (this.tasks.get(i).getUuid().equals(task.getUuid())){
                this.tasks.set(i, task);
                this.adapter.update();
                break;
            }
        }
        //Now update the task on the server.
        serverUpdateTask(task);
    }

    public void serverUpdateTask(Task task){
        //First build the url to send the task to.
        TaskApplication taskApplication = (TaskApplication) getActivity().getApplication();
        String urlTask = String.format("http://%s:%s/task/%s", taskApplication.getHost(), taskApplication.getPort(), task.getUuid());
        try{
            //Now create the request to update the note and send it.
            HttpResponse response = new HttpRequest(urlTask)
                    .method(HttpRequest.Method.PUT)
                    .contentType("application/json")
                    .body(task.format())
                    .perform();
        }
        catch(IOException e){
            Log.d("Task Update error: ", e.getMessage());
        }
    }

    public static class ViewHolderOnClickCallback{
        private final HomeFragment fragment;

        public ViewHolderOnClickCallback(HomeFragment fragment){
            this.fragment = fragment;
        }

        public void execute(Task task){
            this.fragment.editTask(task);
        }
    }
}