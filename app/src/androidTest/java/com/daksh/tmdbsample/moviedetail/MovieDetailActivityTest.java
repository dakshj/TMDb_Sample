package com.daksh.tmdbsample.moviedetail;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.daksh.tmdbsample.R;
import com.daksh.tmdbsample.data.model.Movie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by daksh on 12-Sep-16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieDetailActivityTest {

    // The third parameter is set to false if we do not want the Activity to be
    // launched automatically.
    @Rule
    public final ActivityTestRule<MovieDetailActivity> rule =
            new ActivityTestRule<>(MovieDetailActivity.class, false, false);
    private Movie movie;

    /**
     * Sets up a dummy {@link Movie}.
     */
    @Before
    public void setUp() {
        movie = new Movie("Test", "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
                "/6bbZ6XyvgfjhQwbplnUh1LSj1ky.jpg", "This is a test movie!",
                9.4599999, Calendar.getInstance().getTime());
    }

    /**
     * Launches the Activity. Needs to be called before any @Test is run!
     */
    private void launchActivity() {
        rule.launchActivity(new Intent().putExtra(MovieDetailFragment.ARG_MOVIE, movie));
    }

    /**
     * Test to cross-check values in the {@link Movie} object against
     * values that have been loaded into the widgets of {@link MovieDetailActivity}.
     * <p>
     * All values are String comparisons post-formatting.
     */
    @Test
    public void checkDataBindingValues() {
        launchActivity();

        onView(withId(R.id.textUserRating))
                .check(matches(withText(Movie.getFormattedUserRating(movie.getUserRating()))));

        onView(withId(R.id.textReleaseDate))
                .check(matches(withText(Movie.getFormattedReleaseDate(movie.getReleaseDate()))));

        onView(withId(R.id.textSynopsis))
                .check(matches(withText(movie.getSynopsis())));
    }
}
