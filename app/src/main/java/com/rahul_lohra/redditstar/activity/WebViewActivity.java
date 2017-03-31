package com.rahul_lohra.redditstar.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.service.GetNewTokenService;
import com.rahul_lohra.redditstar.Utility.MyUrl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {


    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private static final String TAG = WebViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        setToolbar();
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        webView.clearCache(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setSavePassword(false);
        webView.clearFormData();
//        webView.clearHistory();
//        webView.clearSslPreferences();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setAppCacheMaxSize(1);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        disableCache();
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyChromeClient());
        loadUrl();
    }

    void setToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void loadUrl(){
        Intent intent = getIntent();
        if(null == intent)
            return;
        Uri uri = intent.getData();
        webView.loadUrl(uri.toString());
    }

    private class MyWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if(url.startsWith("http://www.example.co.in")){
                //Means everything is good
                Uri uri = Uri.parse(url);
                if (uri.getQueryParameter("error") != null) {
                    String error = uri.getQueryParameter("error");
                    Log.e(TAG, "An error has occurred : " + error);
                } else {
                    String state = uri.getQueryParameter("state");

                    if (state!=null && state.equals(MyUrl.STATE)) {
                        String code = uri.getQueryParameter("code");
                        getAccessToken(code);
                        finish();
                        webView = null;
                        Toast.makeText(getApplicationContext(),getString(R.string.attempting_login),Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
            view.loadUrl(url);
            return true;

        }
    }

    private class MyChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
//            setProgress(newProgress * 1000);
        }
    }

    private void getAccessToken(String code) {
        Intent intent = new Intent(this, GetNewTokenService.class);
        intent.putExtra("code",code);
        startService(intent);
    }

    private void disableCache(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            disableCacheForLolipop();
        }else {
            disableCacheForBelowLolipop();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableCacheForLolipop(){

        ValueCallback<Boolean> mCookieDeleted = new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                Log.d(TAG, "cookies deleted");
                // do whatever you want to do after the cookie is deleted; eg : reload tew page etc.
            }
        };

        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(true);
        cm.setAcceptThirdPartyCookies(webView, true);
        cm.removeAllCookies(mCookieDeleted);
        cm.removeSessionCookies(mCookieDeleted);
    }

    private void disableCacheForBelowLolipop(){
        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(false);
        cm.removeAllCookie();
        cm.removeSessionCookie();
    }
}
