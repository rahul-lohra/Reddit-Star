package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;

public class RefreshTokenService extends IntentService {

    private static final String TAG = RefreshTokenService.class.getSimpleName();
    public RefreshTokenService(String name) {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }
}
