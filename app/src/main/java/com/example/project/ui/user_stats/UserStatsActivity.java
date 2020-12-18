package com.example.project.ui.user_stats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.project.R;
import com.example.project.model.Task;
import com.example.project.ui.home.HomeActivity;
import com.example.project.ui.home.HomeFragment;
import com.example.project.ui.task_overview.TaskOverviewActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserStatsActivity extends AppCompatActivity {

    // VIEWS
    AnyChartView anyChartView;

    // FIELDS
    protected UserStatsFragment fragment;
    private UserStatsFragment userStatsFragment;
    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set views by their IDs
        userStatsFragment = (UserStatsFragment) getSupportFragmentManager().findFragmentById(R.id.task_overview_fragment);

        // Receive list of tasks
        // Populate tasks field from intent
        tasks = new ArrayList<>();
        Intent intent = getIntent();
        List<Task> taskList = intent.getParcelableArrayListExtra("Tasks");
        tasks = taskList;

        // Calls fragment to set recycler view and total time textview
        fragment.setTop5Tasks(tasks);
        fragment.setTotalTime(tasks);

        // Enters data (task names & times) into pie chart
        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart();
    }

    // Enters data (task names & times) into pie chart
    public void setupPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        // Sort times in decreasing order
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task2.getTotalTime() > task1.getTotalTime())
                    return 1;
                else
                    return -1;
            }
        });

        // Entering tasks into pie chart (no limit)
        for (int i = 0; i < tasks.size(); i++) {
            dataEntries.add(new ValueDataEntry(tasks.get(i).getName(), tasks.get(i).getTotalTime()));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }

    // Called by fragment's back button on click listener
    // Takes you tome Home activity
    protected void goBackToHome(View v){
        Activity activity = (Activity) v.getContext();
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivityForResult(intent, 1);
    }
}
