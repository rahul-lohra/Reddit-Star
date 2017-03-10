package com.android.rahul_lohra.redditstar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */
public class CommentsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_2)
    public View view_2;
//    @Bind(R.id.view_3)
//    public View view_3;
    @Bind(R.id.tv_username)
    public TextView tvUsername;
    @Bind(R.id.tv_comment)
    public TextView tvComment;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.tv_upvote_count)
    public TextView tvUpvoteCount;
    @Bind(R.id.view2)
    public View view2;
    @Bind(R.id.tv_reply)
    public TextView tvReply;

    public CommentsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
