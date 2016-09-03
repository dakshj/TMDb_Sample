package com.daksh.tmdbsample.movielist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseActivity;
import com.daksh.tmdbsample.data.intdef.SortOrder;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.ActivityMovieListBinding;
import com.daksh.tmdbsample.di.AppComponent;
import com.daksh.tmdbsample.moviedetail.MovieDetailActivity;
import com.daksh.tmdbsample.moviedetail.MovieDetailFragment;

import java.util.List;

public class MovieListActivity extends BaseActivity implements MovieListContract.View {

    private boolean twoPane;
    private ActivityMovieListBinding B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);

        setSupportActionBar(B.toolbar);
        B.toolbar.setTitle(getTitle());

        if (B.layoutMovieList.movieDetailContainer != null) {
            setTwoPane(true);
        }
    }

    @Override
    public void injectActivity(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    public void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showMovies(List<Movie> movies) {

    }

    @Override
    public void addMovies(List<Movie> movies) {

    }

    @Override
    public void showSortOrderSelector(SortOrder currentSortOrder) {

    }

    @Override
    public void setPresenter(MovieListContract.Presenter presenter) {

    }

    private MovieListAdapter getAdapter() {
        return new MovieListAdapter(new MovieListAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(Movie movie) {
                if (isTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MovieDetailFragment.ARG_MOVIE, movie);
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movieDetailContainer, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_MOVIE, movie);
                    startActivity(intent);
                }
            }
        }, isTwoPane());
    }
}
