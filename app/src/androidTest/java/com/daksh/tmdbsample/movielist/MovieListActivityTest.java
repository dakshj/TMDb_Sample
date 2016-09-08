package com.daksh.tmdbsample.movielist;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.daksh.tmdbsample.R;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by daksh on 08-09-2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieListActivityTest extends TestCase {

    MovieListActivity activity;

    @Rule
    public final ActivityTestRule<MovieListActivity> rule =
            new ActivityTestRule<>(MovieListActivity.class);

    @Before
    public void init() {
        activity = rule.getActivity();
    }

    @Test
    public void testShouldOpenSortingAlertDialogWhenSortMenuItemClicked() {
        onView(withId(R.id.menu_sort))
                .perform(click());

        onView(withText(R.string.dialog_sort_order_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testShouldReloadListAfterSelectingOtherRadioButton() {
        //Reset check to Popular
        onView(withId(R.id.menu_sort))
                .perform(click());

        onView(withId(R.id.radioPopular))
                .perform(click());

        onView(withText(R.string.save))
                .perform(click());

        //Test loading Top Rated
        onView(withId(R.id.menu_sort))
                .perform(click());

        onView(withId(R.id.radioTopRated))
                .perform(click());

        onView(withText(R.string.save))
                .perform(click());

        //Requires Internet to pass
        onView(withId(R.id.content))
                .check(matches(isDisplayed()));
    }
}
