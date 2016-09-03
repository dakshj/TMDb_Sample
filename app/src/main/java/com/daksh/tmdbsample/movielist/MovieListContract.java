package com.daksh.tmdbsample.movielist;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.base.BaseView;
import com.daksh.tmdbsample.data.intdef.SortOrder;
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

        void showSortOrderSelector(SortOrder currentSortOrder);
    }

    interface Presenter {

        void loadMovies(Integer page);

        void openMovieDetails(@NonNull Movie movie);

        void setSortOrder(SortOrder sortOrder);
    }
}
