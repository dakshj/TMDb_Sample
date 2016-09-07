package com.daksh.tmdbsample.movielist;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseActivity;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.ActivityMovieListBinding;
import com.daksh.tmdbsample.databinding.ActivityMovieListSortDialogBinding;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.di.module.MovieListModule;

import java.util.List;

import javax.inject.Inject;

public class MovieListActivity extends BaseActivity implements MovieListContract.View {

    private static final int GRID_COLUMNS = 2;

    @Inject
    MovieListPresenter presenter;

    private boolean twoPane;
    private ActivityMovieListBinding B;
    private MovieListAdapter adapter;

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

        presenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_movie_list_menu, menu);
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

    @NonNull
    @Override
    public String getTag() {
        return "MovieListActivity";
    }

    private void setUpGrid() {
        adapter = new MovieListAdapter(new MovieListAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(Movie movie) {
                presenter.openMovieDetails(movie);
            }
        });

        B.layoutMovieList.listMovie.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
        B.layoutMovieList.listMovie.setAdapter(adapter);

        B.layoutMovieList.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.startSwipeRefresh();
            }
        });
    }

    @Override
    public void injectActivity(AppComponent appComponent) {
        appComponent.getMovieListComponent(new MovieListModule(this)).inject(this);
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    public void setTwoPane(boolean twoPane) {
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
                .setTitle(R.string.select_sort_order)
                .setView(binding.getRoot())
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    @Override
    public void showMovieDetails(Movie movie) {
        Toast.makeText(this, "Clicked on " + movie.getTitle() + "!", Toast.LENGTH_SHORT).show();
        //TODO go to movie details
        //if (isTwoPane()) {
        //    Bundle arguments = new Bundle();
        //    arguments.putParcelable(MovieDetailFragment.ARG_MOVIE, movie);
        //    MovieDetailFragment fragment = new MovieDetailFragment();
        //    fragment.setArguments(arguments);
        //    getSupportFragmentManager().beginTransaction()
        //            .replace(R.id.movieDetailContainer, fragment)
        //            .commit();
        //} else {
        //    Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        //    intent.putExtra(MovieDetailFragment.ARG_MOVIE, movie);
        //    startActivity(intent);
        //}
    }

    @Override
    public void stopSwipeRefresh() {
        B.layoutMovieList.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void stopInfiniteScroll() {
        //TODO
    }

    @Override
    public void scrollListToTop() {
        if (adapter.getItemCount() > 0) {
            B.layoutMovieList.listMovie.scrollToPosition(0);
        }
    }
}
