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

public class DrawerNormal extends RecyclerView.ViewHolder {

    @Bind(R.id.tv)
    public TextView tv;

    public DrawerNormal(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
