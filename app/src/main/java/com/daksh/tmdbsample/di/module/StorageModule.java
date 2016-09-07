package com.daksh.tmdbsample.di.module;

import android.content.Context;

import com.daksh.tmdbsample.data.source.local.AppSettings;
import com.daksh.tmdbsample.data.source.local.AppSettingsImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daksh on 03-Sep-16.
 */

@Module
public class StorageModule {

    @Provides
    @Singleton
    AppSettings provideAppSettings(Context context) {
        return new AppSettingsImpl(context);
    }
}
