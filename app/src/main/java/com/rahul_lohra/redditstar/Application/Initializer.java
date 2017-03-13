package com.rahul_lohra.redditstar.Application;

import android.app.Application;

import com.rahul_lohra.redditstar.BuildConfig;
//import com.rahul_lohra.redditstar.dagger.Component.DaggerNetComponent;
import com.rahul_lohra.redditstar.Dagger.Component.DaggerNetComponent;
import com.rahul_lohra.redditstar.Dagger.Component.NetComponent;
import com.rahul_lohra.redditstar.Dagger.Module.AppModule;
import com.rahul_lohra.redditstar.Dagger.Module.NetModule;
import com.facebook.stetho.Stetho;


/**
 * Created by rkrde on 15-12-2016.
 */

public class Initializer extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //LeakCanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

        //Dagger
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(getApplicationContext()))
                .build();

        if(BuildConfig.DEBUG){
//            Stetho.initializeWithDefaults(this);
            // Create an InitializerBuilder
            Stetho.InitializerBuilder initializerBuilder =
                    Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
            initializerBuilder.enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this)
            );

// Enable command line interface
            initializerBuilder.enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(getApplicationContext())
            );

// Use the InitializerBuilder to generate an Initializer
            Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer
            Stetho.initialize(initializer);
        }
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
