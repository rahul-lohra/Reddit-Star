package com.android.rahul_lohra.redditstar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.rahul_lohra.redditstar.viewHolder.PostViewDetail;

public abstract class BaseactivityForPostViewDetail extends AppCompatActivity implements PostViewDetail.IPostViewDetail {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
