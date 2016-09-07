package com.daksh.tmdbsample.util;

import android.util.Log;

import com.daksh.tmdbsample.BuildConfig;

/**
 * Created by daksh on 06-Sep-16.
 */
public final class Logger {

    public static void log(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void networkLog(String message) {
        log("Network", message);
    }

    public static void errorLog(String message) {
        log("Error", message);
    }
}
