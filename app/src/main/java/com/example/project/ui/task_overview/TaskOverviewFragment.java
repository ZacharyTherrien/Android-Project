package com.example.project.ui.task_overview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskOverviewFragment extends Fragment {
    private View root;
    private TaskOverviewActivity activity;
    private EntryViewAdapter adapter;

    // text views for the name and description of the task
    private EditText nameView;
    private EditText descView;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_task_overview, container, false);
        this.activity = (TaskOverviewActivity) getActivity();
        // set the fragment instance in activity to this instance of fragment
        this.activity.fragment = this;

        // send the callback class with a method to be used as the onclick listener for the view holders
        this.adapter = new EntryViewAdapter(activity, this.activity.task.getEntries(), new ViewHolderOnClickCallback(this));

        // get the text views
        this.nameView = this.root.findViewById(R.id.overview_task_name);
        this.descView = this.root.findViewById(R.id.overview_task_desc);

        setOnNewEntryClickListener();
        doRecyclerViewStuff();
        setTaskTextFields();
        addEditTextOnChangeListeners();

        return root;
    }

    // sets the on click event listener for the new button
    private void setOnNewEntryClickListener(){
        this.root.findViewById(R.id.overview_new_entry_fbtn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createEntry();
            }
        });
    }

    // creates a recycler view, sets its layout manager and adapter
    private void doRecyclerViewStuff(){
        RecyclerView recyclerView = this.root.findViewById(R.id.overview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(this.adapter);
    }

    // set the 2 task fields to have the values from the task
    private void setTaskTextFields(){
        this.nameView.setText(this.activity.task.getName());
        this.descView.setText(this.activity.task.getDescription());
    }

    // update the values in the task instance when the user changes the content of the text fields
    private void addEditTextOnChangeListeners(){
        this.nameView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                activity.task.setName(nameView.getText().toString());
            }
        });

        this.descView.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                activity.task.setDescription(descView.getText().toString());
            }
        });
    }

    // gets the entries from the server for the task
    private List<Entry> getEntriesFromServer(){
        return new ArrayList<>();
    }

    // adds entry on the server db
    private void addEntryToDatabase(Entry entry){

    }

    // updates entry on server db
    private void updateEntryInDatabase(Entry entry){

    }

    // creates an entry and sends it to entry activity to be edited
    private void createEntry(){
        Entry entry = new Entry();

        entry.setUuid(UUID.randomUUID().toString());
        entry.setTask_uuid(this.activity.task.getUuid());
        this.activity.task.addEntry(entry);
        this.addEntryToDatabase(entry);
        this.editEntry(entry);
    }

    // edits an existing entry by sending it to the entry activity
    private void editEntry(Entry entry){
        this.activity.sendEntryToEntryPageAndBack(this.root, entry);
    }

    // saves the instance of an entry in the list, db and view
    public void saveEntry(Entry entry){
        for (int i = 0; i < this.activity.task.getEntries().size(); i++) {
            if (this.activity.task.getEntry(i).getUuid().equals(entry.getUuid())){
                this.activity.task.setEntry(i, entry);
                this.updateEntryInDatabase(entry);
                this.adapter.update();
                return;
            }
        }
    }

    // callback class
    public static class ViewHolderOnClickCallback{
        private final TaskOverviewFragment fragment;

        public ViewHolderOnClickCallback(TaskOverviewFragment fragment){
            this.fragment = fragment;
        }

        // method that gets called as the callback
        // takes an entry and edits it
        public void execute(Entry entry){
            this.fragment.editEntry(entry);
        }
    }
}
