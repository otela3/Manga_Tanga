package com.example.mangatangajava.database;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfflineActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //activacion de persistencia de datos offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
