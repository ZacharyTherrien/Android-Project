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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Entry;
import com.example.project.model.Task;
import com.example.project.ui.task_entry.TaskEntryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TaskOverviewActivity extends AppCompatActivity {
    private Task task;
    protected TaskOverviewFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void sendEntryToEntryPageAndBack(View v, Entry entry){
        Activity activity = (Activity) v.getContext();
        Intent intent = new Intent(activity, TaskEntryActivity.class);
        intent.putExtra("Entry", entry);
        activity.startActivityForResult(intent, 1);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Bundle extras = intent.getExtras();
        assert extras != null;
        Entry entry = (Entry) extras.get("Entry");
        fragment.saveEntry(entry);
    }
}
