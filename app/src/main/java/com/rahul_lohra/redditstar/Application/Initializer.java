package com.rahul_lohra.redditstar.Application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.rahul_lohra.redditstar.Application.timber.ReleaseTree;
import com.rahul_lohra.redditstar.BuildConfig;
//import com.rahul_lohra.redditstar.dagger.Component.DaggerNetComponent;
//import com.rahul_lohra.redditstar.Dagger.Component.DaggerNetComponent;
import com.rahul_lohra.redditstar.Dagger.Component.NetComponent;
import com.rahul_lohra.redditstar.Dagger.Module.AppModule;
import com.rahul_lohra.redditstar.Dagger.Module.ContextModule;
import com.rahul_lohra.redditstar.Dagger.Module.NetModule;
import com.facebook.stetho.Stetho;

import timber.log.Timber;


/**
 * Created by rkrde on 15-12-2016.
 */

public class Initializer extends Application {
    private NetComponent mNetComponent;
    private static Initializer initializer;
    @Override
    public void onCreate() {
        super.onCreate();
        initializer = this;
//        Timber.plant(new Timber.DebugTree());
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element)+":"+element.getLineNumber();
                }
            });
        }else {
            Timber.plant(new ReleaseTree());

        }

        mNetComponent = DaggerNetComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        if(BuildConfig.DEBUG){
            Stetho.InitializerBuilder initializerBuilder =
                    Stetho.newInitializerBuilder(this);
            initializerBuilder.enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this)
            );
            initializerBuilder.enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(getApplicationContext())
            );
            Stetho.Initializer initializer = initializerBuilder.build();
            Stetho.initialize(initializer);
        }
    }

    public static Initializer getInstance(){
        return initializer;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
