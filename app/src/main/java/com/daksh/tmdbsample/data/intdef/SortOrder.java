package com.daksh.tmdbsample.data.intdef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daksh on 03-Sep-16.
 */

public class SortOrder {

    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;

    public final int value;

    public SortOrder(@SortOrderDef int value) {
        this.value = value;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({POPULAR, TOP_RATED})
    public @interface SortOrderDef {}
}
