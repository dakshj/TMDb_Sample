package com.daksh.tmdbsample.base;

import java.lang.ref.WeakReference;

/**
 * Created by daksh on 09-Sep-16.
 */
public abstract class BasePresenterImpl<V> implements BasePresenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
    }

    @Override
    public V getView() throws NullPointerException {
        if (viewRef != null && viewRef.get() != null) {
            return viewRef.get();
        } else {
            throw new NullPointerException("View is unavailable");
        }
    }

    @Override
    public void detachView() {
        viewRef = null;
    }
}
