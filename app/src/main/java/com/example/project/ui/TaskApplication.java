package com.example.project.ui;


import android.app.Application;

import com.example.project.login.LoginManager;
import com.example.project.login.LoginManagerServer;
import com.example.project.login.LoginManagerStub;

public class TaskApplication extends Application {

    private final String DEFAULT_PROTOCOL = "http";
    private final String DEFAULT_HOST = "10.0.2.2";
    private final String DEFAULT_PORT = "9999";

    private String protocol;
    private String host;
    private String port;

    private LoginManager loginManager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.protocol = DEFAULT_PROTOCOL;
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
        //loginManager = new LoginManagerStub(this);
        loginManager = new LoginManagerServer(this);
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
