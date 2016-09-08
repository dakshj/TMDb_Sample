package com.daksh.tmdbsample.app;

import com.daksh.tmdbsample.di.TestInjector;

/**
 * Created by daksh on 08-09-2016.
 */
public class TestTmdbApplication extends TmdbApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        TestInjector.INSTANCE.initializeAppComponent(this).inject(this);
    }
}
