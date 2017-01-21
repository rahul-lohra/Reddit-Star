package com.android.rahul_lohra.redditstar;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.android.rahul_lohra.redditstar.utility.MyUrl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    /*
    https://www.reddit.com/api/v1/authorize?client_id=CLIENT_ID&response_type=TYPE&
    state=RANDOM_STRING&redirect_uri=URI&duration=DURATION&scope=SCOPE_STRING
     */

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loadWebView();
    }

    private void loadWebView()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(MyUrl.LOGIN_AUTHORITY)
                .appendPath("api")
                .appendPath("v1")
                .appendPath("authorize")
                .appendQueryParameter("client_id", MyUrl.CLIENT_ID)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("state","AnyState")
                .appendQueryParameter("redirect_uri", MyUrl.REDIRECT_URI)
                .appendQueryParameter("duration","permanent")
                .appendQueryParameter("scope",MyUrl.SCOPE);
        String myUrl = builder.build().toString();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient(myUrl));
        webView.loadUrl(myUrl);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);





    }
}
