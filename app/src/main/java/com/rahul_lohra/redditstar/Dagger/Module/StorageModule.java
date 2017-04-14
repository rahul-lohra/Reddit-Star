package com.rahul_lohra.redditstar.Dagger.Module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rkrde on 14-04-2017.
 */
@Module(includes = ContextModule.class)
public class StorageModule {

    @Singleton
    @Provides
    SharedPreferences sharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
