package com.android.rahul_lohra.redditstar.dagger.Module;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.rahul_lohra.redditstar.modal.RefreshTokenResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.MyUrl;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.rahul_lohra.redditstar.utility.MyUrl.CLIENT_ID;

/**
 * Created by rkrde on 15-12-2016.
 */
@Module
public class NetModule {

    static int count = 0;
    Context context;
    private static final String TAG = NetModule.class.getSimpleName();

    public NetModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @Named("withToken")
    Retrofit provideRetrofitForFun() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Log.d(TAG, "intercept");
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.authenticator(new TokenAuthenticator())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }

    @Provides
    @Singleton
    @Named("withoutToken")
    Retrofit provideRetrofitForToken() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.authenticator(new TokenAuthenticator())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        Retrofit retrofit = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.LOGIN_AUTHORITY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }

    public class TokenAuthenticator implements Authenticator {

        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            Log.d(TAG, "TokenAuthenticator: authenticate");

            if (response.code() == 401) {

                ++count;
                if(count==2){
                    count = 0;
                    return null;
                }
            /*
            get accessToken and refreshToken of Active User
             */
                String arrayOfToken[] = Constants.getAccessTokenAndRefreshTokenOfActiveUser(context);
                String refreshToken = arrayOfToken[1];
                String authString = CLIENT_ID + ":";
                String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                        Base64.NO_WRAP);

            /*
            Make Synchronous Api Call to refresh Token
             */
                ApiInterface apiInterface = provideRetrofitForToken().create(ApiInterface.class);
                String token = "Basic " + encodedAuthString;
                retrofit2.Response<RefreshTokenResponse> res = apiInterface.refreshToken(
                        token,
                        "refresh_token",refreshToken).execute();
                String newValidToken = "bearer ";
                if (res.code() == 200) {
                    String newToken = res.body().getAccessToken();
                    newValidToken = newValidToken + newToken;
                    //update in db
                    Constants.updateAccessToken(context, newToken, refreshToken);
                    return response.request().newBuilder()
                            .header(Constants.AUTHORIZATION, newValidToken)
                            .build();
                }
            }
            return null;
        }
    }

}
