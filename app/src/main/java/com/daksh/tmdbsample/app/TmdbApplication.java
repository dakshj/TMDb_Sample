package com.daksh.tmdbsample.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.daksh.tmdbsample.BuildConfig;
import com.daksh.tmdbsample.di.Injector;

import io.fabric.sdk.android.Fabric;

/**
 * Created by daksh on 03-Sep-16.
 */
public class TmdbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Injector.INSTANCE.initializeAppComponent(this).inject(this);

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
