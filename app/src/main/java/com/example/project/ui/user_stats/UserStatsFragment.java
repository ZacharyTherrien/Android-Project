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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;
import com.example.project.ui.home.HomeActivity;
import com.example.project.ui.home.HomeFragment;
import com.example.project.ui.util.TimeSignature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserStatsFragment extends Fragment {

    // VIEWS
    private View root;
    private UserStatsAdapter adapter;
    private UserStatsActivity activity;
    private RecyclerView topTasksRecyclerView;
    private TextView totalTimeTextView;
    private FloatingActionButton userBackBtn;

    // FIELDS
    List<Task> tasks = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Set views by their IDs
        root = inflater.inflate(R.layout.fragment_user_stats, container, false);
        activity = (UserStatsActivity) getActivity();
        activity.fragment = this;
        topTasksRecyclerView = root.findViewById(R.id.topTasks_recylerView);
        totalTimeTextView = root.findViewById(R.id.totalTime_textView);
        userBackBtn = root.findViewById(R.id.user_back_fbtn);

        // Back button on click listener
        userBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.goBackToHome(root);
            }
        });

        adapter = new UserStatsAdapter(tasks);
        topTasksRecyclerView.setAdapter(adapter);
        topTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    // Loop through all tasks (called in fragment)
    // Get sum time time from all tasks
    // Write into totalTimeTextView
    public void setTotalTime(List<Task> list) {
        int sum = 0;
        for (Task t: list) {
            sum += t.getTotalTime();
        }
        totalTimeTextView.setText("Total Time: " + TimeSignature.secondsToTimeDetailed(sum));
    }

    // Loop through all tasks (called in fragment)
    // Get top 5 tasks with most total time
    // Add top 5 tasks into tasks field
    public void setTop5Tasks(List<Task> taskList) {
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task2.getTotalTime() > task1.getTotalTime())
                    return 1;
                else
                    return -1;
            }
        });

        // Adding top 5 tasks from sorted list
        // If <5 tasks, add null spot
        int top = 5;
        int max = Math.min(taskList.size(), top);
        for (int i = 0; i < max; i++) {
            if (taskList.get(i) == null)
                tasks.add(null);
            else
                tasks.add(taskList.get(i));
        }
    }
}
