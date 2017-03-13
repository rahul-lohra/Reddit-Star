package com.rahul_lohra.redditstar.service.search;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.modal.search.T5_SearchResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.rahul_lohra.redditstar.Utility.SpConstants;
import com.rahul_lohra.redditstar.Utility.UserState;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 20-02-2017.
 */

public class SearchSubredditsService extends IntentService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    ApiInterface apiInterface;


    public static String after = null;
    private static final String TAG = SearchSubredditsService.class.getSimpleName();
    private SharedPreferences sp;
    public SearchSubredditsService() {
        super(TAG);
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
            String subredditName = intent.getStringExtra("query");
            after = intent.getStringExtra("after");
            Map<String, Object> map = new HashMap<>();
            map.put("after", after);
            map.put("limit", "7");
            map.put("q", subredditName);
            map.put(SpConstants.OVER_18,sp.getBoolean(SpConstants.OVER_18,false));

            try {
                Response<T5_SearchResponse> res = apiInterface.searchSubreddits(token,map).execute();
                Log.d(TAG,"resCode:"+res.code());
                if (res.code() == 200) {

                    EventBus.getDefault().post(res.body().data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                after = null;
            }
        }

    }
}
