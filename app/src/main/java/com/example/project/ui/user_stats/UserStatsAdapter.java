package com.example.project.ui.user_stats;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Task;
import com.example.project.ui.task_entry.TaskEntryActivity;
import com.example.project.ui.task_overview.TaskOverviewActivity;
import com.example.project.ui.util.TimeSignature;

import java.util.List;

public class UserStatsAdapter extends RecyclerView.Adapter<UserStatsAdapter.TaskViewHolder> {

    // FIELDS
    private List<Task> tasks;

    public UserStatsAdapter(List<Task> tasks) { this.tasks = tasks; }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_stats_task, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.set(tasks.get(position), position);
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    // TOP 5 TOTAL TASKS RECYCLER VIEW
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        // VIEWS
        private final TextView taskNameTextView;
        private final TextView taskTimeTextView;

        // FIELDS
        private int position;
        private Task task;

        public TaskViewHolder(@NonNull final View root) {
            super(root);
            taskNameTextView = root.findViewById(R.id.taskName_textView);
            taskTimeTextView = root.findViewById(R.id.taskTime_textView);

            // On click listener for every task in recycler view
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TASK ENTRY ----------------------------------------
                    // Get an instance of activity from the root's context
                    // Every instance of root will hold a context
                    Activity act = (Activity) root.getContext();
                    // Create an intent
                    // This intent holds activity and the activity it wants to do:
                    // TaskOverviewActivity
                    Intent intent = new Intent(act, TaskOverviewActivity.class);
                    // Extra tendency
                    // Set the intent's extra (map-like object) to the task
                    // So that it can be set upon entering the TaskOverviewActivity
                    intent.putExtra("Task", task);
                    // Start the TaskOverviewActivity by providing it an intent
                    act.startActivityForResult(intent, 1);
                }
            });
        }

        // Sets textviews for every task
        // Called in onBindViewHolder
        public void set(Task task, int position) {
            this.task = task;
            this.position = position;
            taskNameTextView.setText(task.getName());

            int time = task.getTotalTime();
            taskTimeTextView.setText(TimeSignature.secondsToTime(time));
        }
    }

    public void create() {
        Task t = new Task();
        this.tasks.add(t);
        this.notifyItemInserted(tasks.size());
        this.notifyDataSetChanged();
    }
}
