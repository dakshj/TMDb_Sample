package com.daksh.tmdbsample.data.model;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by daksh on 03-Sep-16.
 */
public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
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

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.synopsis = in.readString();
        this.userRating = in.readDouble();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        //TODO add placeholder
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.userRating);
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
    }
}
