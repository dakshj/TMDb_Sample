package com.daksh.tmdbsample.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseActivity;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.ActivityMovieDetailBinding;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.movielist.MovieListActivity;
import com.daksh.tmdbsample.util.Logger;

import icepick.State;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    @State
    Movie movie;

    private ActivityMovieDetailBinding B;

    /**
     * Used for starting {@link MovieDetailActivity} with no transitions.
     *
     * @param context Context using which {@link MovieDetailActivity} can be started.
     * @param movie   The {@link Movie} to be loaded.
     */
    public static void start(@NonNull Context context, @NonNull Movie movie) {
        context.startActivity(getCallingIntent(context, movie));
    }

    /**
     * Used for starting {@link MovieDetailActivity} with Shared Element Transition of the
     * Poster Image.
     *
     * @param context                       Context using which {@link MovieDetailActivity} can be started.
     * @param movie                         The {@link Movie} to be loaded.
     * @param profileImageTransitionOptions The Shared Element Transition for the Poster Image.
     */
    public static void start(@NonNull Context context, @NonNull Movie movie,
            @Nullable ActivityOptionsCompat profileImageTransitionOptions) {
        Intent intent = getCallingIntent(context, movie);

        if (profileImageTransitionOptions != null) {
            context.startActivity(intent, profileImageTransitionOptions.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * Intent from which {@link MovieDetailActivity} can be started.
     *
     * @param context Context using which {@link MovieDetailActivity} can be started.
     * @param movie   The {@link Movie} to be loaded.
     * @return Calling Intent for {@link MovieDetailActivity}.
     */
    public static Intent getCallingIntent(@NonNull Context context, @NonNull Movie movie) {
        return new Intent(context, MovieDetailActivity.class)
                .putExtra(MovieDetailFragment.ARG_MOVIE, movie);
    }

    @Override
    public void injectActivity(AppComponent appComponent) {
        // Not needed because injection is performed and injected dependencies are
        // used in MovieDetailFragment.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        setSupportActionBar(B.toolbar);

        B.toolbar.setTitle("");
        setSupportActionBar(B.toolbar);

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
            movie = getIntent().getParcelableExtra(MovieDetailFragment.ARG_MOVIE);

            if (movie == null) {
                Logger.errorLog("Movie is null in MovieDetailActivity!");
                finish();
                return;
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movieDetailContainer, MovieDetailFragment.get(movie))
                    .commit();
        }

        if (movie != null) {
            B.setMovie(movie);
        }
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
