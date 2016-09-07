package com.daksh.tmdbsample.data.intdef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by daksh on 03-Sep-16.
 */

public class ListLoadType {

    public static final int FIRST = 0;
    public static final int INFINITE_SCROLL = 1;
    public static final int PULL_TO_REFRESH = 2;

    public final int listLoadType;

    public ListLoadType(@ListLoadTypeDef int listLoadType) {
        this.listLoadType = listLoadType;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FIRST, INFINITE_SCROLL, PULL_TO_REFRESH})
    public @interface ListLoadTypeDef {
    }
}
