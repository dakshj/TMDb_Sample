package com.daksh.tmdbsample.di.component;

import com.daksh.tmdbsample.di.module.MovieDetailModule;
import com.daksh.tmdbsample.di.scope.ActivityScope;
import com.daksh.tmdbsample.moviedetail.MovieDetailFragment;

import dagger.Subcomponent;

/**
 * Created by daksh on 07-09-2016.
 */

@ActivityScope
@Subcomponent(modules = {
        MovieDetailModule.class
})
public interface MovieDetailComponent {

    void inject(MovieDetailFragment movieDetailFragment);
}
