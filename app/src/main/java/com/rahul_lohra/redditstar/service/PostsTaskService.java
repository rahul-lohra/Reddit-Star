package com.rahul_lohra.redditstar.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.Utility.MyUrl;
import com.rahul_lohra.redditstar.Utility.RedditSort;
import com.rahul_lohra.redditstar.Utility.SpConstants;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 02-03-2017.
 */

public class PostsTaskService extends BaseGcmTaskService {


    final String TAG = PostsTaskService.class.getSimpleName();
    public  final static String INTENT_TAG = "com.rahul_lohra.redditstar.service.widget.WidgetTaskService";
    public final static String TAG_ONCE = "once";
    public final static String TAG_PERIODIC_FRONT_PAGE = "periodic_front_page";
    String filterParam_1 = MyUrl.Filter_Param_1.HOT;

    @Override
    public int onRunTask(TaskParams taskParams) {
        Boolean isUserLoggedIn = UserState.isUserLoggedIn(mContext);
        apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);

        Log.d(TAG,"onRunTask,tag="+taskParams.getTag());
        int result = GcmNetworkManager.RESULT_FAILURE;
        if(null == mContext)
        {
            Log.wtf(TAG,"onRunTask,Context is empty");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }

        if(taskParams.getTag().equals(TAG_PERIODIC_FRONT_PAGE)){
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
                        res = apiInterface.getFrontPage(token,filterParam_1,map).execute();
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
//                        getContentResolver().delete(widgetUri,null,null);
                        Constants.clearPosts(mContext,Constants.TYPE_WIDGET);
                        Constants.insertPostsIntoTable(mContext,res.body(), Constants.TYPE_WIDGET);
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
        } else if(taskParams.getTag().equals(TAG_PERIODIC_FRONT_PAGE)){
            Log.d(TAG,"Periodic FrontPage update Start");

            apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
            Map<String,String> map = new HashMap<>();
            map.put("limit","30");
            map.put("after","");
            String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(getApplicationContext()) : "";

            try {
                Response<FrontPageResponse> res = apiInterface.getFrontPage(token,filterParam_1,map).execute();
                if(res.code()==200)
                {
                    result = GcmNetworkManager.RESULT_SUCCESS;
                    Constants.clearPosts(getApplicationContext(),Constants.TYPE_POST);
                    Constants.clearComments(getApplicationContext());
                    Constants.insertPostsIntoTable(getApplicationContext(),res.body(), Constants.TYPE_POST);
                    for(FrontPageChild frontPageChild:res.body().getData().getChildren()){
                        String url = frontPageChild.getData().getThumbnail();
                        FutureTarget<File> future = Glide.with(getApplicationContext())
                                .load(url)
                                .downloadOnly(100, 100);
                    }
                }
                Log.d(TAG,"response:"+res.code());
            } catch (IOException e) {
                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
                result = GcmNetworkManager.RESULT_RESCHEDULE;
            }
            Log.d(TAG,"Periodic FrontPage update END");
            return result;
        }

        else {
            return result;
        }

    }

    private void updateWidgets(){
        Intent dataUpdatedIntent = new Intent(INTENT_TAG)
                .setPackage(getPackageName());
        sendBroadcast(dataUpdatedIntent);
    }
}
