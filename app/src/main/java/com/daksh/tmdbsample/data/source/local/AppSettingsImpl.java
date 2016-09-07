package com.daksh.tmdbsample.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.daksh.tmdbsample.data.intdef.SortOrder;

/**
 * Created by daksh on 06-Sept-2016.
 */

public class AppSettingsImpl implements AppSettings {

    private static final String FILE_APP_SETTINGS = "APP_SETTINGS";

    private static final String SORT_ORDER = "SORT_ORDER";

    private SharedPreferences sharedPreferences;

    public AppSettingsImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_APP_SETTINGS, Context.MODE_PRIVATE);
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public SortOrder getSortOrder() {
        return new SortOrder(sharedPreferences.getInt(SORT_ORDER, SortOrder.POPULAR));
    }

    @Override
    public void setSortOrder(SortOrder sortOrder) {
        sharedPreferences
                .edit()
                .putInt(SORT_ORDER, sortOrder.value)
                .apply();
    }
}
