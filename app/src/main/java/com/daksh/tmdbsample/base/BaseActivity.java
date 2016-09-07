package com.daksh.tmdbsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.di.Injector;
import com.daksh.tmdbsample.util.PresenterManager;

/**
 * Created by daksh on 03-Sep-16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectActivity(Injector.INSTANCE.getAppComponent());

        if (savedInstanceState == null) {
            instantiatePresenter();
        } else {
            PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    protected abstract void instantiatePresenter();

    public abstract void injectActivity(AppComponent appComponent);
}
