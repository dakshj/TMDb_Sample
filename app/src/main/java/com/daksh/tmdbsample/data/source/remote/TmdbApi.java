package com.daksh.tmdbsample.data.source.remote;

import com.daksh.tmdbsample.data.model.MovieListApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface TmdbApi {

    @GET("/movie/popular")
    Single<MovieListApiResponse> popular(@Query("page") Integer page);

    @GET("/movie/top_rated")
    Single<MovieListApiResponse> topRated(@Query("page") Integer page);
}
