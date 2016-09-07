package com.daksh.tmdbsample.di.component;

import com.daksh.tmdbsample.di.module.MovieListModule;
import com.daksh.tmdbsample.di.scope.ActivityScope;
import com.daksh.tmdbsample.movielist.MovieListActivity;

import dagger.Subcomponent;

/**
 * Created by daksh on 07-09-2016.
 */

@ActivityScope
@Subcomponent(modules = {
        MovieListModule.class
})
public interface MovieListComponent {

    void inject(MovieListActivity movieListActivity);
}
