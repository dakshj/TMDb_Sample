package com.daksh.tmdbsample.movielist;

import android.support.annotation.NonNull;

import com.daksh.tmdbsample.base.BasePresenterImpl;
import com.daksh.tmdbsample.data.intdef.ListLoadType;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.data.model.MovieListApiResponse;
import com.daksh.tmdbsample.data.source.local.AppSettings;
import com.daksh.tmdbsample.data.source.remote.TmdbApi;
import com.daksh.tmdbsample.databinding.MovieListItemBinding;
import com.daksh.tmdbsample.di.scope.ActivityScope;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by daksh on 03-Sep-16.
 */

@ActivityScope
public class MovieListPresenter extends BasePresenterImpl<MovieListContract.View>
        implements MovieListContract.Presenter {

    @NonNull
    private final TmdbApi api;

    @NonNull
    private final AppSettings appSettings;

    @Inject
    public MovieListPresenter(@NonNull MovieListContract.View view,
            @NonNull TmdbApi api, @NonNull AppSettings appSettings) {
        attachView(view);
        this.api = api;
        this.appSettings = appSettings;
    }

    @Override
    public void start() {
        loadMovies(null, new ListLoadType(ListLoadType.FIRST));
    }

    private void loadMovies(final Integer page, final ListLoadType listLoadType) {
        SortOrder sortOrder = appSettings.getSortOrder();

        if (listLoadType.value == ListLoadType.FIRST) {
            getView().showLoading();
        }

        Single<MovieListApiResponse> single = null;

        switch (sortOrder.value) {
            case SortOrder.POPULAR:
                single = api.popular(page);
                break;

            case SortOrder.TOP_RATED:
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
                        switch (listLoadType.value) {
                            case ListLoadType.FIRST:
                                getView().showMovies(response.getMovies());
                                getView().scrollListToTop();
                                break;

                            case ListLoadType.SWIPE_REFRESH:
                                getView().showMovies(response.getMovies());
                                getView().stopSwipeRefresh();
                                break;

                            case ListLoadType.ENDLESS_SCROLL:
                                getView().addMovies(response.getMovies());
                                getView().stopNextPageLoad();
                                break;
                        }

                        getView().setTotalListPages(response.getTotalPages());
                    }

                    @Override
                    public void onError(Throwable error) {
                        switch (listLoadType.value) {
                            case ListLoadType.FIRST:
                                getView().showError();
                                break;

                            case ListLoadType.SWIPE_REFRESH:
                                getView().stopSwipeRefresh();
                                break;

                            case ListLoadType.ENDLESS_SCROLL:
                                getView().pageLoadingFailed(page);
                                break;
                        }
                    }
                });
    }

    @Override
    public void openSortOrderSelector() {
        getView().showSortOrderSelector(appSettings.getSortOrder());
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie, @NonNull MovieListItemBinding B) {
        getView().showMovieDetails(movie, B);
    }

    @Override
    public void setSortOrder(@NonNull SortOrder sortOrder) {
        SortOrder currentSortOrder = appSettings.getSortOrder();

        if (currentSortOrder.value != sortOrder.value) {
            appSettings.setSortOrder(sortOrder);
            loadMovies(null, new ListLoadType(ListLoadType.FIRST));
        }
    }

    @Override
    public void startSwipeRefresh() {
        loadMovies(null, new ListLoadType(ListLoadType.SWIPE_REFRESH));
    }

    @Override
    public void startNextPageLoad(Integer page) {
        loadMovies(page, new ListLoadType(ListLoadType.ENDLESS_SCROLL));
    }
}
