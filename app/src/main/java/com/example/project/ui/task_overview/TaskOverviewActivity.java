package com.example.project.ui.task_overview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;
import com.example.project.model.Entry;
import com.example.project.model.Task;
import com.example.project.ui.task_entry.TaskEntryActivity;

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
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                goBack();
            }
        });

        // listener for floating button to go back
        findViewById(R.id.user_back_fbtn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                goBack();
            }
        });
    }

    // ends activity and returns instance of task back
    private void goBack(){
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        intent.putExtra("Task", task);
        finish();
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
