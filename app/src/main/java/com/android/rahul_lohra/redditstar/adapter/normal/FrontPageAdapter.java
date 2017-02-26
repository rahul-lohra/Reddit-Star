package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.fragments.SearchFragment;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChildData;
import com.android.rahul_lohra.redditstar.modal.frontPage.Image;
import com.android.rahul_lohra.redditstar.modal.frontPage.Preview;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.Share;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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

public class FrontPageAdapter extends RecyclerView.Adapter {

    private Context context;
    List<FrontPageChild> list;
    private Retrofit retrofit;
    private ApiInterface apiInterface;
    private String TAG = FrontPageAdapter.class.getSimpleName();
    private Fragment fragment;
    private IFrontPageAdapter iFrontPageAdapter;


    public interface IFrontPageAdapter{
        void sendData(DetailPostModal modal, ImageView imageView);
    }

    public FrontPageAdapter(Fragment fragment,Context context, List<FrontPageChild> list, Retrofit retrofit,IFrontPageAdapter iFrontPageAdapter) {
        this.context = context;
        this.list = list;
        this.retrofit = retrofit;
        this.apiInterface = retrofit.create(ApiInterface.class);
        this.fragment = fragment;
        this.iFrontPageAdapter = iFrontPageAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        v = new PostView(context,LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_posts, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PostView postView = (PostView) holder;

        final FrontPageChildData frontPageChildData = list.get(position).getData();
        final String id = frontPageChildData.getId();
        final String subreddit = frontPageChildData.getSubreddit();
        final String thingId = frontPageChildData.getName();
        final String author = frontPageChildData.getAuthor();
        final long createdUtc = frontPageChildData.getCreatedUtc();
        final String time = Constants.getTimeDiff(createdUtc);
        final String ups = String.valueOf(frontPageChildData.getUps());
        final String title = String.valueOf(frontPageChildData.getTitle());
        final String commentsCount = String.valueOf(frontPageChildData.getNumComments());
        final Preview preview = frontPageChildData.getPreview();
        final String thumbnail = (preview!=null)?frontPageChildData.getThumbnail():"";
        final String url = frontPageChildData.getUrl();

        final Boolean likes = frontPageChildData.getLikes();
        postView.setLikes(likes);



        final List<String> bigImageUrlList = new ArrayList<>();
        if(preview!=null){
            for(Image image:frontPageChildData.getPreview().getImages()){
                bigImageUrlList.add(image.getSource().getUrl());
            }
        }

        if (likes != null) {
            Integer resId = (likes) ? R.drawable.ic_arrow_upward_true : R.drawable.ic_arrow_downward_true;
            Glide.with(context)
                    .load("")
                    .placeholder(resId)
                    .into((likes) ?postView.imageUpVote:postView.imageDownVote);

        }
        if(!(fragment instanceof SearchFragment)){

            if (position == list.size() - 1 && list.size()>4) {
                EventBus.getDefault().post("getNextData");
            }
        }

        postView.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPostModal modal = new DetailPostModal(id,
                        subreddit,ups,title,commentsCount,thumbnail,time,author,bigImageUrlList,likes);
                iFrontPageAdapter.sendData(modal,postView.imageView);
            }
        });

        postView.imageUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                boolean loggedIn = UserState.isUserLoggedIn(context);

                if (!loggedIn) {
                    Toast.makeText(context, context.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean mLikes = postView.getLikes();
                if(mLikes!=null){
                    if(!mLikes)
                    {
                        postView.performVote(PostView.DIRECTION_NULL,thingId);
                        frontPageChildData.setLikes(null);
                    }
                }else {
                    //upvote
                    postView.performVote(PostView.DIRECTION_UP,thingId);
                    frontPageChildData.setLikes(true);
                }

            }
        });

//        postView.imageView.setOnClickListener((View v)->{});

        postView.imageDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean loggedIn = UserState.isUserLoggedIn(context);

                if (!loggedIn) {
                    Toast.makeText(context, context.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean mLikes = postView.getLikes();
                if(mLikes!=null){
                    if(mLikes)
                    {
                        postView.performVote(PostView.DIRECTION_NULL,thingId);
                        frontPageChildData.setLikes(null);
                    }
                }else {
                    //upvote
                    postView.performVote(PostView.DIRECTION_DOWN,thingId);
                    frontPageChildData.setLikes(false);
                }
            }
        });

        postView.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share share = new Share();
                share.shareUrl(fragment.getActivity(),url);
            }
        });

        //set Textual Data

        if (preview != null) {
            Glide.with(fragment)
                    .load(frontPageChildData.getThumbnail())
                    .centerCrop()
                    .crossFade()
                    .into(postView.imageView);
        }else {
            postView.imageView.setVisibility(View.GONE);
        }

        postView.tvVote.setText(String.valueOf(frontPageChildData.getUps()));
        postView.tvTitle.setText(list.get(position).getData().getSubreddit());
        postView.tvDetail.setText(list.get(position).getData().getTitle());
        postView.tvComments.setText(String.valueOf(list.get(position).getData().getNumComments()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
