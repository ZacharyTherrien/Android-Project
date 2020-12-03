package com.example.project.ui.task_entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;
import com.example.project.model.Entry;

public class TaskEntryActivity extends AppCompatActivity {

    //Views
    Button returnButton;

    //Fields
    private Entry entry;

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
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                intent.putExtra("Entry", "TODO: GET ENTRY.");
                finish();
            }
        });

        this.entry = new Entry();
    }

    public Entry getEntry(){
        return this.entry;
    }
}
