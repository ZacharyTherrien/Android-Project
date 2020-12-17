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
    protected List<Entry> entries;
    private EntryViewAdapter adapter;
    private EditText nameView;
    private EditText descView;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_task_overview, container, false);
        this.activity = (TaskOverviewActivity) getActivity();
        this.activity.fragment = this;
        this.entries = getEntriesFromServer();
        this.adapter = new EntryViewAdapter(this.getContext(), this.entries, new ViewHolderOnClickCallback(this));
        this.nameView = this.root.findViewById(R.id.overview_task_name);
        this.descView = this.root.findViewById(R.id.overview_task_desc);

        setOnNewEntryClickListener();
        doRecyclerViewStuff();
        setTaskTextFields();
        addEditTextOnChangeListeners();

        return root;
    }

    private void setOnNewEntryClickListener(){
        this.root.findViewById(R.id.overview_new_entry_fbtn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createEntry();
            }
        });
    }

    private void doRecyclerViewStuff(){
        RecyclerView recyclerView = this.root.findViewById(R.id.overview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(this.adapter);
    }

    private void setTaskTextFields(){
        this.nameView.setText(this.activity.task.getName());
        this.descView.setText(this.activity.task.getDescription());
    }

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

    private List<Entry> getEntriesFromServer(){
        return new ArrayList<>();
    }

    private void createEntry(){
        Entry entry = new Entry();
        entry.setUuid(UUID.randomUUID().toString());
        this.entries.add(entry);
        this.editEntry(entry);
    }

    private void editEntry(Entry entry){
        this.activity.sendEntryToEntryPageAndBack(this.root, entry);
    }

    public void saveEntry(Entry entry){
        for (int i = 0; i < this.entries.size(); i++) {
            if (this.entries.get(i).getUuid().equals(entry.getUuid())){
                this.entries.set(i, entry);
                this.adapter.update();
                break;
            }
        }
    }

    public static class ViewHolderOnClickCallback{
        private final TaskOverviewFragment fragment;

        public ViewHolderOnClickCallback(TaskOverviewFragment fragment){
            this.fragment = fragment;
        }

        public void execute(Entry entry){
            this.fragment.editEntry(entry);
        }
    }
}
