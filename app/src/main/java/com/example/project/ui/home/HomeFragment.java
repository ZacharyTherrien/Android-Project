package com.example.project.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.example.project.ui.TaskApplication;
import com.example.project.ui.task_overview.TaskOverviewActivity;
import com.example.project.ui.task_overview.TaskOverviewFragment;
import com.example.project.ui.user_stats.UserStatsActivity;
import com.example.project.ui.user_stats.UserStatsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
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
        tasks.add(new Task("0001", "0001", "Jogging", "Run around the block for some exercise."));
        tasks.add(new Task("0002", "0001", "Cooking", "Cook dinner for the kids."));
        tasks.add(new Task("0003", "0001", "Cleaning", "Clean the house before inviting guests."));
        tasks.add(new Task("0004", "0001", "Gaming", "Relax and play Switch games."));
        tasks.add(new Task("0005", "0001", "Shopping", "Get groceries while they're on sale."));
        tasks.add(new Task("0006", "0001", "Jogging", "Run around the block for some exercise."));
        tasks.add(new Task("0007", "0001", "Cooking", "Cook dinner for the kids."));
        tasks.add(new Task("0008", "0001", "Cleaning", "Clean the house before inviting guests."));
        tasks.add(new Task("0009", "0001", "Gaming", "Relax and play Switch games."));
        tasks.add(new Task("00010", "0001", "Shopping", "Get groceries while they're on sale."));

        adapter = new HomeAdapter(tasks);
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        addFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "yeet", Toast.LENGTH_LONG).show();
                createTask();
            }
        });

        final LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        TaskApplication taskApplication = (TaskApplication) getActivity().getApplication();
        LoginManager loginManager = taskApplication.getLoginManager();
        loginDialogFragment.show(getChildFragmentManager(), "Login");
        loginManager.setOnLoginListener(new OnLoginListener() {
            @Override
            public void onLogin(String UUID) {
                loginDialogFragment.dismiss();
                tasksRecycleView.setAdapter(adapter);
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


        return root;
    }

    private void createTask() {
        Task task = new Task();
        task.setUuid(UUID.randomUUID().toString());
        tasks.add(task);
        editTask(task);
    }

    private void editTask(Task task){
        this.activity.sendTaskToTaskPageAndBack(this.root, task);
    }

    public void saveTask(Task task){
        for (int i = 0; i < this.tasks.size(); i++) {
            if (this.tasks.get(i).getUuid().equals(task.getUuid())){
                this.tasks.set(i, task);
                this.adapter.update();
                break;
            }
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