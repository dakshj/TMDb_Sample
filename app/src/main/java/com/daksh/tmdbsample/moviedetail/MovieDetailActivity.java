package com.daksh.tmdbsample.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseActivity;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.ActivityMovieDetailBinding;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.movielist.MovieListActivity;
import com.daksh.tmdbsample.util.Logger;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    private ActivityMovieDetailBinding B;

    public static void start(@NonNull Context context, @NonNull Movie movie) {
        context.startActivity(
                new Intent(context, MovieDetailActivity.class)
                        .putExtra(MovieDetailFragment.ARG_MOVIE, movie)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity.
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        if (savedInstanceState == null) {
            Movie movie = getIntent().getParcelableExtra(MovieDetailFragment.ARG_MOVIE);

            if (movie == null) {
                Logger.errorLog("Movie is null in MovieDetailActivity!");
                finish();
                return;
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movieDetailContainer, MovieDetailFragment.get(movie))
                    .commit();

            B.setMovie(movie);
        }
    }

    @Override
    public void injectActivity(AppComponent appComponent) {
        //Not needed
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
