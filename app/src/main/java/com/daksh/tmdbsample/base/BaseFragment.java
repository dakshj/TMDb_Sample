package com.daksh.tmdbsample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.daksh.tmdbsample.di.Injector;
import com.daksh.tmdbsample.di.component.AppComponent;

import icepick.Icepick;

/**
 * Created by daksh on 03-Sep-16.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);

        injectFragment(Injector.INSTANCE.getAppComponent());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Icepick.saveInstanceState(this, savedInstanceState);
    }

    public abstract void injectFragment(AppComponent applicationComponent);
}
