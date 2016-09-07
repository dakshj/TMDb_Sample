package com.daksh.tmdbsample.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daksh.tmdbsample.di.Injector;
import com.daksh.tmdbsample.di.component.AppComponent;
import com.daksh.tmdbsample.util.StateMaintainer;

/**
 * Created by daksh on 03-Sep-16.
 */
public abstract class BaseActivity<P> extends AppCompatActivity {

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer(getFragmentManager(), getTag());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupMvp();
    }

    private void setupMvp() {
        if (mStateMaintainer.firstTimeIn()) {
            initialize();
        } else {
            reinitialize();
        }
    }

    private void initialize() {
        setupComponent();
        startPresenter();
        mStateMaintainer.put(getTag(), getPresenter());
    }

    @SuppressWarnings("unchecked")
    private void reinitialize() {
        setPresenter((P) mStateMaintainer.get(getTag()));
        if (getPresenter() == null) {
            setupComponent();
        }
    }

    private void setupComponent() {
        injectActivity(Injector.INSTANCE.getAppComponent());
    }

    protected abstract P getPresenter();

    protected abstract void setPresenter(P presenter);

    protected abstract void startPresenter();

    /**
     * @return Tag:<br/>A unique key used to persist the Presenter across configuration changes.
     */
    @NonNull
    public abstract String getTag();

    public abstract void injectActivity(AppComponent appComponent);
}
