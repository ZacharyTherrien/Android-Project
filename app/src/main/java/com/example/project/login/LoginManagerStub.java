package com.example.project.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class LoginManagerStub implements LoginManager{

    private static final String PREFERENCE_NAME = "login-manager";
    private static final String PREFERENCE_USERNAME = "username";
    private static final String PREFERENCE_UUID = "UUID";

    private static class Account{
        public String UUID;
        public String password;

        public Account(String UUID, String password){
            this.UUID = UUID;
            this.password = password;
        }
    }

    private static Map<String, Account> accounts;

    static{
        accounts = new HashMap<>();
        accounts.put("Ian", new Account(java.util.UUID.randomUUID().toString(), "JAVA IS THE BEST"));
    }

    boolean loggedIn;
    String username;
    String UUID;

    private OnLoginListener onLoginListener;

    private Context context;

    public LoginManagerStub(Context context) {
        this.context = context;

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if(preferences.contains(PREFERENCE_USERNAME)){
            loggedIn = true;
            this.username = preferences.getString(PREFERENCE_USERNAME, "");
            this.UUID = preferences.getString(PREFERENCE_UUID, "");
        }
        else
            loggedIn = false;
    }

    @Override
    public void setOnLoginListener(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Override
    public void login(String username, String password) {
        if (accounts.containsKey(username)) {
            Account account = accounts.get(username);
            if (account.password.equals(password)) {
                this.UUID = account.UUID;
                loggedIn = true;
                this.username = username;

                SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
                preferences.edit().putString(PREFERENCE_USERNAME, username)
                                  .putString(PREFERENCE_UUID, UUID)
                                  .apply();

                if (onLoginListener != null)
                    onLoginListener.onLogin(UUID);

                Log.d("LOGGED IN", "Username:: " + this.username);
                Log.d("LOGGED IN", "UUID: " + this.UUID);
            }
            else {
                if (onLoginListener != null)
                    onLoginListener.onError("Incorrect password.");
                loggedIn = false;
            }
        }
        else {
            if(onLoginListener != null)
                onLoginListener.onError("Invalid username.");
            loggedIn = false;
        }
    }

    @Override
    public void logout() {
        loggedIn = false;
        this.username = "";
        this.UUID = "";

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        preferences.edit().remove(PREFERENCE_USERNAME)
                          .remove(PREFERENCE_UUID)
                          .apply();

        if(onLoginListener != null)
            onLoginListener.onLogout();
    }

    @Override
    public void register(String username, String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            if(onLoginListener != null)
                onLoginListener.onError("Paswords do not match");
            return;
        }
        if(accounts.containsKey(username)){
            if(onLoginListener != null)
                onLoginListener.onError("Username already exists.");
            return;
        }
        this.loggedIn = true;
        this.UUID = java.util.UUID.randomUUID().toString();
        this.username = username;
        accounts.put(username, new Account(this.UUID, password));
        if(onLoginListener != null)
            onLoginListener.onRegister(this.UUID);
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getUUID() {
        return UUID;
    }
}
