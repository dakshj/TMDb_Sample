package com.daksh.tmdbsample.movielist;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.data.intdef.ListLoadType;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.data.model.MovieListApiResponse;
import com.daksh.tmdbsample.data.source.local.AppSettings;
import com.daksh.tmdbsample.data.source.remote.TmdbApi;
import com.daksh.tmdbsample.di.scope.ActivityScope;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by daksh on 03-Sep-16.
 */

@ActivityScope
public class MovieListPresenter implements MovieListContract.Presenter {

    @NonNull
    private final TmdbApi api;
    @NonNull
    private final AppSettings appSettings;

    private WeakReference<MovieListContract.View> viewRef;

    @Inject
    public MovieListPresenter(@NonNull MovieListContract.View view,
            @NonNull TmdbApi api, @NonNull AppSettings appSettings) {
        viewRef = new WeakReference<>(view);
        this.api = api;
        this.appSettings = appSettings;
    }

    @Override
    public void start() {
        loadMovies(null, appSettings.getSortOrder(), new ListLoadType(ListLoadType.FIRST));
    }

    @Override
    public void destroy() {
        viewRef = null;
    }

    private MovieListContract.View getView() throws NullPointerException {
        if (viewRef != null && viewRef.get() != null) {
            return viewRef.get();
        } else {
            throw new NullPointerException("View is unavailable");
        }
    }

    @Override
    public void setView(MovieListContract.View view) {
        viewRef = new WeakReference<>(view);
    }

    @Override
    public void loadMovies(final Integer page, SortOrder sortOrder, final ListLoadType listLoadType) {
        if (page == null) {
            getView().showLoading();
        }

        Single<MovieListApiResponse> single = null;

        switch (sortOrder.sortOrder) {
            case SortOrder.POPULAR:
                single = api.popular(page);
                break;

            case SortOrder.RATING:
                single = api.topRated(page);
                break;
        }

        if (single == null) {
            getView().showError();
            return;
        }

        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<MovieListApiResponse>() {
                    @Override
                    public void onSuccess(MovieListApiResponse response) {
                        if (page == null) {
                            getView().showMovies(response.getMovies());

                            if (listLoadType.listLoadType == ListLoadType.PULL_TO_REFRESH) {
                                getView().stopPullToRefresh();
                            }
                        } else {
                            getView().addMovies(response.getMovies());
                            if (listLoadType.listLoadType == ListLoadType.INFINITE_SCROLL) {
                                getView().stopInfiniteScroll();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        getView().showError();
                    }
                });
    }

    @Override
    public void openSortOrderSelector() {
        getView().showSortOrderSelector(appSettings.getSortOrder());
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {
        getView().showMovieDetails(movie);
    }

    @Override
    public void setSortOrder(@NonNull SortOrder sortOrder) {
        appSettings.setSortOrder(sortOrder);
        loadMovies(null, sortOrder, new ListLoadType(ListLoadType.FIRST));
    }
}
