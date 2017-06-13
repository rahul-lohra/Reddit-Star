package com.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.pagerAdapter.MediaPagerAdapter;
import com.rahul_lohra.redditstar.contract.IMedia;
import com.rahul_lohra.redditstar.factory.DomainFactory;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MediaActivity extends AppCompatActivity {

    @Inject
    @Named("withoutToken")
    Retrofit retrofit;
    ApiInterface apiInterface;
    String domain;
    String TAG = MediaActivity.class.getSimpleName();
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    MediaPagerAdapter adapter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        getSupportActionBar().setTitle("");
        ((Initializer) getApplication()).getNetComponent().inject(this);
        init();
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        domain = intent.getStringExtra("domain");

        getMediaUrl(url);

    }

    private void init() {
        apiInterface = retrofit.create(ApiInterface.class);
    }


    private void setViewPagerAdapter(List<String> urlList) {
        adapter = new MediaPagerAdapter(getSupportFragmentManager(), urlList);
        viewPager.setAdapter(adapter);
    }

    private void getMediaUrl(String url) {

        Call<ResponseBody> call = DomainFactory.getCall(apiInterface, domain, url);
        if (null == call)
            return;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Class domainResponse = DomainFactory.getDomainResponse(domain);
                    if (null == domainResponse)
                        return;
                    //Do this is back ground thread!!
                    try {
                        Object obj = new Gson().fromJson(response.body().string(), domainResponse);
                        List<String> mediaUrl = DomainFactory.provideUrl((IMedia) obj);
                        for (String str : mediaUrl) {
                            System.out.println("Media url:" + str);
                        }
                        setViewPagerAdapter(mediaUrl);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFail:" + t.getMessage());

            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
