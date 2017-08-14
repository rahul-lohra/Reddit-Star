package com.rahul_lohra.redditstar.Dagger.Module;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rahul_lohra.redditstar.BuildConfig;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.modal.token.RefreshTokenResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import timber.log.Timber;

import static com.rahul_lohra.redditstar.Utility.MyUrl.CLIENT_ID;

/**
 * Created by rkrde on 14-04-2017.
 */
@Module(includes = {CacheModule.class})
public class NetworkModule {
    static int count = 0;
    private final String TAG = getClass().getSimpleName();


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
    @Named("withoutAuth")
    OkHttpClient setUpOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
                                     Interceptor interceptor,
                                     Cache cache,
                                     StethoInterceptor stethoInterceptor
                                     ) {
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
