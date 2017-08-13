package com.rahul_lohra.redditstar.activity.profile;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.activity.BaseActivity;
import com.rahul_lohra.redditstar.adapter.pagerAdapter.ProfileAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ProfileAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setAdapter();
    }
    private void setAdapter(){
        pagerAdapter = new ProfileAdapter(getSupportFragmentManager(),4);
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }
}
