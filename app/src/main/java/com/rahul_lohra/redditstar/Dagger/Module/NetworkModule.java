package com.rahul_lohra.redditstar.Dagger.Module;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rahul_lohra.redditstar.BuildConfig;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by rkrde on 14-04-2017.
 */
@Module(includes = CacheModule.class)
public class NetworkModule {
    @Singleton
    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (BuildConfig.DEBUG) {
                    Timber.i(message);
                }
            }
        });
    }

    @Singleton
    @Provides
    public Interceptor interceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
    }
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,Interceptor interceptor, Cache cache, StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .build();
    }
    @Singleton
    @Provides
    StethoInterceptor stethoInterceptor() {
        return new StethoInterceptor();
    }
}
