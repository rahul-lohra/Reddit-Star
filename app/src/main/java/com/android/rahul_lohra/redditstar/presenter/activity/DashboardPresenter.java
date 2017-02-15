package com.android.rahul_lohra.redditstar.presenter.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.IDashboard;
import com.android.rahul_lohra.redditstar.modal.SubredditResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.retrofit.CustomCallback;
import com.android.rahul_lohra.redditstar.service.GetSubscribedSubredditsService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DashboardPresenter implements LoaderManager.LoaderCallbacks{

    @Inject
    @Named("withToken")
    Retrofit retrofit;

    ApiInterface apiInterface;
    private IDashboard view;
    private Context context;

    final static String TAG = DashboardPresenter.class.getSimpleName();

    public DashboardPresenter(Context context,IDashboard view) {
        this.view = view;
        this.context = context;
        ((Initializer)context).getNetComponent().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void getMySubredditsAndDeletePreviousOnes(){
        Uri mUri = MyProvider.SubredditLists.CONTENT_URI;
        context.getContentResolver().delete(mUri,null,null);
        Intent intent = new Intent(context, GetSubscribedSubredditsService.class);
        context.startService(intent);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
