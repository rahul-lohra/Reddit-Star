package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.SpConstants;
import com.android.rahul_lohra.redditstar.utility.UserState;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;


public class GetSubredditListService extends IntentService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    ApiInterface apiInterface;


    public static String after = null;

    private final String TAG = GetSubredditListService.class.getSimpleName();
    SharedPreferences sp;
    public GetSubredditListService() {
        super(GetSubredditListService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer) getApplication()).getNetComponent().inject(this);
        apiInterface = (UserState.isUserLoggedIn(getApplicationContext())) ? retrofitWithToken.create(ApiInterface.class) : retrofitWithoutToken.create(ApiInterface.class);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            boolean isUserLoggedIn = UserState.isUserLoggedIn(getApplicationContext());
            apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
            String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(getApplicationContext()) : "";
            String subredditName = intent.getStringExtra("subredditName");
            after = intent.getStringExtra("after");
            Map<String, String> map = new HashMap<>();
            map.put("after", after);
            map.put("limit", "15");
            map.put(SpConstants.OVER_18,String.valueOf(sp.getBoolean(SpConstants.OVER_18,false)));

            try {
                Response<FrontPageResponse> res = apiInterface.getSubredditList(token,subredditName,map).execute();
                Log.d(TAG,"resCode:"+res.code());
                if (res.code() == 200) {
//                    EventBus.getDefault().post(res.body().getData());
                    Constants.insertPostsIntoTable(getApplicationContext(),res.body(),Constants.TYPE_TEMP);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                after = null;
            }
        }
    }

}
