package com.daksh.tmdbsample.movielist;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.base.BasePresenter;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;

import java.util.List;

/**
 * Created by daksh on 03-Sep-16.
 */
public class MovieListPresenter extends BasePresenter<List<Movie>, MovieListContract.View>
        implements MovieListContract.Presenter {

    @Override
    protected void updateView() {

    }

    @Override
    public void loadMovies(Integer page) {

    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {

    }

    @Override
    public void setSortOrder(SortOrder sortOrder) {

    }
}
