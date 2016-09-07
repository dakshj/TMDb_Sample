package com.daksh.tmdbsample.data.intdef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daksh on 03-Sep-16.
 */

public class SortOrder {

    public static final int POPULAR = 0;
    public static final int RATING = 1;

    public final int sortOrder;

    public SortOrder(@SortOrderDef int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POPULAR, RATING})
    public @interface SortOrderDef {
    }
}
