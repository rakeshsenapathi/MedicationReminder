package com.senapathi.medicationreminder.main.activities;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Senapathi on 27-12-2016.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
