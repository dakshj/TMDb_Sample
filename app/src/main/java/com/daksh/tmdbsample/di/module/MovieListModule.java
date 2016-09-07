package com.daksh.tmdbsample.di.module;

import com.daksh.tmdbsample.di.scope.ActivityScope;
import com.daksh.tmdbsample.movielist.MovieListActivity;
import com.daksh.tmdbsample.movielist.MovieListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daksh on 07-09-2016.
 */

@Module
public class MovieListModule {

    private final MovieListActivity activity;

    public MovieListModule(MovieListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    MovieListContract.View provideMovieListView() {
        return activity;
    }
}
