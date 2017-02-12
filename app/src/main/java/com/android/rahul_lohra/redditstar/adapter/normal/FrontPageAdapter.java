package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChildData;
import com.android.rahul_lohra.redditstar.modal.frontPage.Preview;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by rkrde on 22-01-2017.
 */

public class FrontPageAdapter extends RecyclerView.Adapter {

    private Context context;
    List<FrontPageChild> list;


    public FrontPageAdapter(Context context, List<FrontPageChild> list) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostView postView = (PostView)holder;


        FrontPageChildData frontPageChildData = list.get(position).getData();
        final String id = frontPageChildData.getId();
        final String subreddit = frontPageChildData.getSubreddit();
        if(position==list.size()-1)
        {
            EventBus.getDefault().post("getNextData");
        }
        postView.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("subreddit",subreddit);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //set Textual Data
        Preview preview = frontPageChildData.getPreview();
        if(preview!=null){
//            Glide.with(context)
//                    .load(preview.getImages().get(0).getSource().getUrl())
//            .into(postView.imageView);

            Glide.with(context)
                    .load(frontPageChildData.getThumbnail())
            .into(postView.imageView);
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
