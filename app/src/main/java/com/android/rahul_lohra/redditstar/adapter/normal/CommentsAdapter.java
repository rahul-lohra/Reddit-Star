package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.modal.comments.Child;
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
        int depth = list.get(position).getDepth();
        //set Margin and Color
        switch (depth){
            case 1: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
                break;
            case 2: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
                break;
            case 3: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.grey_500));
                break;
        }

        int margin = depth *20;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)viewHolder.view_2.getLayoutParams();
        params.setMarginStart(margin);
        viewHolder.view_2.setLayoutParams(params);
        viewHolder.view_2.requestLayout();

        //set Textual Data

        Child child = list.get(position).getChild();
        String comment = child.t1data.body;
        String author = child.t1data.author;
        int upvote = child.t1data.ups;
        viewHolder.tvComment.setText(comment);
        viewHolder.tvUsername.setText(author);
        viewHolder.tvUpvoteCount.setText(String.valueOf(upvote));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
