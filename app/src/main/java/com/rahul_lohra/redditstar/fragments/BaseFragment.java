package com.rahul_lohra.redditstar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.contract.IActivity;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;

public class BaseFragment extends Fragment implements IActivity{
    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    Retrofit retrofit;

    ApiInterface apiInterface;
    public BaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected void deleteArticlesAndComments(){
        Constants.clearPosts(getContext(), Constants.TYPE_POST);
        Constants.clearPosts(getContext(), Constants.TYPE_SEARCH);
        Constants.clearPosts(getContext(), Constants.TYPE_TEMP);
        Constants.clearComments(getContext());
    }


    @Override
    public void openActivity(Intent intent) {
        startActivity(intent);
    }
}
