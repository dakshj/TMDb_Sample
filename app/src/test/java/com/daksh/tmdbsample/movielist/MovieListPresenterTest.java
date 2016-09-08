package com.daksh.tmdbsample.movielist;

import android.os.Build;

import com.daksh.tmdbsample.BuildConfig;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.MovieListApiResponse;
import com.daksh.tmdbsample.data.source.local.AppSettings;
import com.daksh.tmdbsample.data.source.remote.TmdbApi;
import com.daksh.tmdbsample.di.TestInjector;
import com.daksh.tmdbsample.util.MockResponse;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import rx.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by daksh on 08-09-2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = Build.VERSION_CODES.M)
public class MovieListPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MovieListContract.View view;

    @Mock
    TmdbApi api;

    @Mock
    AppSettings appSettings;

    @Inject
    Gson gson;

    @InjectMocks
    MovieListPresenter presenter;

    @Before
    public void setUp() {
        TestInjector.INSTANCE.getAppComponent().inject(this);
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void testStartPresenterWithPopularSortOrder() {
        when(appSettings.getSortOrder()).thenReturn(new SortOrder(SortOrder.POPULAR));

        MovieListApiResponse popularMovies = MockResponse.getPopularMovies(gson);

        when(api.popular(null))
                .thenReturn(Single.just(popularMovies));

        presenter.start();

        verify(api).popular(null);
        verify(view).showLoading();
    }

    @Test
    public void testStartPresenterWithTopRatedSortOrder() {
        when(appSettings.getSortOrder()).thenReturn(new SortOrder(SortOrder.TOP_RATED));

        MovieListApiResponse topRatedMovies = MockResponse.getTopRatedMovies(gson);

        when(api.topRated(null))
                .thenReturn(Single.just(topRatedMovies));

        presenter.start();

        verify(api).topRated(null);
        verify(view).showLoading();
    }
}
