package com.daksh.tmdbsample.moviedetail;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.base.BasePresenterImpl;
import com.daksh.tmdbsample.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * Created by daksh on 09-Sep-16.
 */

@ActivityScope
public class MovieDetailPresenter extends BasePresenterImpl<MovieDetailContract.View> {

    @Inject
    public MovieDetailPresenter(@NonNull MovieDetailContract.View view) {
        attachView(view);
    }

    @Override
    public void start() {

    }
}
