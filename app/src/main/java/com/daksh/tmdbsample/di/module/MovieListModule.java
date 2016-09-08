package com.daksh.tmdbsample.di.module;

import com.daksh.tmdbsample.di.scope.ActivityScope;
import com.daksh.tmdbsample.movielist.MovieListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daksh on 07-09-2016.
 */

@Module
public class MovieListModule {

    private final MovieListContract.View view;

    public MovieListModule(MovieListContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    MovieListContract.View provideMovieListView() {
        return view;
    }
}
