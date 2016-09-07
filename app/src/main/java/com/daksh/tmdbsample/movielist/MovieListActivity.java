package com.daksh.tmdbsample.movielist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseActivity;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.ActivityMovieListBinding;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.di.module.MovieListModule;

import java.util.List;

import javax.inject.Inject;

public class MovieListActivity
        extends BaseActivity<MovieListPresenter> implements MovieListContract.View {

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
    public MovieListPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void setPresenter(MovieListPresenter presenter) {
        this.presenter = presenter;
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

    }

    @Override
    public void showMovieDetails(Movie movie) {
        Toast.makeText(this, "Clicked on a Movie!", Toast.LENGTH_SHORT).show();
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
}
