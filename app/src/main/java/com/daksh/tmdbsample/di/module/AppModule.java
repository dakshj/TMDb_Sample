package com.daksh.tmdbsample.di.module;

import android.content.Context;

import com.daksh.tmdbsample.app.TmdbApplication;

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
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }
}
