package com.android.rahul_lohra.redditstar.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.SparseArray;

import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.comments.Child;
import com.android.rahul_lohra.redditstar.modal.comments.CommentsGsonTypeAdapter;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.comments.Example;
import com.android.rahul_lohra.redditstar.modal.comments.Replies;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 09-02-2017.
 */

public class CommentsLoader extends AsyncTaskLoader<List<CustomComment>> {
    private static final String TAG = CommentsLoader.class.getSimpleName();

//    @Inject
//    @Named("fun")
//    Retrofit retrofitWithToken;

    List<CustomComment> customCommentList = new ArrayList<>();
    int depth = 0;

    @Inject
    @Named("token")
    Retrofit retrofitWithoutToken;

    ApiInterface apiInterface;
    String subbreditName;
    String commentId;

    public CommentsLoader(Context context, String subbreditName, String commentId) {
        super(context);
        ((Initializer) context.getApplicationContext()).getNetComponent().inject(this);
        apiInterface = retrofitWithoutToken.create(ApiInterface.class);
        this.subbreditName = subbreditName;
        this.commentId = commentId;
    }

    @Override
    public List<CustomComment> loadInBackground() {
        //determine whether you are logged in or not
        customCommentList = new ArrayList<>();
        List<List<Example>> exampleList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("depth", "3");
        map.put("showedits", "false");
        map.put("showmore", "false");
        map.put("limit", "15");
        try {
            Response<ResponseBody> res = apiInterface.getComments(commentId, subbreditName, map).execute();

            if (res.code() == 200) {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Example.class, new CommentsGsonTypeAdapter().nullSafe());
                Gson gson = builder.create();
                exampleList = gson.fromJson(res.body().string(), new TypeToken<ArrayList<Example>>() {
                }.getType());
                traverse(exampleList.get(1).get(0), depth);
                Log.d(TAG, "Custom List Size->" + customCommentList.size());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customCommentList;
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
