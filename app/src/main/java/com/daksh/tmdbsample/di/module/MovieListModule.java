package com.daksh.tmdbsample.di.module;

import com.daksh.tmdbsample.data.source.local.AppSettings;
import com.daksh.tmdbsample.data.source.remote.TmdbApi;
import com.daksh.tmdbsample.di.scope.ActivityScope;
import com.daksh.tmdbsample.movielist.MovieListContract;
import com.daksh.tmdbsample.movielist.MovieListPresenter;

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

    @Provides
    @ActivityScope
    MovieListContract.Presenter provideMovieListPresenter(TmdbApi api, AppSettings appSettings) {
        return new MovieListPresenter(view, api, appSettings);
    }
}
