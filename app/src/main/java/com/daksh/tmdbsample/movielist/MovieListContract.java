package com.daksh.tmdbsample.movielist;

import com.daksh.tmdbsample.base.BasePresenter;
import com.daksh.tmdbsample.base.BaseView;

/**
 * Created by daksh on 03-Sep-16.
 */
public interface MovieListContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
