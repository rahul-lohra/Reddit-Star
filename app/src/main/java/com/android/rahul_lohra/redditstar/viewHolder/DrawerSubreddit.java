package com.android.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DrawerSubreddit extends RecyclerView.ViewHolder implements SparkEventListener {
    @Bind(R.id.tv)
    public TextView tv;
    @Bind(R.id.spark_button)
    public SparkButton sparkButton;

    Context context;

    public DrawerSubreddit(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        sparkButton.setEventListener(this);
    }

    @Override
    public void onEvent(ImageView button, boolean buttonState) {

    }
}
