package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.ListingChild;
import com.android.rahul_lohra.redditstar.modal.SubredditDataStructure;
import com.android.rahul_lohra.redditstar.modal.SubredditResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 28-01-2017.
 */

public class GetSubscribedSubredditsService extends IntentService {

    @Inject
    @Named("withToken")
    Retrofit retrofit;
    ApiInterface apiInterface;

    private final static String TAG = GetSubscribedSubredditsService.class.getSimpleName();
    public GetSubscribedSubredditsService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer)getApplication()).getNetComponent().inject(this);
    apiInterface =retrofit.create(ApiInterface.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        getAllSubscribedSubreddits(getApplicationContext());
    }

    void getAllSubscribedSubreddits(Context context){


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

        String arr []={accessToken,""};
        makeApiRequest(arr);
    }

    private void makeApiRequest(String[] array){
        String accessToken = array[0];
        String token = "bearer "+accessToken;
        Map<String,String> map = new HashMap<>();
        map.put("after",array[1]);
        try {
            Response<SubredditResponse> response = apiInterface.getMySubscribedSubreddits(token,map).execute();
            if(response.code()==200){
                List<ListingChild> list = response.body().getData().getChildren();
                saveSubreddit(list);

                //exit condition
                if(list.size()==0)
                    return;

                String after = response.body().getData().getAfter();
                String arr[] = {accessToken,after};
                makeApiRequest(arr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSubreddit(List<ListingChild> list){
        Uri mUri = MyProvider.SubredditLists.CONTENT_URI;

        ContentValues contentValues[] = new ContentValues[list.size()];

        for (int i= 0;i<contentValues.length;++i){
            contentValues[i] = new ContentValues();
            contentValues[i].put(MySubredditColumn.KEY_ID,list.get(i).getData().getId());
            contentValues[i].put(MySubredditColumn.KEY_DISPLAY_NAME,list.get(i).getData().getDisplayName());
            contentValues[i].put(MySubredditColumn.KEY_HEADER_IMAGE,list.get(i).getData().getHeaderImg());
            contentValues[i].put(MySubredditColumn.KEY_TITLE,list.get(i).getData().getTitle());
            contentValues[i].put(MySubredditColumn.KEY_OVER_18,list.get(i).getData().getOver18());
            contentValues[i].put(MySubredditColumn.KEY_ICON_IMAGE,list.get(i).getData().getIconImg());
            contentValues[i].put(MySubredditColumn.KEY_ACCOUNTS_ACTIVE,list.get(i).getData().getAccountsActive());
            contentValues[i].put(MySubredditColumn.KEY_SUBSCRIBERS_COUNT,list.get(i).getData().getSubscribers());
            contentValues[i].put(MySubredditColumn.KEY_LANGUAGE,list.get(i).getData().getLang());
            contentValues[i].put(MySubredditColumn.KEY_COLOR,list.get(i).getData().getKeyColor());
            contentValues[i].put(MySubredditColumn.KEY_NAME,list.get(i).getData().getName());
            contentValues[i].put(MySubredditColumn.KEY_URL,list.get(i).getData().getUrl());
            contentValues[i].put(MySubredditColumn.KEY_USER_IS_MODERTOR,list.get(i).getData().getUserIsModerator());
            contentValues[i].put(MySubredditColumn.KEY_SUBREDDIT_TYPE,list.get(i).getData().getSubredditType());
            contentValues[i].put(MySubredditColumn.KEY_SUBMISSION_TYPE,list.get(i).getData().getSubmissionType());
            contentValues[i].put(MySubredditColumn.USER_IS_SUBSCRIBER,list.get(i).getData().getUserIsSubscriber());

        }
        int rowsInserted  = getContentResolver().bulkInsert(mUri,contentValues);
        Log.d(TAG,"saveSubreddit:Rows Inserted:"+rowsInserted);
    }
}
