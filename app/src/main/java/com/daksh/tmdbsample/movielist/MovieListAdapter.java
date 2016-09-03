package com.daksh.tmdbsample.movielist;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.data.model.Movie;
import com.daksh.tmdbsample.databinding.MovieListItemBinding;

import java.util.List;

/**
 * Created by daksh on 03-Sep-16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    @NonNull
    private final ItemClickListener itemClickListener;
    private final boolean twoPane;
    private List<Movie> movies;

    public MovieListAdapter(@NonNull ItemClickListener itemClickListener, boolean twoPane) {
        this.itemClickListener = itemClickListener;
        this.twoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieListItemBinding B =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.movie_list_item, parent, false);
        return new ViewHolder(B, itemClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.movie = movies.get(position);
        //TODO Load image using Picasso
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        //TODO
    }

    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        //TODO
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    public interface ItemClickListener {
        void onItemClicked(Movie movie);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MovieListItemBinding B;
        private final ItemClickListener itemClickListener;
        private Movie movie;

        public ViewHolder(@NonNull MovieListItemBinding B,
                @NonNull ItemClickListener itemClickListener) {
            super(B.getRoot());
            this.B = B;
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClicked(movie);
        }
    }
}
