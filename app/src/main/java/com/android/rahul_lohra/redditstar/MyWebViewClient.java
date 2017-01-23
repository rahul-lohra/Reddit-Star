package com.android.rahul_lohra.redditstar;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by rkrde on 23-12-2016.
 */

public class MyWebViewClient extends WebViewClient {

    String TAG = MyWebViewClient.class.getSimpleName();
    String url;

    public MyWebViewClient(String url) {
        this.url = url;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        return super.shouldOverrideUrlLoading(view, request);

        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.d(TAG, "Page Finished");
        AsyncGetToken.PARSING_URL = url;

    }


}
