package com.rahul_lohra.redditstar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DrawerSearchItem extends RecyclerView.ViewHolder {
    @Bind(R.id.et)
    public EditText et;

    public DrawerSearchItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
