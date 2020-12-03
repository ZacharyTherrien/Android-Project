package com.example.project.ui.user_stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;

import java.util.ArrayList;
import java.util.List;

public class UserStatsFragment extends Fragment {

    UserStatsAdapter adapter;
    private RecyclerView topTasksRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_stats, container, false);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("0001", "0001", "Jogging", "Run around the block for some exercise."));
        tasks.add(new Task("0002", "0001", "Cooking", "Cook dinner for the kids."));
        tasks.add(new Task("0003", "0001", "Cleaning", "Clean the house before inviting guests."));
        tasks.add(new Task("0004", "0001", "Gaming", "Relax and play Switch games."));
        tasks.add(new Task("0005", "0001", "Shopping", "Get groceries while they're on sale."));

        topTasksRecyclerView = root.findViewById(R.id.topTasks_recylerView);

        adapter = new UserStatsAdapter(tasks);
        topTasksRecyclerView.setAdapter(adapter);
        topTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
