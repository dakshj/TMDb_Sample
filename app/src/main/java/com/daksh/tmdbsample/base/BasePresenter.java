package com.daksh.tmdbsample.base;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface BasePresenter<V> {

    void attachView(V view);

    void start();

    V getView();

    void detachView();
}
