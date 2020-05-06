package com.example.laboratory.application;

import android.app.Application;

public class LApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initialize(this);
    }
}
