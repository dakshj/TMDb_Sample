package com.daksh.tmdbsample.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by daksh on 03-Sep-16.
 */
public class Movie {

    private final String title;

    @SerializedName("poster_path")
    private final String imageUrl;

    @SerializedName("overview")
    private final String synopsis;

    @SerializedName("vote_average")
    private final double userRating;

    private final Date releaseDate;

    public Movie(String title, String imageUrl, String synopsis, double userRating, Date releaseDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
}
