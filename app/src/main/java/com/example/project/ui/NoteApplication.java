package com.example.project.ui;


import android.app.Application;

import com.example.project.login.LoginManager;
import com.example.project.login.LoginManagerStub;

public class NoteApplication extends Application {

    private LoginManager loginManager;

    @Override
    public void onCreate() {
        super.onCreate();
        loginManager = new LoginManagerStub(this);
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }
}
