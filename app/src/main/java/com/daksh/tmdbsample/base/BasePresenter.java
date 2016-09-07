package com.daksh.tmdbsample.base;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface BasePresenter<V> {

    void start();

    void setView(V view);

    void destroy();
}
