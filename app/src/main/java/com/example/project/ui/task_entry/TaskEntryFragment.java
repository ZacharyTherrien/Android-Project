package com.example.project.ui.task_entry;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.model.Entry;
import com.example.project.ui.util.AmountFragment;

public class TaskEntryFragment extends Fragment {

    //Get all the necessary views.
    TextView taskTextView;
    EditText nameEditText;
    TextView totalTextView;
    Button amountButton;
    Button datesButton;
    Button timerButton;
    Chronometer timeChronometer;
    Button startStopButton;

    //Fields
    Entry entry;
    long pausedAt;
    boolean timerOn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_entry, container, false);

        //Set the views by their ID in the fragment.
        taskTextView = root.findViewById(R.id.task_TextView);
        nameEditText = root.findViewById(R.id.name_EditText);
        totalTextView = root.findViewById(R.id.total_TextView);
        amountButton = root.findViewById(R.id.amount_Button);
        datesButton = root.findViewById(R.id.dates_Button);
        timerButton = root.findViewById(R.id.timer_Button);
        timeChronometer = root.findViewById(R.id.time_Chronometer);
        startStopButton = root.findViewById(R.id.startStop_Button);

        //Set values into views and fields
        entry = new Entry();
        startStopButton.setText("Start");
        timerOn = false;

        //Add event listeners and inner classes for their event handler.
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                entry.setName(nameEditText.getText().toString());
            }
        });

        amountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrieveAmount();
            }
        });

        datesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the timer has not started yet, start it, otherwise, pause the timer.
                if(!timerOn){
                    startStopButton.setText("Stop");
                    if(pausedAt != 0)
                        timeChronometer.setBase(timeChronometer.getBase() + SystemClock.elapsedRealtime() - pausedAt);
                    else
                        timeChronometer.setBase(SystemClock.elapsedRealtime());
                    timeChronometer.start();
                    //Do not allow the user to enter time from timer while it's running.
                    timerButton.setEnabled(false);
                }
                else{
                    startStopButton.setText("Start");
                    pausedAt = SystemClock.elapsedRealtime();
                    timeChronometer.stop();
                    timerButton.setEnabled(true);
                }
                timerOn = !timerOn;
            }
        });

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the time to the fragment and the entry.
                int seconds = (int) (SystemClock.elapsedRealtime() - timeChronometer.getBase()) / 1000 - 1;
                totalTextView.setText(Long.toString(Integer.parseInt(totalTextView.getText().toString()) + seconds));
                entry.AddTime(seconds);
                //Finally, reset it.
                timeChronometer.setBase(SystemClock.elapsedRealtime());
                pausedAt = 0;
            }
        });

        return root;
    }

    public void RetrieveAmount(){
        AmountFragment amountFragment = new AmountFragment();
        amountFragment.setTargetFragment(this, 1);
        amountFragment.show(getChildFragmentManager(), "Choose Amount");
        getTargetFragment().onActivityResult(getTargetRequestCode(), 1, null);
    }

    //Provided an instance of entry, set it and its fields.
    public void setEntry(Entry entry){
        //Set the note of the fragment to the one provided.
        this.entry = entry;
        //Now set the fields to the fragment.

    }

    @Override
    public void onAttach(){}

}
