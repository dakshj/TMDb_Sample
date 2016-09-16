package com.daksh.tmdbsample.movielist;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.transition.ChangeImageTransform;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseActivity;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.ActivityMovieListBinding;
import com.daksh.tmdbsample.databinding.ActivityMovieListSortDialogBinding;
import com.daksh.tmdbsample.databinding.MovieListItemBinding;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.di.module.MovieListModule;
import com.daksh.tmdbsample.moviedetail.MovieDetailActivity;
import com.daksh.tmdbsample.moviedetail.MovieDetailFragment;
import com.daksh.tmdbsample.util.EndlessRecyclerViewScrollListener;
import com.jakewharton.rxbinding.support.v4.view.RxMenuItemCompat;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.view.MenuItemActionViewEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import icepick.State;
import rx.android.schedulers.AndroidSchedulers;

public class MovieListActivity extends BaseActivity implements MovieListContract.View {

    private static final int GRID_COLUMNS = 2;
    private static final String STATE_RECYCLER_VIEW = "STATE_RECYCLER_VIEW";

    @Inject
    MovieListContract.Presenter presenter;

    @State
    ArrayList<Movie> movies;

    @State
    Movie selectedMovie;

    @State
    int currentPageIndex = -1;

    @State
    String searchQuery = "";

    private boolean twoPane;
    private ActivityMovieListBinding B;
    private MovieListAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private MovieDetailFragment fragmentTwoPane;
    private MenuItem itemSearch;

    @Override
    public void injectActivity(AppComponent appComponent) {
        appComponent.getMovieListComponent(new MovieListModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);

        if (B.layoutMovieList.movieDetailContainer != null) {
            setTwoPane(true);
        }

        setSupportActionBar(B.toolbar);
        B.toolbar.setTitle(getTitle());

        setUpGrid();

        setUpErrorLayout();

        if (savedInstanceState == null || getMovies() == null) {
            presenter.start();
        } else {
            reloadStates(savedInstanceState);
        }
    }

    private void reloadStates(@NonNull Bundle savedInstanceState) {
        // Restore data set
        adapter.setMovies(getMovies());

        // Restore movie details if in two-pane layout
        if (isTwoPane()) {
            if (getSelectedMovie() != null) {
                loadTwoPaneMovieDetails(getSelectedMovie());
            }
        }

        // Restore number of pages already loaded via endless scrolling
        if (currentPageIndex != -1) {
            scrollListener.setCurrentPageIndexAfterConfigurationChange(currentPageIndex);
        }

        // Restore RecyclerView's scroll state
        // postDelayed with a 100ms delay worked more reliably that post
        // Also, the delay is not visible
        if (savedInstanceState.containsKey(STATE_RECYCLER_VIEW)) {
            Parcelable state = savedInstanceState.getParcelable(STATE_RECYCLER_VIEW);

            if (state != null) {
                B.layoutMovieList.listMovie.postDelayed(() ->
                        B.layoutMovieList.listMovie.getLayoutManager()
                                .onRestoreInstanceState(state), 100);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_movie_list_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        itemSearch = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Make the SearchView occupy the entire available width on the Toolbar
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Restore state of SearchView after configuration changes
        if (!TextUtils.isEmpty(getSearchQuery())) {
            itemSearch.expandActionView();
            searchView.setQuery(getSearchQuery(), false);
        }

        // Perform a search automatically after 1 second of no typing
        RxSearchView.queryTextChanges(searchView)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    if (!getSearchQuery().equals(charSequence.toString())) {
                        setSearchQuery(charSequence.toString());
                        presenter.start();
                    }
                });

        // Reload default list after the SearchView is closed
        RxMenuItemCompat.actionViewEvents(itemSearch)
                .subscribe(menuItemActionViewEvent -> {
                    if (menuItemActionViewEvent.kind() == MenuItemActionViewEvent.Kind.COLLAPSE &&
                            !TextUtils.isEmpty(getSearchQuery())) {
                        presenter.start();
                    }
                });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                presenter.openSortOrderSelector();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Close the SearchView if open
        if (itemSearch != null && !itemSearch.isActionViewExpanded()) {
            itemSearch.collapseActionView();
            return;
        }

        // Close Movie Details if visible (in two-pane mode)
        if (isTwoPane() && fragmentTwoPane != null) {
            dismissMovieDetails();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_RECYCLER_VIEW,
                B.layoutMovieList.listMovie.getLayoutManager().onSaveInstanceState());

        super.onSaveInstanceState(outState);
    }

    private void setUpGrid() {
        adapter = new MovieListAdapter((movie, B) -> presenter.openMovieDetails(movie, B));

        B.layoutMovieList.listMovie.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
        B.layoutMovieList.listMovie.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(
                (GridLayoutManager) B.layoutMovieList.listMovie.getLayoutManager()) {
            @Override
            public void onLoadMore(int pageIndex, int totalItemCount) {
                currentPageIndex = pageIndex;
                presenter.startNextPageLoad(pageIndex + 1);
            }
        };

        B.layoutMovieList.listMovie.addOnScrollListener(scrollListener);

        B.layoutMovieList.swipeRefreshLayout.setOnRefreshListener(() ->
                presenter.startSwipeRefresh());
    }

    private void setUpErrorLayout() {
        B.buttonError.setOnClickListener(view -> presenter.start());
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    private void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
    }

    @Override
    public void showLoading() {
        B.idViewAnimator.setDisplayedChildId(B.loading.getId());
    }

    @Override
    public void showError() {
        B.idViewAnimator.setDisplayedChildId(B.error.getId());
    }

    @Override
    public void showMovies(List<Movie> movies) {
        B.idViewAnimator.setDisplayedChildId(B.content.getId());

        setMovies(movies);

        adapter.setMovies(movies);
    }

    @Override
    public void addMovies(List<Movie> movies) {
        adapter.addMovies(movies);
    }

    @Override
    public void showSortOrderSelector(SortOrder currentSortOrder) {
        final ActivityMovieListSortDialogBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(this),
                        R.layout.activity_movie_list_sort_dialog, null, false);

        switch (currentSortOrder.value) {
            case SortOrder.POPULAR:
                binding.radioGroupSorting.check(R.id.radioPopular);
                break;

            case SortOrder.TOP_RATED:
                binding.radioGroupSorting.check(R.id.radioTopRated);
                break;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_sort_order_title)
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    SortOrder sortOrder = null;

                    switch (binding.radioGroupSorting.getCheckedRadioButtonId()) {
                        case R.id.radioPopular:
                            sortOrder = new SortOrder(SortOrder.POPULAR);
                            break;

                        case R.id.radioTopRated:
                            sortOrder = new SortOrder(SortOrder.TOP_RATED);
                            break;
                    }

                    if (sortOrder != null) {
                        presenter.setSortOrder(sortOrder);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showMovieDetails(Movie movie, MovieListItemBinding B) {
        setSelectedMovie(movie);

        if (isTwoPane()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fragmentTwoPane = MovieDetailFragment.newInstance(movie);

                fragmentTwoPane.setSharedElementEnterTransition(new ChangeImageTransform());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movieDetailContainer, fragmentTwoPane)
                        .addToBackStack(null)
                        .addSharedElement(B.imagePoster, getString(R.string.poster_image_transition))
                        .commit();
            } else {
                loadTwoPaneMovieDetails(movie);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //Shared Element Transition of Poster Image from Master List to Detail screen
                ActivityOptionsCompat profileImageTransitionOptions = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, B.imagePoster,
                                getString(R.string.poster_image_transition));

                MovieDetailActivity.start(this, movie, profileImageTransitionOptions);
            } else {
                MovieDetailActivity.start(this, movie);
            }
        }
    }

    private void loadTwoPaneMovieDetails(@NonNull Movie movie) {
        fragmentTwoPane = MovieDetailFragment.newInstance(movie);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movieDetailContainer, fragmentTwoPane)
                .commit();
    }

    @Override
    public void stopSwipeRefresh() {
        B.layoutMovieList.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void stopNextPageLoad() {
        //Not required unless a loading footer is shown in the RecyclerView
    }

    @Override
    public void scrollListToTop() {
        if (adapter.getItemCount() > 0) {
            B.layoutMovieList.listMovie.smoothScrollToPosition(0);
        }
    }

    @Override
    public void pageLoadingFailed(Integer page) {
        if (page != null && scrollListener != null) {
            currentPageIndex = scrollListener.loadingFailed(page - 1);
        }
    }

    @Override
    public void setTotalListPages(long totalPages) {
        if (scrollListener != null) {
            scrollListener.setLastPageIndex(totalPages - 1);
        }
    }

    @Override
    public void dismissMovieDetails() {
        if (isTwoPane() && fragmentTwoPane != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(fragmentTwoPane)
                    .commit();

            fragmentTwoPane = null;
        }
    }

    private List<Movie> getMovies() {
        return movies;
    }

    private void setMovies(List<Movie> movies) {
        this.movies = new ArrayList<>();
        this.movies.addAll(movies);
    }

    private Movie getSelectedMovie() {
        return selectedMovie;
    }

    private void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    @Override
    public boolean isSearchOpened() {
        return itemSearch != null && itemSearch.isActionViewExpanded();
    }

    @Override
    public void dismissSearch() {
        setSearchQuery("");

        if (itemSearch != null && itemSearch.isActionViewExpanded()) {
            itemSearch.collapseActionView();
        }
    }

    @Override
    public String getSearchQuery() {
        return searchQuery;
    }

    private void setSearchQuery(@NonNull String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
