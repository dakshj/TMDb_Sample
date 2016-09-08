package com.daksh.tmdbsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.daksh.tmdbsample.di.Injector;
import com.daksh.tmdbsample.di.component.AppComponent;

/**
 * Created by daksh on 03-Sep-16.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectFragment(Injector.INSTANCE.getAppComponent());
    }

    public abstract void injectFragment(AppComponent applicationComponent);
}
