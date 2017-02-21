package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.activity.ReplyActivity;
import com.android.rahul_lohra.redditstar.modal.T3_Kind;
import com.android.rahul_lohra.redditstar.modal.comments.Child;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChildData;
import com.android.rahul_lohra.redditstar.modal.frontPage.Preview;
import com.android.rahul_lohra.redditstar.modal.t3_Link.T3_Data;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.CommentsViewHolder;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 22-01-2017.
 */

public class T3_LinkSearchAdapter extends RecyclerView.Adapter {


    private Context context;
    List<T3_Kind> list;
    private Retrofit retrofit;
    private ApiInterface apiInterface;
    private static final String TAG  = T3_LinkSearchAdapter.class.getSimpleName();

    public T3_LinkSearchAdapter(Context context, List<T3_Kind> list, Retrofit retrofit) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        v = new PostView(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_posts, parent, false));
        return v;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PostView postView = (PostView) holder;


        T3_Data t3Data = list.get(position).data;
        final String id = t3Data.id;
        final String subreddit = t3Data.subreddit;
        final String thingId = t3Data.name;

        final Object objLikes = t3Data.likes;
        if (objLikes != null) {
            Boolean b = (Boolean) objLikes;
            Integer resId = (b) ? R.drawable.ic_arrow_upward_true : R.drawable.ic_arrow_downward_true;
//            Glide.with(context)
//                    .load(resId)
//                    .into(postView.imageUpVote);


        }
        if (position == list.size() - 1 && list.size()>4) {
            EventBus.getDefault().post("getNextData");
        }

        //click
        postView.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("subreddit", subreddit);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        postView.imageUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean loggedIn = UserState.isUserLoggedIn(context);

                if (!loggedIn) {
                    Toast.makeText(context, context.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    return;
                }


                int dir = 1;
                if (objLikes != null) {
                    Boolean b = (Boolean) objLikes;
                    dir = (b) ? 0 : 1;
                }
//                makeVoteRequest(thingId,dir);

                String token = UserState.getAuthToken(context);
                String auth = "bearer " + token;
                Map<String, String> map = new HashMap<String, String>();
                map.put("dir", String.valueOf(dir));
                map.put("id", thingId);
                map.put("rank", String.valueOf(2));

                apiInterface.postVote(auth, map).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d(TAG, "UpVote onResponse:" + response.code());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "UpVote onFail:" + t.getMessage());
                    }
                });
            }
        });

        postView.imageDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean loggedIn = UserState.isUserLoggedIn(context);

                if (!loggedIn) {
                    Toast.makeText(context, context.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    return;
                }

                int dir = -1;
                if (objLikes != null) {
                    Boolean b = (Boolean) objLikes;
                    dir = (b) ? -1 : 0;
                }
//                makeVoteRequest(thingId,dir);

                String token = UserState.getAuthToken(context);
                String auth = "bearer " + token;
                Map<String, String> map = new HashMap<String, String>();
                map.put("dir", String.valueOf(dir));
                map.put("id", thingId);
                map.put("rank", String.valueOf(2));

                apiInterface.postVote(auth, map).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d(TAG, "DownVote onResponse:" + response.code());
//                        setVote(postView.imageDownVote,Integer.parseInt(dir));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "DownVote onFail:" + t.getMessage());
                    }
                });
            }
        });

        //set Textual Data
        Preview preview = t3Data.preview;
        if (preview != null) {
            Glide.with(context)
                    .load(t3Data.thumbnail)
                    .centerCrop()
                    .crossFade()
                    .into(postView.imageView);
        }else {
            postView.imageView.setVisibility(View.GONE);
        }

        postView.tvVote.setText(String.valueOf(t3Data.ups));
        postView.tvTitle.setText(t3Data.subreddit);
        postView.tvDetail.setText(t3Data.title);
        postView.tvComments.setText(String.valueOf(t3Data.numComments));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
