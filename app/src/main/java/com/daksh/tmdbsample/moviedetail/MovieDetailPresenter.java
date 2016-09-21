package com.daksh.tmdbsample.moviedetail;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.base.BasePresenterImpl;

/**
 * Created by daksh on 09-Sep-16.
 */
public class MovieDetailPresenter extends BasePresenterImpl<MovieDetailContract.View>
        implements MovieDetailContract.Presenter {

    public MovieDetailPresenter(@NonNull MovieDetailContract.View view) {
        attachView(view);
    }

    @Override
    public void start() {

    }
}
