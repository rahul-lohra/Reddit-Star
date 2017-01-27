package com.android.rahul_lohra.redditstar.presenter.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.contracts.service.GetNewTokenContract;
import com.android.rahul_lohra.redditstar.service.GetUserCredentialsService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.rahul_lohra.redditstar.utility.MyUrl.ACCESS_TOKEN_URL;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.CLIENT_ID;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.REDIRECT_URI;

/**
 * Created by rkrde on 25-01-2017.
 */

public class GetNewTokenPresenter implements GetNewTokenContract {

    private String TAG = GetNewTokenPresenter.class.getSimpleName();
    private Context context;
    boolean val = false;
    Handler mainHandler;

    public GetNewTokenPresenter(Context context) {
        this.context = context;
        mainHandler = new Handler(context.getMainLooper());
    }


    @Override
    public boolean getToken(String code) {

        OkHttpClient client = new OkHttpClient();

        String authString = CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Sample App")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .url(ACCESS_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "grant_type=authorization_code&code=" + code +
                                "&redirect_uri=" + REDIRECT_URI))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "ERROR: " + e);
                Handler mainHandler = new Handler(context.getMainLooper());
                mainHandler.post(myRunnableFail);
                Toast.makeText(context, context.getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                val = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                JSONObject data = null;
                try {
                    data = new JSONObject(json);
                    String accessToken = data.optString("access_token");
                    String refreshToken = data.optString("refresh_token");

                    if (!accessToken.isEmpty()) {
                        //Important Check
                        val = true;
                        saveInDatabase(accessToken,refreshToken,1);
                    }

                    Log.d(TAG, "Access Token = " + accessToken);
                    Log.d(TAG, "Refresh Token = " + refreshToken);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return val;
    }

    @Override
    public synchronized void  saveInDatabase(String accessToken, String refreshToken,int activeState) {
        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.ACCESS_TOKEN,accessToken);
        cv.put(UserCredentialsColumn.REFRESH_TOKEN,refreshToken);
        cv.put(UserCredentialsColumn.ACTIVE_STATE,activeState);
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        context.getContentResolver().insert(mUri,cv);
        disableActiveState(accessToken,0);
        fetchUsersCredentials(accessToken);
    }

    @Override
    public synchronized void disableActiveState(String accessToken,int activeState) {
        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.ACTIVE_STATE,activeState);
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mWhere = UserCredentialsColumn.ACCESS_TOKEN +"!=?";
        String mSelectionArgs[]={accessToken};
        int rowsUpdated = context.getContentResolver().update(mUri,cv,mWhere,mSelectionArgs);
        Log.d(TAG,"disableActiveState() ->rowsUpdated:"+rowsUpdated);
    }

    @Override
    public void showLoginSuccess() {
        mainHandler.post(myRunnableSuccess);
    }

    @Override
    public void showLoginFailure() {
        mainHandler.post(myRunnableFail);
    }

    @Override
    public void fetchUsersCredentials(String accessToken) {
        Intent intent = new Intent(context, GetUserCredentialsService.class);
        intent.putExtra("accessToken",accessToken);
        context.startService(intent);
    }

    private Runnable myRunnableSuccess = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(context, context.getString(R.string.login_success), Toast.LENGTH_LONG).show();

        }
    };

    private Runnable myRunnableFail = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(context, context.getString(R.string.login_success), Toast.LENGTH_LONG).show();

        }
    };

}


