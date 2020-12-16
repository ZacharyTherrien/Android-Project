package com.example.project.ui.task_overview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.model.Entry;
import com.example.project.ui.task_entry.TaskEntryActivity;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class EntryViewAdapter extends RecyclerView.Adapter<EntryViewAdapter.EntryViewHolder> {

    private Context context;
    private List<Entry> entries;
    private LayoutInflater inflater;
    private TaskOverviewFragment.ViewHolderOnClickCallback holderCallback;

    public EntryViewAdapter(Context context, List<Entry> entries, TaskOverviewFragment.ViewHolderOnClickCallback holderCallback) {
        this.context = context;
        this.entries = entries;
        this.inflater = LayoutInflater.from(this.context);
        this.holderCallback = holderCallback;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.task_overview_item, parent, false);
        return new EntryViewHolder(view, this.holderCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        holder.set(this.entries.get(position));
    }

    public void update(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.entries.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {

        private final View root;
        private final TaskOverviewFragment.ViewHolderOnClickCallback callback;
        private Entry entry;

        public EntryViewHolder(@NonNull View root, TaskOverviewFragment.ViewHolderOnClickCallback callback){
            super(root);
            this.root = root;
            this.callback = callback;
        }

        public void set(Entry entry) {
            this.entry = entry;

            setName();
            setTime();
            setOnClick(this.callback);
        }

        private void setOnClick(final TaskOverviewFragment.ViewHolderOnClickCallback callback){
            this.root.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    callback.execute(entry);
                }
            });
        }

        private void setName(){
            TextView nameView = this.root.findViewById(R.id.overview_item_name);
            nameView.setText(this.entry.getName());
        }

        private void setTime(){
            TextView timeView = this.root.findViewById(R.id.overview_item_time);
            timeView.setText(simplifySeconds(this.entry.getTime()));
        }

        private String simplifySeconds(int sec){
            final int SEC_IN_MIN = 60;
            final int SEC_IN_HR = 60 * 60;
            final int SEC_IN_DAY = 24 * 60 * 60;

            int days = sec / SEC_IN_DAY;
            sec %= SEC_IN_DAY;

            int hrs = sec / SEC_IN_HR;
            sec %= SEC_IN_HR;

            int mins = sec / SEC_IN_MIN;
            sec %= SEC_IN_MIN;

            if (days > 0) return days + "D " + hrs + "H " + mins + "M " + sec + "S";
            else if (hrs > 0) return hrs + "H " + mins + "M " + sec + "S";
            else if (mins > 0) return mins + "M " + sec + "S";
            else return sec + "S";
        }
    }
}
