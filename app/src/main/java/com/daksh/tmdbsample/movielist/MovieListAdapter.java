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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daksh on 03-Sep-16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    @NonNull
    private final ItemClickListener itemClickListener;
    private List<Movie> movies;

    public MovieListAdapter(@NonNull ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        movies = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MovieListItemBinding B =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.movie_list_item, parent, false);

        /**
         * Set height of poster image dynamically to avoid problem of disappearing neighbouring
         * images when an image is not yet loaded.
         */
        B.imagePoster.post(() -> B.imagePoster.getLayoutParams().height =
                (int) ((double) B.imagePoster.getWidth() * Movie.POSTER_IMAGE_ASPECT_RATIO));

        return new ViewHolder(B, itemClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.movie = movies.get(position);
        holder.B.setMovie(holder.movie);
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }

        return movies.size();
    }

    public void setMovies(@NonNull List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(@NonNull List<Movie> movies) {
        int currentSize = getItemCount();
        this.movies.addAll(movies);
        notifyItemRangeInserted(currentSize, movies.size());
    }

    public interface ItemClickListener {
        void onItemClicked(Movie movie);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @NonNull
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
