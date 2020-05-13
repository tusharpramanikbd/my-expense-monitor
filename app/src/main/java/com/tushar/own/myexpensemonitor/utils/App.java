package com.tushar.own.myexpensemonitor.utils;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.tushar.own.myexpensemonitor.database.AppDatabase;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize Local database
        AppDatabase.AppDatabaseInitializer(getApplicationContext());

    }
}
