package com.android.rahul_lohra.redditstar.dagger.Component;

import com.android.rahul_lohra.redditstar.dagger.Module.AppModule;
import com.android.rahul_lohra.redditstar.dagger.Module.NetModule;
import com.android.rahul_lohra.redditstar.activity.MainActivity;
import com.android.rahul_lohra.redditstar.dagger.extras.TokenAuthenticator;
import com.android.rahul_lohra.redditstar.presenter.activity.DashboardPresenter;
import com.android.rahul_lohra.redditstar.service.GetSubscribedSubredditsService;
import com.android.rahul_lohra.redditstar.service.GetUserCredentialsService;

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
    void inject(GetUserCredentialsService service);
    void inject(GetSubscribedSubredditsService service);

}
