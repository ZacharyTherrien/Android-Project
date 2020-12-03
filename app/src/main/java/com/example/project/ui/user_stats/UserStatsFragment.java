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
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;

public class UserStatsFragment extends Fragment {

    private TextView usernameTextView;
    private TextView taskChartTextView;
    private TextView topTasksTextView;
    private TextView taskName1TextView;
    private TextView taskHours1TextView;
    private TextView taskName2TextView;
    private TextView taskHours2TextView;
    private TextView taskName3TextView;
    private TextView taskHours3TextView;
    private TextView taskName4TextView;
    private TextView taskHours4TextView;
    private TextView taskName5TextView;
    private TextView taskHours5TextView;

    UserStatsAdapter adapter;
    private RecyclerView topTasksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_stats, container, false);



        // Set views by their ID in fragment
        usernameTextView = root.findViewById(R.id.username_textView);
        taskChartTextView = root.findViewById(R.id.taskChart_textView);
        topTasksTextView = root.findViewById(R.id.topTasks_textView);

        taskName1TextView = root.findViewById(R.id.taskName1_textView);
        taskHours1TextView = root.findViewById(R.id.taskTime1_textView);

        taskName2TextView = root.findViewById(R.id.taskName2_textView);
        taskHours2TextView = root.findViewById(R.id.taskTime2_textView);

        taskName3TextView = root.findViewById(R.id.taskName3_textView);
        taskHours3TextView = root.findViewById(R.id.taskTime3_textView);

        taskName4TextView = root.findViewById(R.id.taskName4_textView);
        taskHours4TextView = root.findViewById(R.id.taskTime4_textView);

        taskName5TextView = root.findViewById(R.id.taskName5_textView);
        taskHours5TextView = root.findViewById(R.id.taskTime5_textView);

        // Array for TextViews holding task NAMES
        TextView[] taskNameTextViews = {taskName1TextView, taskName2TextView, taskName3TextView, taskName4TextView, taskName5TextView};
        // Array for TextViews holding task TIME
        TextView[] taskTimeTextViews = {taskHours1TextView, taskHours2TextView, taskHours3TextView, taskHours4TextView, taskHours5TextView};

        // Declaring hard-coded tasks
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        Task task4 = new Task();
        Task task5 = new Task();
        // Store hard-coded tasks in an array
        Task[] taskArr = {task1, task2, task3, task4, task5};

        // Set on click listeners for every TextView
        for (int i = 0; i < taskArr.length; i++) {

            // Task NAME OnClickListener
            taskNameTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "TASK NAMES", Toast.LENGTH_LONG).show();
                }
            });

            // Task TIME OnClickListener
            taskTimeTextViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "TASK TIME", Toast.LENGTH_LONG).show();
                }
            });
        }

        return root;
    }
}
