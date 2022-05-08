package com.example.movieapp.main.ui;

import android.app.Application;
import android.content.Context;

public class MovieApp extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}
