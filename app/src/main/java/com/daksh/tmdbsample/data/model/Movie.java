package com.daksh.tmdbsample.data.model;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static final String USER_RATING_APPEND = " / 10";

    /**
     * (Height / Width) Aspect Ratio of Poster Image
     */
    public static final double POSTER_IMAGE_ASPECT_RATIO = 1.5;

    /**
     * (Width / Height) Aspect Ratio of Backdrop Image
     */
    public static final double BACKDROP_IMAGE_ASPECT_RATIO = 1.77;

    /**
     * Poster Image Quality in terms of width in px
     */
    private static final String POSTER_IMAGE_QUALITY = "w500";

    /**
     * Backdrop Image Quality in terms of width in px
     */
    private static final String BACKDROP_IMAGE_QUALITY = "w1280";

    /**
     * Image's Base URL
     */
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    private final String title;

    @SerializedName("poster_path")
    private final String posterImageUrl;

    @SerializedName("backdrop_path")
    private final String backdropImageUrl;

    @SerializedName("overview")
    private final String synopsis;

    @SerializedName("vote_average")
    private final double userRating;

    private final Date releaseDate;

    public Movie(String title, String posterImageUrl, String backdropImageUrl, String synopsis,
            double userRating, Date releaseDate) {
        this.title = title;
        this.posterImageUrl = posterImageUrl;
        this.backdropImageUrl = backdropImageUrl;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.posterImageUrl = in.readString();
        this.backdropImageUrl = in.readString();
        this.synopsis = in.readString();
        this.userRating = in.readDouble();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
    }

    /**
     * Function used by DataBinding to load the poster image into an ImageView
     * by calling {@link Movie#posterImageUrl}
     */
    @BindingAdapter({"posterImageUrl"})
    public static void loadPosterImage(ImageView view, String imageUrl) {
        loadImage(view, IMAGE_BASE_URL + POSTER_IMAGE_QUALITY + imageUrl);
    }

    /**
     * Function used by DataBinding to load the backdrop image into an ImageView
     * by calling {@link Movie#backdropImageUrl}
     */
    @BindingAdapter({"backdropImageUrl"})
    public static void loadBackdropImage(ImageView view, String imageUrl) {
        loadImage(view, IMAGE_BASE_URL + BACKDROP_IMAGE_QUALITY + imageUrl);
    }

    private static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    /**
     * Function used by DataBinding to load the user rating into a TextView
     * by calling {@link Movie#userRating}
     */
    @BindingAdapter({"userRating"})
    public static void loadUserRating(TextView view, double userRating) {
        view.setText(getFormattedUserRating(userRating));
    }

    /**
     * @param userRating The {@link Movie#userRating} of a {@link Movie}.
     * @return A formatted String of the {@link Movie#userRating} for displaying purposes.
     */
    public static String getFormattedUserRating(double userRating) {
        double rounded = round(userRating, 2);

        String formatted;

        // Used to remove any redundant decimal zeroes.
        if (rounded == (long) rounded) {
            formatted = String.format(Locale.getDefault(), "%d", (long) rounded);
        } else {
            formatted = String.format("%s", rounded);
        }

        return formatted.concat(USER_RATING_APPEND);
    }

    /**
     * Safely round a double to required places.
     * <br/>
     * <strong>Read:</strong> http://stackoverflow.com/a/2808648/1558717
     *
     * @param value  The value to be rounded
     * @param places The decimal places to round the value to
     * @return The rounded value
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Function used by DataBinding to load the release date into a TextView
     * by calling {@link Movie#releaseDate}
     */
    @BindingAdapter({"releaseDate"})
    public static void loadReleaseDate(TextView view, Date releaseDate) {
        view.setText(getFormattedReleaseDate(releaseDate));
    }

    /**
     * @param releaseDate The {@link Movie#releaseDate} of a {@link Movie}.
     * @return A formatted String of the {@link Movie#releaseDate} for displaying purposes.
     */
    public static String getFormattedReleaseDate(Date releaseDate) {
        return new SimpleDateFormat("d MMM, y", Locale.getDefault()).format(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public String getBackdropImageUrl() {
        return backdropImageUrl;
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
        dest.writeString(this.posterImageUrl);
        dest.writeString(this.backdropImageUrl);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.userRating);
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
    }
}
