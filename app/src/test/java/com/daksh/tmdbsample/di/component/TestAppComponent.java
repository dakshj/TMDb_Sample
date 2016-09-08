package com.daksh.tmdbsample.di.component;

import com.daksh.tmdbsample.app.TestTmdbApplication;
import com.daksh.tmdbsample.di.module.NetworkModule;
import com.daksh.tmdbsample.di.module.StorageModule;
import com.daksh.tmdbsample.di.module.TestAppModule;
import com.daksh.tmdbsample.movielist.MovieListPresenterTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by daksh on 08-09-2016.
 */

@Singleton
@Component(modules = {
        TestAppModule.class,
        NetworkModule.class,
        StorageModule.class
})
public interface TestAppComponent extends AppComponent {

    void inject(TestTmdbApplication testTmdbApplication);

    void inject(MovieListPresenterTest movieListPresenterTest);
}
