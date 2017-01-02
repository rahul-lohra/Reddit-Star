package com.android.rahul_lohra.redditstar.Activity.Dagger.Component;

import com.android.rahul_lohra.redditstar.Activity.Dagger.Module.AppModule;
import com.android.rahul_lohra.redditstar.Activity.Dagger.Module.NetModule;
import com.android.rahul_lohra.redditstar.Activity.Activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rkrde on 24-12-2016.
 */
@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    void inject(MainActivity activity);

}
