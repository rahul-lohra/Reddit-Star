package com.rahul_lohra.redditstar.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;

/**
 * Created by rkrde on 15-04-2017.
 */

public abstract class BaseGcmTaskService extends GcmTaskService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    @Inject
    Context mContext;
    @Inject
    SharedPreferences sp;

    ApiInterface apiInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer)getApplication()).getNetComponent().inject(this);
    }
}
