package com.rahul_lohra.redditstar.Dagger.Module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rkrde on 14-04-2017.
 */
@Module
public class ContextModule {

    private final Context context ;

    public ContextModule(Context context) {
        this.context = context;
    }
    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }
}
