package com.rahul_lohra.redditstar.viewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.activity.MediaActivity;
import com.rahul_lohra.redditstar.contract.IActivity;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.bumptech.glide.Glide;
import com.rahul_lohra.redditstar.viewHolder.modal.PostViewData;

import java.lang.annotation.Retention;
import java.lang.ref.WeakReference;
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

import static com.rahul_lohra.redditstar.factory.DomainFactory.DOMAIN_GFYCAT;
import static com.rahul_lohra.redditstar.factory.DomainFactory.DOMAIN_IMGUR_1;
import static com.rahul_lohra.redditstar.factory.DomainFactory.DOMAIN_IMGUR_2;
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
    @Nullable
    @Bind(R.id.card_view)
    public CardView cardView;
//    private String domain, url;
    private Integer likes;
    DetailPostModal detailPostModal;
    private WeakReference<Activity> weakActivity;
    private Context context;

    @Retention(SOURCE)
    @IntDef({DIRECTION_UP, DIRECTION_DOWN, DIRECTION_NULL})
    public @interface DirectionMode {
    }

    public static final int DIRECTION_DOWN = -1;
    public static final int DIRECTION_NULL = 0;
    public static final int DIRECTION_UP = 1;

    private final String TAG = PostView.class.getSimpleName();
    private IActivity iActivity;
    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;

    @OnClick(R.id.imageView)
    public void onClickImageView() {
        String domain = detailPostModal.getDomain();
        String url = detailPostModal.getUrl();
        if (domain.equals(DOMAIN_GFYCAT) || domain.equals(DOMAIN_IMGUR_1) || domain.equals(DOMAIN_IMGUR_2)) {

            Intent intent = new Intent(context, MediaActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("domain", domain);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }

    public PostView(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        ((Initializer) context.getApplicationContext()).getNetComponent().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);

        tvTitle.setTextColor(ContextCompat.getColor(context, R.color.red_youtube));
    }

    public void init(Activity activity,DetailPostModal detailPostModal) {
        this.weakActivity = new WeakReference<Activity>(activity);
        this.detailPostModal = detailPostModal;
        
        setLikes(detailPostModal.getLikes());
        setTvTitle(detailPostModal.getSubreddit());

        loadImageNew();

    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
        if (likes != 0) {
            if (likes == 1) {
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_upward_true)
                        .into(imageUpVote);
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_downward)
                        .into(imageDownVote);
            } else if (likes == -1) {
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

    public void performVoteAndUpdateLikes(@DirectionMode int mode, String thingId) {

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


        switch (mode) {
            case DIRECTION_UP: {
                likes = 1;
                updateVoteBackground(imageUpVote, R.drawable.ic_arrow_upward_true);
                updateVoteBackground(imageDownVote, R.drawable.ic_arrow_downward);

            }
            break;
            case DIRECTION_DOWN: {
                likes = 0;
                updateVoteBackground(imageUpVote, R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote, R.drawable.ic_arrow_downward_true);

            }
            break;
            case DIRECTION_NULL: {
                likes = -1;
                updateVoteBackground(imageUpVote, R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote, R.drawable.ic_arrow_downward);
            }
            break;
            default:
        }

    }

    private void updateVoteBackground(ImageView imageView, int resId) {
        Glide.with(context)
                .load("")
                .placeholder(resId)
                .into(imageView);
    }

    private void setTvTitle(String title) {
        tvTitle.setText("r/" + title);
    }

    private void loadImageNew() {
        Activity activity = weakActivity.get();
        if(null ==activity ){
            return;
        }
        final String bigImageUrl = detailPostModal.getBigImageUrl();
        if(detailPostModal.getBigImageUrlHasImage() ==1){
            Glide.with(activity)
                    .load(bigImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.e("error:",bigImageUrl);
                            e.printStackTrace();
                            loadThumbnail();
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
            imageView.setTransitionName("profile");
            imageView.setVisibility(View.VISIBLE);
        }else {
            loadThumbnail();
        }
    }

    private void loadThumbnail() {
        Activity activity = weakActivity.get();
        if(null ==activity ){
            return;
        }

        if(detailPostModal.getThumbnailHasImage()==1){
            String thumbnail = detailPostModal.getThumbnail();
            if (thumbnail != null) {
                imageView.setTransitionName("profile");
                imageView.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(thumbnail)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
        }else {
            imageView.setTransitionName("");
            imageView.setVisibility(View.GONE);
        }
    }
}
