package com.android.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.bumptech.glide.Glide;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by rkrde on 22-01-2017.
 */

public class PostView extends RecyclerView.ViewHolder {
    @Bind(R.id.imageView)
    public ImageView imageView;
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.tv_detail)
    public TextView tvDetail;
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

    private Boolean likes;
    private Context context;

    @Retention(SOURCE)
    @IntDef({DIRECTION_UP, DIRECTION_DOWN, DIRECTION_NULL})
    public @interface DirectionMode {}
    public static final int DIRECTION_DOWN = -1;
    public static final int DIRECTION_NULL = 0;
    public static final int DIRECTION_UP = 1;

    private final String TAG = PostView.class.getSimpleName();

    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;
    public PostView(Context context,View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        ((Initializer)context.getApplicationContext()).getNetComponent().inject(this);
        apiInterface =retrofit.create(ApiInterface.class);
    }

    public Boolean getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

    public void performVote(@DirectionMode int mode,String thingId){

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
                likes=true;
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward_true);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);

            }
                break;
            case DIRECTION_DOWN:{
                likes=false;
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward_true);

            }
                break;
            case DIRECTION_NULL:{
                likes = null;
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
}
