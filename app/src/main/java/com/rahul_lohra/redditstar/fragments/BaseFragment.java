package com.rahul_lohra.redditstar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.contract.IActivity;

import org.greenrobot.eventbus.EventBus;

public class BaseFragment extends Fragment implements IActivity{

//    protected String after = null;
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
