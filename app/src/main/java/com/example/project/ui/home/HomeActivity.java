package com.example.project.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project.R;
import com.example.project.ui.task_overview.TaskOverviewActivity;
import com.example.project.ui.task_overview.TaskOverviewFragment;
import com.example.project.ui.user_stats.UserStatsActivity;

public class HomeActivity extends AppCompatActivity {

    RecyclerView tasksRecyclerView;

    //TODO remove later
    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tasksRecyclerView = findViewById(R.id.tasks_RecyclerView);

        //TODO remove later.
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                Intent intent = new Intent(activity, TaskOverviewActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                Intent intent = new Intent(activity, UserStatsActivity.class);
                activity.startActivityForResult(intent, 1);
            }
        });
    }
}