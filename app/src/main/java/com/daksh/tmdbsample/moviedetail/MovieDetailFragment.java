package com.daksh.tmdbsample.moviedetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.base.BaseFragment;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.MovieDetailBinding;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.di.module.MovieDetailModule;
import com.daksh.tmdbsample.movielist.MovieListActivity;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends BaseFragment implements MovieDetailContract.View {

    public static final String ARG_MOVIE = "movie";

    private Movie movie;
    private MovieDetailBinding B;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    public static MovieDetailFragment get(@NonNull final Movie movie) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieDetailFragment.ARG_MOVIE, movie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE)) {
            movie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public void injectFragment(AppComponent applicationComponent) {
        applicationComponent.getMovieDetailComponent(new MovieDetailModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        B = DataBindingUtil.inflate(inflater, R.layout.movie_detail, container, false);

        if (movie != null) {
            B.setMovie(movie);
        }

        return B.getRoot();
    }
}
