package com.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.contract.ILogin;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.bumptech.glide.Glide;
import com.rahul_lohra.redditstar.Utility.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_DOWN;
import static com.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_NULL;
import static com.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_UP;

/**
 * Created by rkrde on 22-01-2017.
 */

public class PostViewDetail extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.image_url)
    AppCompatImageView imageUrl;
    @BindView(R.id.tv_domain)
    public TextView tvDomain;
    @BindView(R.id.tv_share)
    public TextView tvShare;
    @BindView(R.id.tv_comments)
    public TextView tvComments;
    @BindView(R.id.tv_vote)
    public TextView tvVote;
    @BindView(R.id.image_up_vote)
    public ImageView imageUpVote;
    @BindView(R.id.image_down_vote)
    public ImageView imageDownVote;
    @BindView(R.id.card_view)
    public CardView cardView;
    @BindView(R.id.tv_category)
    public TextView tvCategory;
    @BindView(R.id.tv_username)
    public TextView tvUsername;
    @BindView(R.id.linear_layout)
    public LinearLayout linearLayout;
    @BindView(R.id.image_view)
    public AppCompatImageView imageView;


    private Integer likes;
    private Context context;
    private String url;
    private String scores;
    private ILogin mListener;
    private String id;

    public interface IPostViewDetail{
        void showLogin();
    }

    private final String TAG = PostViewDetail.class.getSimpleName();




    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;
    public PostViewDetail(Context context, View itemView,ILogin mListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        ((Initializer)context.getApplicationContext()).getNetComponent().inject(this);
        apiInterface =retrofit.create(ApiInterface.class);
        this.mListener = mListener;
    }

    public void initFromDb(int hasBigImage,
                           int hasThumbnail,
                           String thumbnail,
                           Integer likes,
                           String scores,
                           String url,
                           String id,
                           String title,
                           String commentsCount,
                           String domain,
                           String subreddit,
                           String time,
                           String author){
        if(hasBigImage!=1 && hasThumbnail ==1){
            Glide.with(context)
                    .load(thumbnail)
                    .into(imageView);
        }else {
            imageView.setVisibility(View.GONE);
        }
        setLikes(likes);
        setScores(scores);
        setUrl(url);
        setId(id);
        setTextualData(scores,title,commentsCount,domain,subreddit,time,author);
    }

    private void initFromNetwork(){

    }

    private void setTextualData(String score,String title,String commentsCount,String domain,String subreddit,String time,String author){
        tvVote.setText(score);
        tvTitle.setText(title);
        tvComments.setText(commentsCount);
        tvDomain.setText(domain);
        tvVote.setTextColor(ContextCompat.getColor(context,R.color.white));
        tvComments.setTextColor(ContextCompat.getColor(context,R.color.white));
        tvShare.setTextColor(ContextCompat.getColor(context,R.color.white));
        tvCategory.setText("r/" + subreddit + "-" + time);
        tvUsername.setText(author);

    }

    public Integer getLikes() {
        return likes;
    }

    private void setLikes(Integer likes) {
        this.likes = likes;
        if(likes!=0){
            if(likes==1){
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_upward_true)
                        .into(imageUpVote);
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_downward)
                        .into(imageDownVote);
            }else if(likes==-1){
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_upward)
                        .into(imageUpVote);
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_downward_true)
                        .into(imageDownVote);
            }
        }
    }

    private void setScores(String scores) {
        this.scores = scores;
    }

    private void performVoteAndUpdateLikes(@PostView.DirectionMode int mode, String thingId){

        String token = UserState.getAuthToken(context);
        String auth = "bearer " + token;
        Map<String, String> map = new HashMap<String, String>();
        map.put("dir", String.valueOf(mode));
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


        switch (mode){
            case DIRECTION_UP:{
                likes=1;
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward_true);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);

            }
                break;
            case DIRECTION_DOWN:{
                likes=-1;
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward_true);

            }
                break;
            case DIRECTION_NULL:{
                likes = 0;
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);
            }
                break;
            default:
        }

    }

    private void updateVoteBackground(ImageView imageView,int resId){
        Glide.with(context)
                .load("")
                .placeholder(resId)
                .into(imageView);
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void openLinkInBrowser(){
        if(null == url){

        }else {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    @OnClick(R.id.image_url)
    void OnCLickImageUrl(){
        openLinkInBrowser();
    }

    @OnClick(R.id.image_up_vote)
    void OnCLickUpVote(){
        boolean loggedIn = UserState.isUserLoggedIn(context);
        if (!loggedIn) {
            mListener.pleaseLogin();
        }else {
            int previousLikes = likes;
            if(likes == -1)
            {
                performVoteAndUpdateLikes(DIRECTION_NULL,id);
                Constants.updateLikes(context,DIRECTION_NULL,id,scores,previousLikes);

            }else if(likes == 0) {
                //upvote
                performVoteAndUpdateLikes(PostView.DIRECTION_UP,id);
                Constants.updateLikes(context,PostView.DIRECTION_UP,id,scores,previousLikes);
            }
        }
    }

    @OnClick(R.id.image_down_vote)
    void OnCLickDownVote(){
        boolean loggedIn = UserState.isUserLoggedIn(context);
        if (!loggedIn) {
            mListener.pleaseLogin();
        }else {
            int previousLikes = likes;
            if(likes == 1)
            {
                performVoteAndUpdateLikes(DIRECTION_NULL,id);
                Constants.updateLikes(context,DIRECTION_NULL,id,scores,previousLikes);

            }else if(likes == 0) {
                //upvote
                performVoteAndUpdateLikes(PostView.DIRECTION_DOWN,id);
                Constants.updateLikes(context,PostView.DIRECTION_DOWN,id,scores,previousLikes);
            }
        }    }
}
