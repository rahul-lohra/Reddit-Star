package com.android.rahul_lohra.redditstar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public PostView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
