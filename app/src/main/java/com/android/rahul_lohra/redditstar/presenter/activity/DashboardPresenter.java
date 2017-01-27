package com.android.rahul_lohra.redditstar.presenter.activity;

import android.app.Application;
import android.content.Context;
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
    @Named("fun")
    Retrofit retrofit;

    ApiInterface apiInterface;
    private IDashboard view;
    private Context context;

    final static String TAG = DashboardPresenter.class.getSimpleName();


    public DashboardPresenter(Context context,IDashboard view) {
        this.view = view;
        this.context = context;
//        Application.
        ((Initializer)context).getNetComponent().inject(this);
    }

    public void getMySubreddits(){
        /*
        1.make an Api Request
        2.store in content provider
        3.use loader callback
         */
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mProjection[]={UserCredentialsColumn.ACCESS_TOKEN,UserCredentialsColumn.REFRESH_TOKEN};
        String mWhere = UserCredentialsColumn.ACTIVE_STATE +"=?";
        String mSelectionArgs[]={"1"};
        Cursor cursor = context.getContentResolver().query(mUri,mProjection,mWhere,mSelectionArgs,null);
        String accessToken = "";
        String refreshToken = "";
        if(cursor.moveToFirst()){
            do{
                accessToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.ACCESS_TOKEN));
                refreshToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.REFRESH_TOKEN));
            }while (cursor.moveToNext());
        }
        cursor.close();

        if(accessToken.isEmpty())
        {
            Log.d(TAG,"No one is Active");
            return;
        }
        String token = "bearer "+accessToken;
        Map<String,String> map = new HashMap<>();
        apiInterface.getMySubscribedSubreddits(token,map)
                .enqueue(new Callback<SubredditResponse>() {
                    @Override
                    public void onResponse(Call<SubredditResponse> call, Response<SubredditResponse> response) {
                        if(response.code()==401){
                            /*
                            TODO: Request for new token !!!!
                             */
                        }
                    }

                    @Override
                    public void onFailure(Call<SubredditResponse> call, Throwable t) {

                    }
                });
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
