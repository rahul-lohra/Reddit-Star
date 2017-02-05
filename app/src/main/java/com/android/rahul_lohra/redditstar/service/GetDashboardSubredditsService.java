package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;


public class GetDashboardSubredditsService extends IntentService {

    @Inject
    @Named("fun")
    Retrofit retrofit;
    ApiInterface apiInterface;

    private final  String TAG = GetDashboardSubredditsService.class.getSimpleName();
    public GetDashboardSubredditsService() {
        super(GetDashboardSubredditsService.class.getSimpleName());
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer)getApplication()).getNetComponent().inject(this);
        apiInterface =retrofit.create(ApiInterface.class);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }

}
