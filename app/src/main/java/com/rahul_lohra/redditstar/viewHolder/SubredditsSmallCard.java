package com.rahul_lohra.redditstar.viewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */

public class SubredditsSmallCard extends RecyclerView.ViewHolder {

    @Bind(R.id.parent)
    public RelativeLayout parent;
    @Bind(R.id.tv_share)
    public TextView tvShare;

    public SubredditsSmallCard(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
