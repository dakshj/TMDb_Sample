package com.daksh.tmdbsample.data.intdef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daksh on 03-Sep-16.
 */

public class SortOrder {

    public static final int POPULAR_DESCENDING = 0;
    public static final int POPULAR_ASCENDING = 1;
    public static final int RATING_DESCENDING = 2;
    public static final int RATING_ASCENDING = 3;

    public final int sortOrder;

    public SortOrder(@SortOrderDef int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POPULAR_DESCENDING, POPULAR_ASCENDING, RATING_DESCENDING, RATING_ASCENDING})
    public @interface SortOrderDef {
    }
}
