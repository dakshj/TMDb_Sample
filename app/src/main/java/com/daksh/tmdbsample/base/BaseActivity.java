package com.daksh.tmdbsample.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daksh.tmdbsample.di.Injector;
import com.daksh.tmdbsample.di.component.AppComponent;

/**
 * Created by daksh on 03-Sep-16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupComponent();
    }

    private void setupComponent() {
        injectActivity(Injector.INSTANCE.getAppComponent());
    }

    /**
     * @return Tag:<br/>A unique key used to persist the Presenter across configuration changes.
     */
    @NonNull
    public abstract String getTag();

    public abstract void injectActivity(AppComponent appComponent);
}
