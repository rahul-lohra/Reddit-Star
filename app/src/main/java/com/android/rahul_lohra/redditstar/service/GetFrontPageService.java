package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class GetFrontPageService extends IntentService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofit;
    ApiInterface apiInterface;

    public static String after = null;

    private final  String TAG = GetFrontPageService.class.getSimpleName();
    public GetFrontPageService() {
        super(GetFrontPageService.class.getSimpleName());
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer)getApplication()).getNetComponent().inject(this);
        apiInterface =retrofit.create(ApiInterface.class);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            after = intent.getStringExtra("after");
            Map<String,String> map = new HashMap<>();
            map.put("limit","10");
            map.put("after",after);
            try {
                Response<FrontPageResponse> res = apiInterface.getFrontPage(map).execute();
                if(res.code()==200)
                {
                    EventBus.getDefault().post(res.body().getData());
                }
                Log.d(TAG,"response:"+res.code());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                after = null;
            }
        }
    }

}
