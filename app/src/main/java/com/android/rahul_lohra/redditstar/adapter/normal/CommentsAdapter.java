package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.comments.Example;
import com.android.rahul_lohra.redditstar.viewHolder.CommentsViewHolder;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;


import java.util.List;

import butterknife.Bind;

/**
 * Created by rkrde on 22-01-2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter {


    private Context context;
    List<CustomComment> list;


    public CommentsAdapter(Context context, List<CustomComment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        v = new CommentsViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_comments, parent, false));
        return v;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentsViewHolder viewHolder = (CommentsViewHolder)holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
