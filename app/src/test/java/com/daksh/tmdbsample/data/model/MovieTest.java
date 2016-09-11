package com.daksh.tmdbsample.data.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by daksh on 12-Sep-16.
 */
public class MovieTest {

    @Test
    public void testGetFormattedUserRating() throws Exception {
        assertEquals("10".concat(Movie.USER_RATING_APPEND),
                Movie.getFormattedUserRating(9.999999));

        assertNotEquals("1".concat(Movie.USER_RATING_APPEND),
                Movie.getFormattedUserRating(0.99));

        assertEquals("1".concat(Movie.USER_RATING_APPEND),
                Movie.getFormattedUserRating(0.999));

        assertEquals("9.45".concat(Movie.USER_RATING_APPEND),
                Movie.getFormattedUserRating(9.450001));

        assertEquals("0".concat(Movie.USER_RATING_APPEND),
                Movie.getFormattedUserRating(0.0000000001));

        assertEquals("8.46".concat(Movie.USER_RATING_APPEND),
                Movie.getFormattedUserRating(8.455));
    }
}
