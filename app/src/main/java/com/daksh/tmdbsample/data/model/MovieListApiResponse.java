package com.daksh.tmdbsample.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daksh on 06-Sep-16.
 */
public class MovieListApiResponse {

    private final long page;
    @SerializedName("results")
    private final List<Movie> movieList;
    private final long totalResults;
    private final long totalPages;

    public MovieListApiResponse(long page, List<Movie> movieList, long totalResults, long totalPages) {
        this.page = page;
        this.movieList = movieList;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public long getPage() {
        return page;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }
}
