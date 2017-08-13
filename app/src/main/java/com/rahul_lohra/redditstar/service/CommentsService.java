package com.rahul_lohra.redditstar.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.IntDef;

import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.helper.CommentsGsonTypeAdapter;
import com.rahul_lohra.redditstar.modal.t1_Comments.Child;
import com.rahul_lohra.redditstar.modal.t1_Comments.CustomComment;
import com.rahul_lohra.redditstar.modal.t1_Comments.Example;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.annotation.RetentionPolicy.SOURCE;


@SuppressWarnings("HardCodedStringLiteral")
public class CommentsService  {


    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    @Inject
    Context context;

    public static final String SUBREDDIT_NAME = "subreddit_name";
    public static final String POST_ID = "postId";
    private ICommentsService iCommentsService;

    List<CustomComment> customCommentList = new ArrayList<>();
    ApiInterface apiInterface;
    public boolean isUserLoggedIn = false;
    private final  String TAG = CommentsService.class.getSimpleName();
    int depth = 0;

    public CommentsService(Context context) {
        this.context = context;
        ((Initializer) context).getNetComponent().inject(this);
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

    public void requestComments(Context context,String id,String subreddit){

        if(iCommentsService!=null){
            iCommentsService.commentsLoadedState(TYPE_LOADING);
        }
        String mProj[] = {CommentsColumn.KEY_SQL_ID};
        String mWhere = CommentsColumn.KEY_LINK_ID + "=?";
        String mWhereArgs[] = {id};
        Cursor cursor = context.getContentResolver().query(MyProvider.CommentsLists.CONTENT_URI, mProj, mWhere, mWhereArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                if(iCommentsService!=null){
                    iCommentsService.commentsLoadedState(TYPE_LOAD_SUCCESS);
                }
            } else {

                Intent intent = new Intent(context, CommentsService.class);
                intent.putExtra(CommentsService.POST_ID,id);
                intent.putExtra(CommentsService.SUBREDDIT_NAME, subreddit);
//                context.startService(intent);
                fetchComments(intent);
            }
            cursor.close();
        }
    }


    public void setICommentsService(ICommentsService iCommentsService) {
        this.iCommentsService = iCommentsService;
    }

    public   interface ICommentsService{
        void commentsLoadedState(@CommentsLoadState int commentsLoadType);
    }

    @Retention(SOURCE)
    @IntDef({TYPE_LOADING, TYPE_LOAD_SUCCESS, TYPE_LOAD_FAIL})
    public @interface CommentsLoadState {
    }

    public static final int TYPE_LOADING = 1;
    public static final int TYPE_LOAD_SUCCESS = 2;
    public static final int TYPE_LOAD_FAIL = 3;




    private void fetchComments(Intent intent){
        if (intent != null) {
            isUserLoggedIn = UserState.isUserLoggedIn(context);
            apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
            final String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(context) : "";
            final String subbreditName = intent.getStringExtra(SUBREDDIT_NAME);
            final String postId = intent.getStringExtra(POST_ID).substring(3);

            Map<String, String> map = new HashMap<>();
            map.put("depth", "7");
            map.put("showedits", "false");
            map.put("showmore", "false");
            map.put("limit", "50");


                apiInterface.getComments(token,postId, subbreditName, map).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> res) {
                        try{
                            if (res.code() == 200) {
                                GsonBuilder builder = new GsonBuilder();
                                builder.registerTypeAdapter(Example.class, new CommentsGsonTypeAdapter().nullSafe());
                                Gson gson = builder.create();
                                List<List<Example>> exampleList = new ArrayList<>();
                                exampleList = gson.fromJson(res.body().string(), new TypeToken<ArrayList<Example>>() {}.getType());
                                traverse(exampleList.get(1).get(0), depth);
                                Constants.bulkInsertIntoCommentsTable(context,customCommentList,postId, subbreditName);
                                customCommentList.clear();

                                if(iCommentsService!=null)
                                    iCommentsService.commentsLoadedState(TYPE_LOAD_SUCCESS);
                            }
                        }catch (IOException e){
                            if(iCommentsService!=null)
                                iCommentsService.commentsLoadedState(TYPE_LOAD_FAIL);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(iCommentsService!=null)
                            iCommentsService.commentsLoadedState(TYPE_LOAD_FAIL);
                    }
                });

        }
    }

}
