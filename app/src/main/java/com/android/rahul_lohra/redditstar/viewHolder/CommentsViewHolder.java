package com.android.rahul_lohra.redditstar.viewHolder;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */

public class CommentsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view)
    View view;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_upvote_count)
    TextView tvUpvoteCount;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.tv_reply)
    TextView tvReply;
    @Bind(R.id.img_upVote)
    AppCompatImageView imgUpVote;
    @Bind(R.id.img_downVote)
    AppCompatImageView imgDownVote;

    public CommentsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
