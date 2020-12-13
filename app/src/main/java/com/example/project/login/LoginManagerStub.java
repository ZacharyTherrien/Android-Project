package com.example.project.login;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginManagerStub implements LoginManager{

    public static final String PREFERENCES_NAME = "login-manager";
    public static final String PREFERENCE_USERNAME = "username";
    public static final String PREFERENCE_UUID = "uuid";

    private static class Account {
        public String uuid;
        public String password;

        public Account(String uuid, String password) {
            this.uuid = uuid;
            this.password = password;
        }
    }

    private static Map<String, Account> accounts;

    static {
        accounts = new HashMap<>();
        accounts.put("george", new Account(UUID.randomUUID().toString(), "georgePWD"));
    }

    private boolean loggedIn;
    private String username;
    private String uuid;

    private OnLoginListener onLoginListener;

    private Context context;

    public LoginManagerStub(Context context) {
        this.context = context;

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if(preferences.contains(PREFERENCE_USERNAME)) {
            loggedIn = true;
            username = preferences.getString(PREFERENCE_USERNAME, ""); // Corrected
            uuid = preferences.getString(PREFERENCE_UUID, "");
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
            if (accounts.get(username).password.equals((password))) {
                uuid = account.uuid;
                loggedIn = true;
                this.username = username;

                SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
                preferences.edit()
                        .putString(PREFERENCE_USERNAME, username)
                        .putString(PREFERENCE_UUID, uuid)
                        .apply();

                if (onLoginListener != null)
                    onLoginListener.onLogin(uuid);
            } else {
                if (onLoginListener != null)
                    onLoginListener.onError("Incorrect password.");
                loggedIn = false;
            }
        } else {
            if (onLoginListener != null)
                onLoginListener.onError("Incorrect username.");
            loggedIn = false;
        }
    }

    @Override
    public void logout() {
        loggedIn = false;
        username = "";
        uuid = "";

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.edit()
                .remove(PREFERENCE_USERNAME)
                .remove(PREFERENCE_UUID)
                .apply();

        if (onLoginListener != null)
            onLoginListener.onLogout();
    }

    @Override
    public void register(String username, String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            if (onLoginListener != null)
                onLoginListener.onError("Passwords do not match!");
            return;
        }

        if (!password.equals(username)) {
            if (onLoginListener != null)
                onLoginListener.onError("Username in use.");
            return;
        }

        uuid = UUID.randomUUID().toString();
        loggedIn = true;
        this.username = username;
        accounts.put(username, new Account(uuid, password));
        if (onLoginListener != null)
            onLoginListener.onRegister(uuid);
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public String getUsername() {
        if (!loggedIn)
            throw new IllegalStateException("Not logged in.");
        return username;
    }

    @Override
    public String getUuid() {
        if (!loggedIn)
            throw new IllegalStateException("Not logged in.");
        return uuid;
    }
}
