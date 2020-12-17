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

import androidx.activity.OnBackPressedCallback;
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
import com.example.project.ui.task_entry.TaskEntryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TaskOverviewActivity extends AppCompatActivity {
    protected Task task;
    protected TaskOverviewFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the task from the intent
        Intent intent = getIntent();
        this.task = intent.getExtras().getParcelable("Task");

        // spawn the fragment
        setContentView(R.layout.activity_task_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add listener to the android back button
        // ends activity and returns instance of task back
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                intent.putExtra("Task", task);
                finish();
            }
        });
    }

    // send an instance of an entry to the entry activity
    protected void sendEntryToEntryPageAndBack(View v, Entry entry){
        Activity activity = (Activity) v.getContext();
        Intent intent = new Intent(activity, TaskEntryActivity.class);
        intent.putExtra("Entry", entry);
        activity.startActivityForResult(intent, 1);
    }

    // get the instance of entry from the entry activity after it ended
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Bundle extras = intent.getExtras();
        assert extras != null;
        Entry entry = (Entry) extras.get("Entry");

        // call fragments save method to update the list and db and views
        fragment.saveEntry(entry);
    }
}
