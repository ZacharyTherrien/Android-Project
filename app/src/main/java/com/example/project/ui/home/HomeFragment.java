package com.example.project.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.login.LoginDialogFragment;
import com.example.project.login.LoginManager;
import com.example.project.login.OnLoginListener;
import com.example.project.model.Task;
import com.example.project.ui.NoteApplication;
import com.example.project.ui.user_stats.UserStatsAdapter;
import com.example.project.ui.user_stats.UserStatsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //Fields
    private UserStatsAdapter adapter;
    private List<Task> tasks;

    //Views
    FloatingActionButton addFloatingButton;
    RecyclerView tasksRecycleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

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

        adapter = new UserStatsAdapter(tasks);
        tasksRecycleView.setAdapter(adapter);
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        addFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.create();
            }
        });


        return root;
    }


}