package com.example.project.ui.task_entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;
import com.example.project.model.Entry;

public class TaskEntryActivity extends AppCompatActivity {

    //Views
    Button returnButton;

    //Fields
    private Entry entry;
    private TaskEntryFragment taskEntryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        returnButton = findViewById(R.id.return_Button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                goBack();
            }
        });

        //Get the task entry fragment.
        this.taskEntryFragment = (TaskEntryFragment) getSupportFragmentManager().findFragmentById(R.id.task_overview_fragment);

        //Get the entry from the intent
        Intent intent = getIntent();
        this.entry = intent.getExtras().getParcelable("Entry");

        //Set the entry's properties to the views.
        taskEntryFragment.setEntry(this.entry);
    }

    private void goBack(){
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        intent.putExtra("Entry", taskEntryFragment.getEntry());
        finish();
    }

    public Entry getEntry(){
        return this.entry;
    }
}
