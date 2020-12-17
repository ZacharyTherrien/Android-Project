package com.example.project.login;

import android.content.Context;
import android.os.StrictMode;

import com.example.project.networking.HttpRequest;
import com.example.project.networking.HttpResponse;
import com.example.project.ui.TaskApplication;

import java.io.IOException;

public class LoginManagerServer implements LoginManager{

    boolean loggedIn;
    String username;
    String uuid;

    private OnLoginListener onLoginListener;

    private Context context;

    public LoginManagerServer(Context context) {
        this.context = context;
    }

    @Override
    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Override
    public void login(String username, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            TaskApplication application = (TaskApplication) context;
            String url = String.format("http://%s:%s/login", application.getHost(), application.getPort());
            HttpResponse response = new HttpRequest(url)
                    .method(HttpRequest.Method.POST)
                    .contentType("application/x-www-form-urlencoded")
                    .body(String.format("username=%s&password=%s", username, password))
                    .perform();

            if(response.getResponseCode() == 200){
                this.loggedIn = true;
                this.username = username;
                String path = response.getHeaders().get("Path").get(0);
                this.uuid = path.replace("/user/", "");
                if(onLoginListener != null){
                    onLoginListener.onLogin(this.uuid);
                }
            }
            else{
                if(onLoginListener != null){
                    onLoginListener.onError("Unknown user or incorrect password.");
                }
            }
        }
        catch(IOException e){
            if(onLoginListener != null){
                onLoginListener.onError(e.getMessage());
            }
        }
    }

    @Override
    public void logout() {
        this.loggedIn = false;
        this.uuid = "";
        this.username = "";
    }

    @Override
    public void register(String username, String password, String passwordCheck) {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    @Override
    public String getUsername() {
        if(!this.loggedIn)
            throw new IllegalStateException("Not logged in.");
        return this.username;
    }

    @Override
    public String getUUID() {
        if(!this.loggedIn)
            throw new IllegalStateException("Not logged in.");
        return this.uuid;
    }
}
