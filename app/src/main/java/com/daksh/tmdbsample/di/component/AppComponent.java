package com.daksh.tmdbsample.di.component;

import com.daksh.tmdbsample.app.TmdbApplication;
import com.daksh.tmdbsample.di.module.AppModule;
import com.daksh.tmdbsample.di.module.NetworkModule;
import com.daksh.tmdbsample.di.module.StorageModule;
import com.daksh.tmdbsample.movielist.MovieListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by daksh on 03-Sep-16.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        StorageModule.class
})
public interface AppComponent {

    void inject(TmdbApplication application);

    void inject(MovieListActivity movieListActivity);
}
