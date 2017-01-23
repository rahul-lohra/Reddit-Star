package com.android.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DrawerDropDown extends RecyclerView.ViewHolder {
    @Bind(R.id.tv)
    public TextView tv;
    @Bind(R.id.rv_sub)
    public RecyclerView rvSub;
    Context context;

    public DrawerDropDown(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        rvSub.setLayoutManager(new LinearLayoutManager(context));
    }
}
