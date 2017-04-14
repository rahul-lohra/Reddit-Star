package com.rahul_lohra.redditstar.Dagger.Module;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rahul_lohra.redditstar.Utility.MyUrl;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rkrde on 14-04-2017.
 */
@Module(includes = NetworkModule.class)
public class ApiModule {
    @Provides
    @Singleton
    @Named("withToken")
    Retrofit provideRetrofitForFun(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(MyUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    @Named("withoutToken")
    Retrofit provideRetrofitForToken(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(MyUrl.LOGIN_AUTHORITY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
