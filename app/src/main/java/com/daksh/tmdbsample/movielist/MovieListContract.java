package com.daksh.tmdbsample.movielist;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.base.BasePresenter;
import com.daksh.tmdbsample.base.BaseView;
import com.daksh.tmdbsample.data.model.Movie;

import java.util.List;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface MovieListContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void showError();

        void showMovies(List<Movie> movies);

        void addMovies(List<Movie> movies);

        void showSortOrderSelector(int currentSortOrder);
    }

    interface Presenter extends BasePresenter {

        void loadMovies(Integer page);

        void openMovieDetails(@NonNull Movie movie);

        void setSortOrder(int sortOrder);
    }
}
