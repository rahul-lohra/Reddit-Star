package com.android.rahul_lohra.redditstar.dummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.rahul_lohra.redditstar.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DummyMainActivity extends AppCompatActivity {

    @OnClick(R.id.btn_dashboard)
    void onClickDashboard(){
        startActivity(new Intent(this,DummyDashboardActivity.class));
    }

    @OnClick(R.id.btn_detail_reddit)
    void onClickDetailReddit(){
        startActivity(new Intent(this,DummyDetailActivity.class));
    }

    @OnClick(R.id.btn_nav_activity)
    void onClickNavActivity(){
        startActivity(new Intent(this,NavActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_main);
        ButterKnife.bind(this);
    }
}
