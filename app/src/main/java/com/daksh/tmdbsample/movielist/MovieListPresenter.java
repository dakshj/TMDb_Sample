package com.daksh.tmdbsample.movielist;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.daksh.tmdbsample.base.BasePresenterImpl;
import com.daksh.tmdbsample.data.intdef.ListLoadType;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.data.model.MovieListApiResponse;
import com.daksh.tmdbsample.data.source.local.AppSettings;
import com.daksh.tmdbsample.data.source.remote.TmdbApi;
import com.daksh.tmdbsample.databinding.MovieListItemBinding;
import com.daksh.tmdbsample.di.scope.ActivityScope;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        loadMovies(getView().getSearchQuery(), null, new ListLoadType(ListLoadType.FIRST));
    }

    private void loadMovies(final String query, final Integer page,
            final ListLoadType listLoadType) {
        Single<MovieListApiResponse> single = null;

        if (TextUtils.isEmpty(query)) {
            SortOrder sortOrder = appSettings.getSortOrder();

            switch (sortOrder.value) {
                case SortOrder.POPULAR:
                    single = api.popular(page);
                    break;

                case SortOrder.TOP_RATED:
                    single = api.topRated(page);
                    break;
            }
        } else {
            try {
                single = api.search(URLEncoder.encode(query, "UTF-8"), page);
            } catch (UnsupportedEncodingException ignored) {
                getView().showError();
            }
        }

        if (single == null) {
            getView().showError();
            return;
        }

        single.subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    if (listLoadType.value == ListLoadType.FIRST) {
                        getView().showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<MovieListApiResponse>() {
                    @Override
                    public void onSuccess(MovieListApiResponse response) {
                        if (response.getTotalResults() == 0) {
                            getView().showEmpty();
                            return;
                        }

                        switch (listLoadType.value) {
                            case ListLoadType.FIRST:
                                getView().showMovies(response.getMovies());
                                getView().scrollListToTop();
                                getView().dismissMovieDetails();
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

            if (getView().isSearchOpened()) {
                getView().dismissSearch();
            }

            loadMovies(null, null, new ListLoadType(ListLoadType.FIRST));
        }
    }

    @Override
    public void startSwipeRefresh() {
        loadMovies(getView().getSearchQuery(), null, new ListLoadType(ListLoadType.SWIPE_REFRESH));
    }

    @Override
    public void startNextPageLoad(Integer page) {
        loadMovies(getView().getSearchQuery(), page, new ListLoadType(ListLoadType.ENDLESS_SCROLL));
    }
}
