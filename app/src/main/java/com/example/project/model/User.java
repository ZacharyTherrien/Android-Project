package com.example.project.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class User {

    private static class TaskRepository{
        public Embedded _embedded;
        public Links _links;
    }

    private static class Embedded{
        public User[] users;
    }

    private static class Links{
        public Href self;
        public Href user;
        public Href tasks;
    }

    private static class Href{
        public String href;
    }

    private long id;
    private String name;
    private String email;
    private String uuid;
    private String avatar;
    private Links _links;

    public User() {
        this(-1);
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String name, String email) {
        this(id);
        this.name = name;
        this.email = email;
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setUuid(String Uuid){
        this.uuid = Uuid;
        return this;
    }

    public String getUuid(){
        return this.uuid;
    }

    public User setAvatar(String avatar){
        this.avatar = avatar;
        return this;
    }

    public String getAvatar(){
        return this.avatar;
    }

    public static User parse(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        User user = gson.fromJson(json, User.class);
        String self = user._links.self.href;
        String[] arr = self.split("/");
        user.setUuid(arr[arr.length - 1]);

        return user;
    }

    public static User[] parseArray(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        TaskRepository taskRepository = gson.fromJson(json, TaskRepository.class);
        User[] users =  taskRepository._embedded.users;
        for(int i = 0; i < users.length; i++){
            String self = users[i]._links.self.href;
            String[] arr = self.split("/");
            users[i].setUuid(arr[arr.length - 1]);
        }

        return users;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

}
