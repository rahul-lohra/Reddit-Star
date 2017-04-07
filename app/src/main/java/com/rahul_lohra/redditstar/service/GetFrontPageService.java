package com.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.modal.custom.AfterModal;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.rahul_lohra.redditstar.modal.frontPage.Preview;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.Utility.SpConstants;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;


@SuppressWarnings("HardCodedStringLiteral")
public class GetFrontPageService extends IntentService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    ApiInterface apiInterface;
    final Uri mUri = MyProvider.PostsLists.CONTENT_URI;

    public static String after = null;
    public boolean isUserLoggedIn = false;
    private final  String TAG = GetFrontPageService.class.getSimpleName();
    SharedPreferences sp;
    public GetFrontPageService() {
        super(GetFrontPageService.class.getSimpleName());
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer) getApplication()).getNetComponent().inject(this);
        isUserLoggedIn = UserState.isUserLoggedIn(getApplicationContext());
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
            after = intent.getStringExtra("after");
            String filterParam_1 = intent.getStringExtra("filterParam_1");
            String filterParam_2 = intent.getStringExtra("filterParam_2");

            if(null ==after){
                return;
            }
            Map<String,String> map = new HashMap<>();
            map.put("limit","20");
            map.put("after",after);
            map.put("t",filterParam_2);
            map.put(SpConstants.OVER_18,String.valueOf(sp.getBoolean(SpConstants.OVER_18,false)));
            String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(getApplicationContext()) : "";

            try {
                Response<FrontPageResponse> res = apiInterface.getFrontPage(token,filterParam_1,map).execute();
                if(res.code()==200)
                {
                    EventBus.getDefault().post(new AfterModal(res.body().getData().getAfter()));
                    Constants.insertPostsIntoTable(getApplicationContext(),res.body(),Constants.TYPE_POST);
                    for(FrontPageChild frontPageChild:res.body().getData().getChildren()){
                        String thumbnail = frontPageChild.getData().getThumbnail();
                        if(Constants.isBigImageUrlValid(thumbnail))
                        {
                            FutureTarget<File> futureThumbnail = Glide.with(getApplicationContext())
                                    .load(thumbnail)
                                    .downloadOnly(2000, 2000);
                        }
                        Preview preview = frontPageChild.getData().getPreview();
                        String bigImageUrl = (preview!=null)?preview.getImages().get(0).getSource().getUrl():null;
                        if(Constants.isBigImageUrlValid(bigImageUrl))
                        {
                            FutureTarget<File> futureBigImage = Glide.with(getApplicationContext())
                                    .load(bigImageUrl)
                                    .downloadOnly(2000, 2000);
                        }

                    }
                }
                Log.d(TAG,"response:"+res.code());
            } catch (UnknownHostException e){

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                after = null;
            }
        }
    }

}
