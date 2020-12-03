package com.example.project.ui.user_stats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class UserStatsActivity extends AppCompatActivity {

    private UserStatsFragment userStatsFragment;

    // ADDED FOR PIE CHART
    AnyChartView anyChartView;
    String[] names = new String[5];
    int[] times = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userStatsFragment = (UserStatsFragment) getSupportFragmentManager().findFragmentById(R.id.list_Fragment);

        // ADDED FOR PIE CHART
        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart(userStatsFragment);
    }

    // ADDED FOR PIE CHART
    public void setupPieChart(UserStatsFragment frg) {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < frg.tasks.size(); i++) {
            names[i] = frg.tasks.get(i).getName();
            times[i] = frg.tasks.get(i).getTotalTime();
        }

        for (int i = 0; i < names.length; i++) {
            dataEntries.add(new ValueDataEntry(names[i], times[i]));
        }

        pie.data(dataEntries);
        //pie.title("Earnings");
        anyChartView.setChart(pie);
    }

    // ADDED FOR PIE CHART
    // HOLY VIDEO: https://www.youtube.com/watch?v=qWBA2ikLJjU
}
