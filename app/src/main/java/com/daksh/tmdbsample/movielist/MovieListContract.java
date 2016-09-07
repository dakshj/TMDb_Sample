package com.daksh.tmdbsample.movielist;

import com.daksh.tmdbsample.base.BasePresenter;
import com.daksh.tmdbsample.base.BaseView;
import com.daksh.tmdbsample.data.intdef.ListLoadType;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;

import java.util.List;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface MovieListContract {

    interface View extends BaseView {

        void showLoading();

        void showError();

        void showMovies(List<Movie> movies);

        void addMovies(List<Movie> movies);

        void showSortOrderSelector(SortOrder currentSortOrder);

        void showMovieDetails(Movie movie);

        void stopSwipeRefresh();

        void stopInfiniteScroll();

        void scrollListToTop();
    }

    interface Presenter extends BasePresenter<View> {

        void loadMovies(Integer page, SortOrder sortOrder, ListLoadType listLoadType);

        void openSortOrderSelector();

        void openMovieDetails(Movie movie);

        void setSortOrder(SortOrder sortOrder);

        void startSwipeRefresh();
    }
}
