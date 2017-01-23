package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.service.GetNewToken;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.android.rahul_lohra.redditstar.utility.MyUrl.STATE;

public class WebViewActivity extends AppCompatActivity {


    @Bind(R.id.webView)
    WebView webView;
    private static final String TAG = WebViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        loadUrl();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new myWebClient());
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

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            if(url.startsWith("http://www.example.co.in")){
                //Means everything is good
                Uri uri = Uri.parse(url);
                if (uri.getQueryParameter("error") != null) {
                    String error = uri.getQueryParameter("error");
                    Log.e(TAG, "An error has occurred : " + error);
                } else {
                    String state = uri.getQueryParameter("state");
                    if (state.equals(STATE)) {
                        String code = uri.getQueryParameter("code");
                        getAccessToken(code);
                        finish();
                        webView = null;
                        Toast.makeText(getApplicationContext(),getString(R.string.attempting_login),Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }

    private void getAccessToken(String code) {
        Intent intent = new Intent(this, GetNewToken.class);
        intent.putExtra("code",code);
        startService(intent);
    }

}
