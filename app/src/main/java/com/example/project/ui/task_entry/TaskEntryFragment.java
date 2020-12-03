package com.example.project.ui.task_entry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.model.Entry;
import com.example.project.ui.util.AmountFragment;
import com.example.project.ui.util.DatePickerDialogFragment;
import com.example.project.ui.util.TimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class TaskEntryFragment extends Fragment {

    /**
     * TODO Ask Ian:
     * - How to get a value from the dialog fragment.
     * - A few milisecond delay when subtracting date's times.
     * - EntryActivity issue with scope in onClickListener.
     * - Stack of activity/intent from home -> user -> task.
     */


    //Get all the necessary views.
    TextView taskTextView;
    EditText nameEditText;
    TextView totalTextView;
    Button amountButton;
    Button datesButton;
    Button timerButton;
    Chronometer timeChronometer;
    Button resetButton;
    Button startStopButton;

    //Fields
    Entry entry;
    long dateStart;
    boolean dateObtained;
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
        resetButton = root.findViewById(R.id.reset_Button);
        startStopButton = root.findViewById(R.id.startStop_Button);

        //Set values into views and fields
        entry = new Entry();
        dateObtained = false;
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
                //First get the starting date.
                DatePickerDialogFragment datePickerStart = DatePickerDialogFragment.create(new Date(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SwitchObtained();
                        GetDate(year, month, dayOfMonth);
                    }
                });
                datePickerStart.show(getFragmentManager(), "Date Picker");
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeChronometer.setBase(SystemClock.elapsedRealtime());
                pausedAt = 0;
            }
        });

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the time to the fragment and the entry.
                int seconds = (int) (SystemClock.elapsedRealtime() - timeChronometer.getBase()) / 1000 - 1;
                if(seconds > 0)
                    entry.AddTime(seconds);
                totalTextView.setText(Integer.toString(entry.getTime()));
                //Finally, reset it.
                timeChronometer.setBase(SystemClock.elapsedRealtime());
                pausedAt = 0;
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
                    //Do not allow the user to enter time from timer while it's running nor reset.
                    EnableTimerButtons(false);
                }
                else{
                    startStopButton.setText("Start");
                    pausedAt = SystemClock.elapsedRealtime();
                    timeChronometer.stop();
                    //Once the time is stopped, enable buttons again.
                    EnableTimerButtons(true);
                }
                timerOn = !timerOn;
            }
        });

        return root;
    }

    public void EnableTimerButtons(boolean enable){
        timerButton.setEnabled(enable);
        resetButton.setEnabled(enable);
    }

    public void RetrieveAmount(){
        AmountFragment amountFragment = new AmountFragment();
        amountFragment.setOnAmountenteredListener(new AmountFragment.OnAmountEnteredListener() {
            @Override
            public void onAmountEntered(int amount, String unit) {
                if(unit.equals("Minutes"))
                    amount = amount * 60;
                else if(unit.equals("Hours"))
                    amount = amount * 60 * 60;
                //Add the time to the entry.
                entry.AddTime(amount);
                totalTextView.setText(Integer.toString(entry.getTime()));
            }
        });
        amountFragment.show(getChildFragmentManager(), "Choose Amount");
    }

    //Provided an instance of entry, set it and its fields.
    public void setEntry(Entry entry){
        //Set the note of the fragment to the one provided.
        this.entry = entry;
        //Now set the fields to the fragment.

    }

    private void SwitchObtained(){
        dateObtained = false;
    }

    private void GetDate(int year, int month, int day){
        //Create a calendar to help create the date.
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONDAY, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        //Keep track of the values of the chosen date.
        final Date NEW_DATE = calendar.getTime();
        //Now get the time.
        TimePickerDialogFragment timePicker = TimePickerDialogFragment.create(new Date(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                //Go to set the time.
                GetTime(NEW_DATE, hour, minute);
            }
        });
        timePicker.show(getFragmentManager(), "Time Picker");
    }

    private void GetTime(Date newDate, int hour, int minute){
        //Set the given time.
        newDate.setHours(hour);
        newDate.setMinutes(minute);
        //Since the seconds cannot even be chosen, set it to 0. Helps a lot with precision.
        newDate.setSeconds(0);
        //Store the current amount of time from the current instance date.
        long time = newDate.getTime();
        if(!dateObtained) {
            //Since the first date has been entered, store its value to calculate the total later later.
            dateStart = time;
            dateObtained = true;
            //Create a new date picker dialog that will go through the process of setting date and time.
            DatePickerDialogFragment datePickerEnd = DatePickerDialogFragment.create(new Date(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    GetDate(year, month, dayOfMonth);
                }
            });
            datePickerEnd.show(getFragmentManager(), "Date Picker");
        }
        else{
            dateObtained = false;
            //Only add it if the end date comes after the start date.
            if(time > dateStart){
                //Set the start and end date.
                entry.setStarted_on(new Date(dateStart));
                entry.setEnded_on(newDate);
                //Get the total amount of seconds from miliseconds.
                long total = (time - dateStart) / 1000;
                //Add the time in the entry
                entry.AddTime((int)total);
                totalTextView.setText(Integer.toString(entry.getTime()));
            }
        }
    }
}
