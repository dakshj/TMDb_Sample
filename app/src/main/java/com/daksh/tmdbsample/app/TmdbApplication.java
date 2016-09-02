package com.daksh.tmdbsample.app;

import android.app.Application;

import com.daksh.tmdbsample.di.Injector;

/**
 * Created by daksh on 03-Sep-16.
 */
public class TmdbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.INSTANCE.initializeAppComponent(this).inject(this);
    }
}
