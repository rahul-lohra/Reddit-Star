package com.android.rahul_lohra.redditstar.dagger.Component;

import com.android.rahul_lohra.redditstar.dagger.Module.AppModule;
import com.android.rahul_lohra.redditstar.dagger.Module.NetModule;
import com.android.rahul_lohra.redditstar.MainActivity;
import com.android.rahul_lohra.redditstar.presenter.DashboardPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rkrde on 24-12-2016.
 */
@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(DashboardPresenter presenter);
}
