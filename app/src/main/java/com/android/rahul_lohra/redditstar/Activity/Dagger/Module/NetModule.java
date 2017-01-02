package com.android.rahul_lohra.redditstar.Activity.Dagger.Module;

import android.content.Context;

import com.android.rahul_lohra.redditstar.Activity.Utility.MyUrl;

import java.io.IOException;

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
 * Created by rkrde on 15-12-2016.
 */
@Module
public class NetModule {

    Context context;
    public NetModule(Context context) {
        this.context = context;
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

//                                          if (CheckNetwork.isNetWork(context)) {
//                                              original = original.newBuilder()
////                                                      .header("Cache-Control", "public, max-age=" + 60)
//                                                      .header("Content-Type", "application/json")
//                                                      .method(original.method(), original.body())
//                                                      .build();
//                                          } else {
//                                              original = original.newBuilder()
//                                                      .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
//                                                      .header("Content-Type", "application/json")
//                                                      .method(original.method(), original.body())
//                                                      .build();
//                                          }

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
//                httpClient.cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024)); //10 mb
        OkHttpClient client = httpClient.build();


        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.LOGIN_AUTHORITY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }
}
