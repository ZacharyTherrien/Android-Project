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

    private List<Entry> entries;
    private EntryViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.overview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.entries = new ArrayList<>();

        this.adapter = new EntryViewAdapter(this, this.entries);
        recyclerView.setAdapter(this.adapter);

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
        Random random = new Random();
        Entry entry = new Entry();

        entry.setName("name" + (1000 + random.nextInt(9000)));
        entry.setTime(random.nextInt(1000000));

        entries.add(entry);

        Collections.sort(this.entries, new Comparator<Entry>() {
            @Override public int compare(Entry a, Entry b) {
                return a.getName().compareTo(b.getName());
            }
        });

        this.adapter.update();
    }
}
