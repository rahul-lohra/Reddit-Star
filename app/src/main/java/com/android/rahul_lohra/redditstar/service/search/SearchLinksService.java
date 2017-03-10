package com.android.rahul_lohra.redditstar.service.search;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.custom.AfterModal;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.SpConstants;
import com.android.rahul_lohra.redditstar.utility.UserState;

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

public class SearchLinksService extends IntentService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    ApiInterface apiInterface;
    private static final String TAG = SearchLinksService.class.getSimpleName();
    public static String after = null;
    public SearchLinksService() {
        super(TAG);
    }
    private SharedPreferences sp;
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
            map.put("q",subredditName);
            map.put(SpConstants.OVER_18,sp.getBoolean(SpConstants.OVER_18,false));

            try {
                Response<FrontPageResponse> res = apiInterface.searchLinks(token,map).execute();
                Log.d(TAG,"resCode:"+res.code());
                if (res.code() == 200) {
                    FrontPageResponse modal = res.body();
                    Constants.insertPostsIntoTable(getApplicationContext(),modal,Constants.TYPE_SEARCH);
                    EventBus.getDefault().post(new AfterModal(modal.getData().getAfter()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                after = null;
            }
        }

    }
}
