package com.android.rahul_lohra.redditstar.Activity.Application;

import android.app.Application;

import com.android.rahul_lohra.redditstar.Activity.Dagger.Component.DaggerNetComponent;
import com.android.rahul_lohra.redditstar.Activity.Dagger.Component.NetComponent;
import com.android.rahul_lohra.redditstar.Activity.Dagger.Module.AppModule;
import com.android.rahul_lohra.redditstar.Activity.Dagger.Module.NetModule;


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
