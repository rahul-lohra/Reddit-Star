package com.rahul_lohra.redditstar.Dagger.Module;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

/**
 * Created by rkrde on 14-04-2017.
 */

@Module(includes = ContextModule.class)
public class CacheModule {
    @Singleton
    @Provides
    public Cache cache(File file) {
        return new Cache(file, 10 * 1024 * 1024); //10 mb
    }

    @Singleton
    @Provides
    public File file(Context context) {
        return new File(context.getCacheDir(), "http-cache");
    }
}
