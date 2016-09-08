package com.daksh.tmdbsample.di.module;

import android.app.Application;
import android.content.Context;

import com.daksh.tmdbsample.app.TestTmdbApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daksh on 08-09-2016.
 */

@Module
public class TestAppModule {

    private final TestTmdbApplication application;

    public TestAppModule(TestTmdbApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }
}
