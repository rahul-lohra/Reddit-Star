package com.rahul_lohra.redditstar.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.Share;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rkrde on 07-04-2017.
 */

public class DetailPostView extends FrameLayout {

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
    @Bind(R.id.image_view)
    public AppCompatImageView imageView;
    private DetailPostModal detailPostModal;
    private WeakReference<Activity> weakActivity;
    private Context mContext;

    @OnClick(R.id.tv_share)
    void onClickShare(){
        if(null == weakActivity.get())
            return;
        Share share = new Share();
        share.shareUrl(weakActivity.get(),detailPostModal.getUrl());
    }


    public DetailPostView(@NonNull Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    public DetailPostView(@NonNull Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        init();
    }

    public DetailPostView(@NonNull Context mContext, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        this.mContext = mContext;
        init();
    }

    public DetailPostView(@NonNull Context mContext, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(mContext, attrs, defStyleAttr, defStyleRes);
        this.mContext = mContext;
        init();
    }

    private void init(){
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.my_search_view, this, true);
        ButterKnife.bind(this,view);
    }

    public void setUpData(DetailPostModal detailPostModal){
        this.detailPostModal = detailPostModal;
        tvVote.setText(detailPostModal.getScore());
        tvTitle.setText(detailPostModal.getTitle());
        tvComments.setText(detailPostModal.getCommentsCount());
        tvDomain.setText(detailPostModal.getDomain());
        tvVote.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        tvComments.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        tvShare.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        tvCategory.setText("r/" + detailPostModal.getSubreddit() + "-" + detailPostModal.getTime());
        tvUsername.setText(detailPostModal.getAuthor());
        
        setLikes(detailPostModal.getLikes());
        setUpImage();
    }

    private void setLikes(Integer likes) {
        if(likes!=0){
            if(likes==1){
                Glide.with(mContext)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_upward_true)
                        .into(imageUpVote);
                Glide.with(mContext)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_downward)
                        .into(imageDownVote);
            }else if(likes==-1){
                Glide.with(mContext)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_upward)
                        .into(imageUpVote);
                Glide.with(mContext)
                        .load("")
                        .placeholder(R.drawable.ic_arrow_downward_true)
                        .into(imageDownVote);
            }
        }
    }

    public void setActivity(WeakReference<Activity> weakActivity){
        this.weakActivity = weakActivity;
    }

    private void setUpImage(){
        int hasBigImage = detailPostModal.getBigImageUrlHasImage();
        int hasThumbnail = detailPostModal.getThumbnailHasImage();

//        if(hasBigImage!=1 && hasThumbnail ==1){
//            Glide.with(mContext)
//                    .load(detailPostModal.getThumbnail())
//                    .into(imageView);
//        }else {
//            imageView.setVisibility(View.GONE);
//        }

        if(hasBigImage ==1){
            Glide.with(mContext)
                    .load(detailPostModal.getBigImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            e.printStackTrace();
                            loadThumbnail();
//                            collapsingToolbarLayout.setVisibility(View.GONE);
                            imageView.setVisibility(View.GONE);
                            imageView.setTransitionName("");
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            updatePalete(resource);
                            imageView.setImageDrawable(resource);
                            return true;
                        }
                    })
                    .into(imageView);
            imageView.setTransitionName("profile");
            imageView.setVisibility(View.VISIBLE);
        }else {
            loadThumbnail();
//            collapsingToolbarLayout.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            imageView.setTransitionName("");
        }


    }

    private void loadThumbnail() {

        if(detailPostModal.getThumbnailHasImage()==1){
            if (detailPostModal.getThumbnail() != null) {
                imageView.setTransitionName("profile");
                imageView.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(detailPostModal.getThumbnail())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                updatePalete(resource);
                                imageView.setImageDrawable(resource);
                                return true;
                            }
                        })
                        .into(imageView);
            }
        }else {
            imageView.setTransitionName("");
            imageView.setVisibility(View.GONE);
        }
    }

    private void updatePalete(Drawable drawable){

    }
}
