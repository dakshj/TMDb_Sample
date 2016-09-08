package com.daksh.tmdbsample.di.module;

import com.daksh.tmdbsample.di.scope.ActivityScope;
import com.daksh.tmdbsample.moviedetail.MovieDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by daksh on 07-09-2016.
 */

@Module
public class MovieDetailModule {

    private final MovieDetailContract.View view;

    public MovieDetailModule(MovieDetailContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    MovieDetailContract.View provideMovieDetailView() {
        return view;
    }
}
