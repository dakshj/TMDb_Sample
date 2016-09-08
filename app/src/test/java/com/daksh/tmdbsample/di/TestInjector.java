package com.daksh.tmdbsample.di;

import com.daksh.tmdbsample.BuildConfig;
import com.daksh.tmdbsample.app.TestTmdbApplication;
import com.daksh.tmdbsample.di.component.DaggerTestAppComponent;
import com.daksh.tmdbsample.di.component.TestAppComponent;
import com.daksh.tmdbsample.di.module.NetworkModule;
import com.daksh.tmdbsample.di.module.TestAppModule;

/**
 * Created by daksh on 03-Sep-16.
 */
public enum TestInjector {

    INSTANCE;

    private TestAppComponent testAppComponent;

    public TestAppComponent initializeAppComponent(TestTmdbApplication application) {
        testAppComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(application))
                .networkModule(new NetworkModule(BuildConfig.BASE_URL, BuildConfig.API_KEY))
                .build();

        return testAppComponent;
    }

    public TestAppComponent getAppComponent() {
        return testAppComponent;
    }
}
