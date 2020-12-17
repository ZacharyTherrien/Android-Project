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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserStatsFragment extends Fragment {

    private View root;
    private UserStatsAdapter adapter;
    private UserStatsActivity activity;
    private RecyclerView topTasksRecyclerView;
    private TextView totalTimeTextView;
    private FloatingActionButton userBackBtn;
    List<Task> tasks = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_stats, container, false);

        activity = (UserStatsActivity) getActivity();
        activity.fragment = this;

        tasks.add(new Task("0001", "0001", "Jogging", "Run around the block for some exercise."));
        tasks.add(new Task("0002", "0001", "Cooking", "Cook dinner for the kids."));
        tasks.add(new Task("0003", "0001", "Cleaning", "Clean the house before inviting guests."));
        tasks.add(new Task("0004", "0001", "Gaming", "Relax and play Switch games."));
        tasks.add(new Task("0005", "0001", "Shopping", "Get groceries while they're on sale."));

        topTasksRecyclerView = root.findViewById(R.id.topTasks_recylerView);
        totalTimeTextView = root.findViewById(R.id.totalTime_textView);
        userBackBtn = root.findViewById(R.id.user_back_fbtn);

        int time = getTotalTime(tasks);
        int hr = time / 3600;
        int min = (time % 3600) / 60;
        int sec = time % 60;
        String totalTime = String.format("%02d:%02d:%02d", hr, min, sec);
        totalTimeTextView.setText("Total Time: " + totalTime);

        // BACK BUTTON
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

    public int getTotalTime(List<Task> list) {
        int sum = 0;
        for (Task t: list) {
            sum += t.getTotalTime();
        }
        return sum;
    }
}
