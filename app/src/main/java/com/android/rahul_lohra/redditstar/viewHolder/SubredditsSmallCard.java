package com.android.rahul_lohra.redditstar.viewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */

public class SubredditsSmallCard extends RecyclerView.ViewHolder {

    @Bind(R.id.image_view)
    public ImageView imageView;
    @Bind(R.id.tv_share)
    public TextView tvShare;
    @Bind(R.id.card_view)
    public CardView cardView;

    public SubredditsSmallCard(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
