package com.example.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Entry implements Parcelable {

    //Fields
    private String uuid;
    private String task_uuid;
    private String name;
    private int time;
    private Date added_on;
    private Date started_on;
    private Date ended_on;

    //Constructors
    public Entry(){
        this.name = "";
        this.time = 0;
        this.added_on = new Date();
    }

    public Entry(String uuid, String task_uuid, String name, int time, Date added_on, Date started_on, Date ended_on) {
        this.uuid = uuid;
        this.task_uuid = task_uuid;
        this.name = name;
        this.time = time;
        this.added_on = added_on;
        this.started_on = started_on;
        this.ended_on = ended_on;
    }

    public Entry(Parcel in) {
        this.uuid = in.readString();
        this.task_uuid = in.readString();
        this.name = in.readString();
        this.time = in.readInt();
        this.added_on = (Date) in.readSerializable();
        this.started_on = (Date) in.readSerializable();
        this.ended_on = (Date) in.readSerializable();
    }

    //Methods
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getTask_uuid() {
        return task_uuid;
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

    public Date getAdded_on() {
        return added_on;
    }

    public Date getStarted_on() {
        return started_on;
    }

    public Entry setStarted_on(Date started_on) {
        this.started_on = started_on;
        return this;
    }

    public Date getEnded_on() {
        return ended_on;
    }

    public Entry setEnded_on(Date ended_on) {
        this.ended_on = ended_on;
        return this;
    }

    public void AddTime(int time){
        this.time += time;
    }

    public Entry clone(){
        Entry clone = new Entry();
        clone.uuid = this.uuid;
        clone.task_uuid = this.task_uuid;
        clone.name = this.name;
        clone.time = this.time;
        clone.added_on = this.added_on;
        clone.started_on = this.started_on;
        clone.ended_on = this.ended_on;
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

    @Override
    public String toString() {
        return "Entry{" +
                "uuid='" + uuid + '\'' +
                ", task_uuid='" + task_uuid + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", added_on=" + added_on +
                ", started_on=" + started_on +
                ", ended_on=" + ended_on +
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
        parcel.writeString(this.task_uuid);
        parcel.writeString(this.name);
        parcel.writeInt(this.time);
        parcel.writeSerializable(this.added_on);
        parcel.writeSerializable(this.started_on);
        parcel.writeSerializable(this.ended_on);
    }
}
