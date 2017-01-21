package com.android.rahul_lohra.redditstar.application;

import android.app.Application;

import com.android.rahul_lohra.redditstar.dagger.Component.DaggerNetComponent;
import com.android.rahul_lohra.redditstar.dagger.Component.NetComponent;
import com.android.rahul_lohra.redditstar.dagger.Module.AppModule;
import com.android.rahul_lohra.redditstar.dagger.Module.NetModule;


/**
 * Created by rkrde on 15-12-2016.
 */

public class Initializer extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //Dagger
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(getApplicationContext()))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
