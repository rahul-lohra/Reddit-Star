package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;

public class RefreshToken extends IntentService {

    private static final String TAG = RefreshToken.class.getSimpleName();
    public RefreshToken(String name) {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }
}
