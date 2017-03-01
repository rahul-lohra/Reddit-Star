package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.service.GetNewTokenService;
import com.android.rahul_lohra.redditstar.utility.MyUrl;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.android.rahul_lohra.redditstar.utility.MyUrl.ACCESS_TOKEN_URL;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.AUTH_URL;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.CLIENT_ID;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.REDIRECT_URI;
import static com.android.rahul_lohra.redditstar.utility.MyUrl.STATE;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        signIn();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    void signIn() {
        String scopeArray[] = getResources().getStringArray(R.array.scope);
        String scope = MyUrl.getProperScope(scopeArray);
        String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, scope);
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
        Intent intent = new Intent(this, GetNewTokenService.class);
        intent.putExtra("code",code);
        startService(intent);
    }
}
