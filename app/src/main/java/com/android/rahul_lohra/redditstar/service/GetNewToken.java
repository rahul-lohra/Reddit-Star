package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;

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

public class GetNewToken extends IntentService {

    private static final String TAG = GetNewToken.class.getSimpleName();
    public GetNewToken() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String code = intent.getStringExtra("code");
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
                Handler mainHandler = new Handler(getMainLooper());
                mainHandler.post(myRunnableFail);
                Toast.makeText(getApplicationContext(),getString(R.string.login_failed),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                JSONObject data = null;
                try {
                    data = new JSONObject(json);
                    String accessToken = data.optString("access_token");
                    String refreshToken = data.optString("refresh_token");

                    Log.d(TAG, "Access Token = " + accessToken);
                    Log.d(TAG, "Refresh Token = " + refreshToken);
                    Handler mainHandler = new Handler(getMainLooper());
                    mainHandler.post(myRunnableSuccess);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    Runnable myRunnableSuccess = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),getString(R.string.login_success),Toast.LENGTH_LONG).show();

        }
    };

    Runnable myRunnableFail = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),getString(R.string.login_success),Toast.LENGTH_LONG).show();

        }
    };
}
