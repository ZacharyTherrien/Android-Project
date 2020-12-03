package com.example.project.ui.task_overview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;
import com.example.project.ui.task_entry.TaskEntryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO remove later.
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRandomEntry();
            }
        });

        findViewById(R.id.button1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Activity activity = (Activity) v.getContext();
                Intent intent = new Intent(activity, TaskEntryActivity.class);
                activity.startActivityForResult(intent, 1);

                return true;
            }
        });
    }

    private void createRandomEntry(){
        View taskView = getLayoutInflater().inflate(R.layout.task_overview_item, (ViewGroup) findViewById(R.id.overview_tasks));
//        ((TextView) taskView.findViewById(R.id.overview_item_title)).setText("text goes here");
//        ((TextView) taskView.findViewById(R.id.overview_item_time)).setText("text goes here too");
    }
}
