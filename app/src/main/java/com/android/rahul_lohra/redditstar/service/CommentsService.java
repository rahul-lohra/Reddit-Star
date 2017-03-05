package com.android.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.helper.CommentsGsonTypeAdapter;
import com.android.rahul_lohra.redditstar.modal.comments.Child;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.comments.Example;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CommentsService extends IntentService {


    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    public static final String SUBREDDIT_NAME = "subreddit_name";
    public static final String POST_ID = "postId";

    List<CustomComment> customCommentList = new ArrayList<>();
    ApiInterface apiInterface;
    public boolean isUserLoggedIn = false;
    private final  String TAG = CommentsService.class.getSimpleName();
    int depth = 0;
    public CommentsService() {
        super("CommentsService");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((Initializer) getApplication()).getNetComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            isUserLoggedIn = UserState.isUserLoggedIn(getApplicationContext());
            apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
            String subbreditName = intent.getStringExtra(SUBREDDIT_NAME);
            String postId = intent.getStringExtra(POST_ID).substring(3);
            List<List<Example>> exampleList = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            map.put("depth", "5");
            map.put("showedits", "false");
            map.put("showmore", "false");
            map.put("limit", "30");

            try {
                Response<ResponseBody> res = apiInterface.getComments(postId, subbreditName, map).execute();

                if (res.code() == 200) {
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Example.class, new CommentsGsonTypeAdapter().nullSafe());
                    Gson gson = builder.create();
                    exampleList = gson.fromJson(res.body().string(), new TypeToken<ArrayList<Example>>() {}.getType());
                    traverse(exampleList.get(1).get(0), depth);
                    Log.d(TAG, "Custom List Size->" + customCommentList.size());

                    Constants.bulkInsertIntoCommentsTable(getApplicationContext(),customCommentList,postId, subbreditName);
                    customCommentList.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void traverse(Example example, int depth) {
        if (null == example) {
            --depth;
            return;
        } else {
            ++depth;
            for (Child child : example.data.children) {
                customCommentList.add(new CustomComment(depth, child));
                traverse(child.t1data.replies, depth);
            }
        }
    }


}
