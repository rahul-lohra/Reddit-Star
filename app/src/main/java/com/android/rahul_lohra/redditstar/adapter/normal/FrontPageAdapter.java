package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;

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
        postView.tvTitle.setText(list.get(position).getData().getSubreddit());
        postView.tvDetail.setText(list.get(position).getData().getTitle());

        if(position==list.size()-1)
        {
            EventBus.getDefault().post("getNextData");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
