package com.example.project.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Task implements Parcelable {

    //Fields
    String uuid;
    String user_uuid;
    String name;
    String description;

    public Task() {
        this.name = "";
        this.description = "";
    }

    public Task(String uuid, String user_uuid, String name, String description) {
        this.uuid = uuid;
        this.user_uuid = user_uuid;
        this.name = name;
        this.description = description;
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

    public int getTotalTime(){
        return -1;
    }

    public Task Clone(){
        Task clone = new Task();
        clone.uuid = this.uuid;
        clone.user_uuid = this.user_uuid;
        clone.name = this.name;
        clone.description = this.description;
        return clone;
    }

    //Overried Methods
    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", user_uuid='" + user_uuid + '\'' +
                ", name='" + name + '\'' +
                ", description=" + description +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
