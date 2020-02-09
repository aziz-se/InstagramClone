package com.techleadbd.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7bpXT1icZ6Rlauo5RYoLPKVrFkuARdAA4q2ZYF5w")
                // if defined
                .clientKey("Tkjt6aVx3GDcbw9hv0GHvuhZngsOsxdT1ifaouwu")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
