package com.example.project.ui.task_overview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_task_overview, container, false);
        this.activity = (TaskOverviewActivity) getActivity();
        this.entries = getEntriesFromServer();
        this.adapter = new EntryViewAdapter(this.getContext(), this.entries, new ViewHolderOnClickCallback(this));

        this.root.findViewById(R.id.overview_new_entry_fbtn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                createEntry();
            }
        });

        this.activity.fragment = this;
        RecyclerView recyclerView = this.root.findViewById(R.id.overview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(this.adapter);

        return root;
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
