package com.android.rahul_lohra.redditstar.Activity;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by rkrde on 23-12-2016.
 */

public class MyWebViewClient extends WebViewClient {

    String TAG = MyWebViewClient.class.getSimpleName();
    String url;
    public MyWebViewClient(String url){this.url = url;}
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        return super.shouldOverrideUrlLoading(view, request);

        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG,"Page Finished");
        AsyncGetToken.PARSING_URL = url;

    }


}
