package com.example.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public class Entry implements Parcelable {

    //Inner classes
    private static class TaskRepository{
        public Entry.Embedded _embedded;
        public Entry.Links _links;
    }

    private static class Embedded{
        public Entry[] entries;
    }

    private static class Links{
        public Entry.Href self;
        public Entry.Href entry;
        public Entry.Href user;
        public Entry.Href entries;
        public Entry.Href collaborators;
    }

    private static class Href{
        public String href;
        public boolean templated;
    }

    //Fields
    private String uuid;
    private String task;
    private String name;
    private int time;
    private Date addedOn;
    private Date startedOn;
    private Date endedOn;
    private Links _links;

    //Constructors
    public Entry(){
        this.name = "";
        this.time = 0;
        this.addedOn = new Date();
    }

    public Entry(String uuid, String task_uuid, String name, int time, Date added_on, Date started_on, Date ended_on) {
        this.uuid = uuid;
        this.task = task_uuid;
        this.name = name;
        this.time = time;
        this.addedOn = added_on;
        this.startedOn = started_on;
        this.endedOn = ended_on;
    }

    public Entry(Parcel in) {
        this.uuid = in.readString();
        this.task = in.readString();
        this.name = in.readString();
        this.time = in.readInt();
        this.addedOn = (Date) in.readSerializable();
        this.startedOn = (Date) in.readSerializable();
        this.endedOn = (Date) in.readSerializable();
    }

    //Methods
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String uuid){
        this.task = uuid;
    }

    public String getName() {
        return name;
    }

    public Entry setName(String name) {
        this.name = name;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Entry setTime(int time) {
        this.time = time;
        return this;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public Date getStarted_on() {
        return startedOn;
    }

    public Entry setStarted_on(Date started_on) {
        this.startedOn = started_on;
        return this;
    }

    public Date getEnded_on() {
        return endedOn;
    }

    public Entry setEnded_on(Date ended_on) {
        this.endedOn = ended_on;
        return this;
    }

    public void AddTime(int time){
        this.time += time;
    }

    public Entry clone(){
        Entry clone = new Entry();
        clone.uuid = this.uuid;
        clone.task = this.task;
        clone.name = this.name;
        clone.time = this.time;
        clone.addedOn = this.addedOn;
        clone.startedOn = this.startedOn;
        clone.endedOn = this.endedOn;
        return clone;
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public String format() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();
        Entry toJson = (Entry) this.clone();
        toJson.setUuid(null);
        return gson.toJson(toJson);
    }

    public static Entry parse(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Entry entry = gson.fromJson(json, Entry.class);

        String self = entry._links.entry.href;
        String[] arr = self.split("/");
        entry.setUuid(arr[arr.length - 1]);

        return entry;
    }

    public static Entry[] parseArray(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Entry.TaskRepository entryRepository = gson.fromJson(json, TaskRepository.class);
        Entry[] entries =  entryRepository._embedded.entries;
        for(int i = 0; i < entries.length; i++){
            String self = entries[i]._links.self.href;
            String[] arr = self.split("/");
            entries[i].setUuid(arr[arr.length - 1]);
        }

        return entryRepository._embedded.entries;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "uuid='" + uuid + '\'' +
                ", task='" + task + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", addedOn=" + addedOn +
                ", startedOn=" + startedOn +
                ", endedOn=" + endedOn +
                '}';
    }

    //Overloads
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.uuid);
        parcel.writeString(this.task);
        parcel.writeString(this.name);
        parcel.writeInt(this.time);
        parcel.writeSerializable(this.addedOn);
        parcel.writeSerializable(this.startedOn);
        parcel.writeSerializable(this.endedOn);
    }
}
