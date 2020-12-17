package com.example.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Task implements Parcelable {

    //Inner classes
    private static class TaskRepository{
        public Embedded _embedded;
        public Links _links;
    }

    private static class Embedded{
        public Task[] tasks;
    }

    private static class Links{
        public Href self;
        public Href task;
        public Href user;
        public Href entries;
        public Href collaborators;
    }

    private static class Href{
        public String href;
        public boolean templated;
    }

    //Fields
    String uuid;
    String user_uuid;
    String name;
    String description;
    List<Entry> entries;
    private Links _links;

    public Task() {
        this.name = "";
        this.description = "";
        this.entries = new ArrayList<>();
    }

    public Task(String uuid, String user_uuid, String name, String description) {
        this.uuid = uuid;
        this.user_uuid = user_uuid;
        this.name = name;
        this.description = description;
        this.entries = new ArrayList<>();
    }

    public Task(Parcel in) {
        this.uuid = in.readString();
        this.user_uuid = in.readString();
        this.name = in.readString();
        this.description = in.readString();

        this.entries = new ArrayList<>();
        in.readList(this.entries, Entry.class.getClassLoader());
    }

    //Methods
    public String getUuid() {
        return uuid;
    }

    public Task setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public Task setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Entry getEntry(int position){
        try {
            return this.entries.get(position);
        }
        catch (Exception e){
            return null;
        }
    }

    public Task setEntry(int position, Entry entry){
        this.entries.set(position, entry);
        return this;
    }

    public List<Entry> getEntries(){
        return this.entries;
    }

    public Task setEntries(List<Entry> entries){
        this.entries = entries;
        return this;
    }

    public Task addEntry(Entry entry){
        this.entries.add(entry);
        return this;
    }

    public int getTotalTime(){
        int total = 0;
        for (Entry entry : this.entries){
            total += entry.getTime();
        }
        return total;
    }

    public Task clone(){
        Task clone = new Task();
        clone.uuid = this.uuid;
        clone.user_uuid = this.user_uuid;
        clone.name = this.name;
        clone.description = this.description;
        clone.entries = this.entries;
        return clone;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String format() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();
        Task toJson = (Task) this.clone();
        toJson.setUuid(null);
        return gson.toJson(toJson);
    }

    public static Task parse(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Task task = gson.fromJson(json, Task.class);

        String self = task._links.task.href;
        String[] arr = self.split("/");
        task.setUuid(arr[arr.length - 1]);

        return task;
    }

    public static Task[] parseArray(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        TaskRepository taskRepository = gson.fromJson(json, TaskRepository.class);
        Task[] tasks =  taskRepository._embedded.tasks;
        for(int i = 0; i < tasks.length; i++){
            String self = tasks[i]._links.self.href;
            String[] arr = self.split("/");
            tasks[i].setUuid(arr[arr.length - 1]);
        }

        return taskRepository._embedded.tasks;
    }

    //Overried Methods
    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + this.uuid + '\'' +
                ", user_uuid='" + user_uuid + '\'' +
                ", name='" + this.name + '\'' +
                ", description=" + this.description +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.uuid);
        parcel.writeString(this.user_uuid);
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeList(this.entries);
    }
}
