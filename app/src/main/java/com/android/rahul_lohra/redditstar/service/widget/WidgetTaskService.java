package com.android.rahul_lohra.redditstar.service.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.MyUrl;
import com.android.rahul_lohra.redditstar.utility.RedditSort;
import com.android.rahul_lohra.redditstar.utility.SpConstants;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 02-03-2017.
 */

public class WidgetTaskService extends GcmTaskService {

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    ApiInterface apiInterface;

    SharedPreferences sp;
    Uri mUri = MyProvider.WidgetLists.CONTENT_URI;

    final String TAG = WidgetTaskService.class.getSimpleName();
    public  final static String INTENT_TAG = "com.android.rahul_lohra.redditstar.service.widget.WidgetTaskService";
    public final static String TAG_ONCE = "once";
    public final static String TAG_PERIODIC = "periodic";
    private Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.wtf(TAG,"onCreate()");
        ((Initializer)getApplication()).getNetComponent().inject(this);
        mContext = getApplicationContext();
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);

    }


    @Override
    public int onRunTask(TaskParams taskParams) {

        Log.d(TAG,"onRunTask,tag="+taskParams.getTag());
        int result = GcmNetworkManager.RESULT_FAILURE;
        if(null == mContext)
        {
            Log.wtf(TAG,"onRunTask,Context is empty");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }

        if(taskParams.getTag().equals(TAG_ONCE) || taskParams.getTag().equals(TAG_PERIODIC)){
            Boolean isUserLoggedIn = UserState.isUserLoggedIn(mContext);
            apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
            String mAfter = sp.getString(SpConstants.WIDGET_AFTER,"");
            String mSort = sp.getString(SpConstants.SORT,"");
            String mTime = sp.getString(SpConstants.TIME,"");
            //Todo update this mAfter in response
            if(mAfter.equals("null"))
            {
                return GcmNetworkManager.RESULT_SUCCESS;
            }
            String mLimit = "25";
            Map<String,String> map = new HashMap<>();
            map.put("limit",mLimit);
            map.put("after",mAfter);
            map.put("sort",mSort);
            map.put("mTime",mTime);


            int widgetSub = sp.getInt(SpConstants.WIDGET_SUBSCRIBE,-1);
            String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(mContext) : "";
            Response<FrontPageResponse> res = null;
            try {
                switch (widgetSub){

                    case SpConstants.POPULAR:
                        res = apiInterface.getSubredditPosts(token, Constants.Subs.POPULAR,RedditSort.HOT,map).execute();
                        break;
                    case SpConstants.FRONT_PAGE:
                        res = apiInterface.getFrontPage(token,map).execute();
                        break;
                    case SpConstants.SUBREDDIT:
                        String subredditName = sp.getString(Constants.Subs.SUBREDDIT,null);
                        res = apiInterface.getSubredditPosts(token,subredditName,RedditSort.HOT,map).execute();
                        break;
                    case SpConstants.FAV:
                        break;
                    default:

                }
                if(res!=null)
                {
                    if(res.code()==200){
                        result = GcmNetworkManager.RESULT_SUCCESS;
                        List<FrontPageChild> mList = res.body().getData().getChildren();
                        String after  = res.body().getData().getAfter();
                        sp.edit().putString(SpConstants.WIDGET_AFTER,after).apply();
                        getContentResolver().delete(mUri,null,null);
                        Constants.insertPostsIntoTable(mContext,res.body(),mUri);
                    /*
                    update App widget
                     */
                        updateWidgets();
                    }else {
                        Log.d(TAG,"res code:"+res.code());
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            return result;
        }else {
            return result;
        }

    }

    private void updateWidgets(){
        Intent dataUpdatedIntent = new Intent(INTENT_TAG)
                .setPackage(getPackageName());
        sendBroadcast(dataUpdatedIntent);
    }
}
