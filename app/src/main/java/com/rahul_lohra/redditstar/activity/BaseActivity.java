package com.rahul_lohra.redditstar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rahul_lohra.redditstar.Application.Initializer;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class BaseActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ((Initializer)getApplication()).getNetComponent().inject(this);
    }
}
