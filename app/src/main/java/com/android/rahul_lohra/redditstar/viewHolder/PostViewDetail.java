package com.android.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.ILogin;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.android.rahul_lohra.redditstar.utility.Constants.updateLikes;
import static com.android.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_DOWN;
import static com.android.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_NULL;
import static com.android.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_UP;

/**
 * Created by rkrde on 22-01-2017.
 */

public class PostViewDetail extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.image_url)
    AppCompatImageView imageUrl;
    @Bind(R.id.tv_domain)
    public TextView tvDomain;
    @Bind(R.id.tv_share)
    public TextView tvShare;
    @Bind(R.id.tv_comments)
    public TextView tvComments;
    @Bind(R.id.tv_vote)
    public TextView tvVote;
    @Bind(R.id.image_up_vote)
    public ImageView imageUpVote;
    @Bind(R.id.image_down_vote)
    public ImageView imageDownVote;
    @Bind(R.id.card_view)
    public CardView cardView;
    @Bind(R.id.tv_category)
    public TextView tvCategory;
    @Bind(R.id.tv_username)
    public TextView tvUsername;
    @Bind(R.id.linear_layout)
    public LinearLayout linearLayout;


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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
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

    public void setScores(String scores) {
        this.scores = scores;
    }

    public void performVoteAndUpdateLikes(@PostView.DirectionMode int mode, String thingId){

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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
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
                updateLikes(context,DIRECTION_NULL,id,scores,previousLikes);

            }else if(likes == 0) {
                //upvote
                performVoteAndUpdateLikes(PostView.DIRECTION_UP,id);
                updateLikes(context,PostView.DIRECTION_UP,id,scores,previousLikes);
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
                updateLikes(context,DIRECTION_NULL,id,scores,previousLikes);

            }else if(likes == 0) {
                //upvote
                performVoteAndUpdateLikes(PostView.DIRECTION_DOWN,id);
                updateLikes(context,PostView.DIRECTION_DOWN,id,scores,previousLikes);
            }
        }    }
}
