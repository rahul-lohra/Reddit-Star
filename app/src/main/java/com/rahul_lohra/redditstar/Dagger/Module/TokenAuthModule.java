package com.rahul_lohra.redditstar.Dagger.Module;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.modal.token.RefreshTokenResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;

import static com.rahul_lohra.redditstar.Utility.MyUrl.CLIENT_ID;

/**
 * Created by rkrde on 14-04-2017.
 */
@Module(includes = {ContextModule.class,ApiModule.class})
public class TokenAuthModule {
    static int count = 0;
    private final String TAG = TokenAuthModule.class.getSimpleName();

    @Provides
    @Singleton
    Authenticator authenticator(final Context context,final @Named("withToken") Retrofit retrofitWithToken){
        return new Authenticator() {
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
                    ApiInterface apiInterface = retrofitWithToken.create(ApiInterface.class);
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
        };
    }

}
