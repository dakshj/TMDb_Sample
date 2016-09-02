package com.daksh.tmdbsample.data.remote;

import com.daksh.tmdbsample.data.model.Movie;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface MovieDbApi {

    @GET("/movie/popular")
    Observable<List<Movie>> popular();

    @GET("/movie/top_rated")
    Observable<List<Movie>> topRated();
}
