package com.android.rahul_lohra.redditstar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.util.Log;

import com.android.rahul_lohra.redditstar.activity.WebViewActivity;
import com.android.rahul_lohra.redditstar.utility.MyUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.rahul_lohra.redditstar.utility.MyUrl.ACCESS_TOKEN_URL;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.AUTH_URL;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.CLIENT_ID;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.REDIRECT_URI;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.STATE;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.btn_login)
    AppCompatButton btnLogin;

    @OnClick(R.id.btn_login)
    public void onClickLogin() {
//        startActivity(new Intent(this,LoginActivity.class));
        signIn();
    }

    @OnClick(R.id.btn_getToken)
    public void onClickGetToken() {
//        startActivity(new Intent(this,LoginActivity.class));
//        new AsyncGetToken(AsyncGetToken.PARSING_URL).execute();
        makeRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    void signIn() {
        String scopeArray[] = getResources().getStringArray(R.array.scope_3);
        String scope = MyUrl.getProperScope(scopeArray);
        String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, scope);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        Intent intent = new Intent(this, WebViewActivity.class);
//        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    void makeRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", "C_fkVWSj1YmOLvW093oKcB2s_-k")
                .add("redirect_uri", REDIRECT_URI)
                .build();
        Request request = new Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .method("POST", formBody)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", getApplication().getPackageName())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "Fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "success");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null && getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            Uri uri = getIntent().getData();
            if (uri.getQueryParameter("error") != null) {
                String error = uri.getQueryParameter("error");
                Log.e(TAG, "An error has occurred : " + error);
            } else {
                String state = uri.getQueryParameter("state");
                if (state.equals(STATE)) {
                    String code = uri.getQueryParameter("code");
                    getAccessToken(code);
                }
            }
        }
    }

    private void getAccessToken(String code) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
