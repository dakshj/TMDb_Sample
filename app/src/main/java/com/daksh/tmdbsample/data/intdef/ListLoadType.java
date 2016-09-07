package com.daksh.tmdbsample.data.intdef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daksh on 03-Sep-16.
 */

public class ListLoadType {

    public static final int FIRST = 0;
    public static final int SWIPE_REFRESH = 1;
    public static final int INFINITE_SCROLL = 2;

    public final int value;

    public ListLoadType(@ListLoadTypeDef int value) {
        this.value = value;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FIRST, SWIPE_REFRESH, INFINITE_SCROLL})
    public @interface ListLoadTypeDef {}
}
