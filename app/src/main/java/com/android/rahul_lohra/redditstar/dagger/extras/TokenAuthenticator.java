package com.android.rahul_lohra.redditstar.dagger.extras;

import android.content.Context;
import android.util.Log;

import com.android.rahul_lohra.redditstar.modal.token.RefreshToken;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 28-01-2017.
 */

public class TokenAuthenticator implements Authenticator {

    @Inject
    @Named("token")
    Retrofit retrofit;
    ApiInterface apiInterface;

    private Context context;
    public TokenAuthenticator(Context context){
        this.context  = context;
        apiInterface = retrofit.create(ApiInterface.class);
    }
    private static final String TAG = TokenAuthenticator.class.getSimpleName();

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Log.d(TAG, "TokenAuthenticator: authenticate");

        if (response.code() == 401) {
            /*
            get accessToken and refreshToken of Active User
             */
            String arrayOfToken[] = Constants.getAccessTokenAndRefreshTokenOfActiveUser(context);
            String accessToken = arrayOfToken[0];
            String refreshToken = arrayOfToken[1];

            /*
            Make Synchronous Api Call to refresh Token
             */
            String token = "Basic " + accessToken;
            Map<String, String> map = new HashMap<>();
            map.put("grant_type", "refresh_token");
            map.put("refresh_token", refreshToken);
            RefreshToken refreshToken1 = new RefreshToken("refresh_token",refreshToken);
        }
        return null;
    }
}
