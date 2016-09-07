package com.daksh.tmdbsample.di;

import com.daksh.tmdbsample.BuildConfig;
import com.daksh.tmdbsample.app.TmdbApplication;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.di.module.AppModule;
import com.daksh.tmdbsample.di.module.NetworkModule;

/**
 * Created by daksh on 03-Sep-16.
 */
public enum Injector {

    INSTANCE;

    private AppComponent appComponent;

    public AppComponent initializeAppComponent(TmdbApplication application) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .networkModule(new NetworkModule(BuildConfig.BASE_URL, BuildConfig.API_KEY))
                .build();

        return appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
