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

    // ADDED FOR PIE CHART
    AnyChartView anyChartView;
    String[] months = {"Jan", "Feb", "Mar"};
    int[] earnings = {500, 800, 2000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ADDED FOR PIE CHART
        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart();
    }

    // ADDED FOR PIE CHART
    public void setupPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < months.length; i++) {
            dataEntries.add(new ValueDataEntry(months[i], earnings[i]));
        }

        pie.data(dataEntries);
        //pie.title("Earnings");
        anyChartView.setChart(pie);
    }

    // ADDED FOR PIE CHART
    // HOLY VIDEO: https://www.youtube.com/watch?v=qWBA2ikLJjU
}
