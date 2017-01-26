package com.android.rahul_lohra.redditstar.presenter.activity;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.IDashboard;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DashboardPresenter implements LoaderManager.LoaderCallbacks{
    private IDashboard view;

    public DashboardPresenter(IDashboard view) {
        this.view = view;
//        Application.

    }

    public void getMySubreddits(){
        /*
        1.make an Api Request
        2.store in content provider
        3.use loader callback

         */
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
