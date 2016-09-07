package com.daksh.tmdbsample.data.source.local;

import com.daksh.tmdbsample.data.intdef.SortOrder;

/**
 * Created by daksh on 06-Sept-2016.
 */
public interface AppSettings {

    SortOrder getSortOrder();

    void setSortOrder(SortOrder sortOrder);
}
