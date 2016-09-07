package com.daksh.tmdbsample.di.module;

import android.app.Application;
import android.content.Context;

import com.daksh.tmdbsample.app.TmdbApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daksh on 03-Sep-16.
 */

@Module
public class AppModule {

    private final TmdbApplication application;

    public AppModule(TmdbApplication application) {
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
