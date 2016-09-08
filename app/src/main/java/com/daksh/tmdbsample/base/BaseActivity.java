package com.daksh.tmdbsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daksh.tmdbsample.di.Injector;
import com.daksh.tmdbsample.di.component.AppComponent;

import icepick.Icepick;

/**
 * Created by daksh on 03-Sep-16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);

        setupComponent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this, outState);
    }

    private void setupComponent() {
        injectActivity(Injector.INSTANCE.getAppComponent());
    }

    public abstract void injectActivity(AppComponent appComponent);
}
