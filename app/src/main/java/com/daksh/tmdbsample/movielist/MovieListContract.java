package com.daksh.tmdbsample.movielist;

import com.daksh.tmdbsample.base.BasePresenter;
import com.daksh.tmdbsample.base.BaseView;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.MovieListItemBinding;

import java.util.List;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface MovieListContract {

    interface View extends BaseView {

        void showLoading();

        void showError();

        void showEmpty();

        void showMovies(List<Movie> movies);

        void addMovies(List<Movie> movies);

        void showSortOrderSelector(SortOrder currentSortOrder);

        void showMovieDetails(Movie movie, MovieListItemBinding B);

        void stopSwipeRefresh();

        void stopNextPageLoad();

        void scrollListToTop();

        void pageLoadingFailed(Integer page);

        void setTotalListPages(long totalPages);

        void dismissMovieDetails();

        boolean isSearchOpened();

        void dismissSearch();

        String getSearchQuery();
    }

    interface Presenter extends BasePresenter<View> {

        void openSortOrderSelector();

        void openMovieDetails(Movie movie, MovieListItemBinding B);

        void setSortOrder(SortOrder sortOrder);

        void startSwipeRefresh();

        void startNextPageLoad(Integer page);
    }
}
