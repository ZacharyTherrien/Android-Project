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

    protected UserStatsFragment fragment;

    private UserStatsFragment userStatsFragment;

    // ADDED FOR PIE CHART
    AnyChartView anyChartView;
    List<Task> tasks;
    List<String> names;
    List<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userStatsFragment = (UserStatsFragment) getSupportFragmentManager().findFragmentById(R.id.task_overview_fragment);

        tasks = new ArrayList<>();
        names = new ArrayList<>();
        times = new ArrayList<>();

        // Receive list of tasks
        Intent intent = getIntent();
        List<Task> taskList = intent.getParcelableArrayListExtra("Tasks");

        tasks = taskList;
        for (int i = 0; i < taskList.size(); i++) {
            names.add(taskList.get(i).getName());
            times.add(taskList.get(i).getTotalTime());
        }

        fragment.setTop5Tasks(tasks);
        fragment.setTotalTime(tasks);

        // ADDED FOR PIE CHART
        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart(userStatsFragment);
    }

    // ADDED FOR PIE CHART
    public void setupPieChart(UserStatsFragment frg) {
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

        for (int i = 0; i < names.size(); i++) {
            dataEntries.add(new ValueDataEntry(tasks.get(i).getName(), tasks.get(i).getTotalTime()));
        }

        pie.data(dataEntries);
        //pie.title("Earnings");
        anyChartView.setChart(pie);
    }

    // ADDED FOR PIE CHART
    // HOLY VIDEO: https://www.youtube.com/watch?v=qWBA2ikLJjU

    protected void goBackToHome(View v){
        Activity activity = (Activity) v.getContext();
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivityForResult(intent, 1);
    }

    protected List<Task> getTasks() {
        return tasks;
    }
}
