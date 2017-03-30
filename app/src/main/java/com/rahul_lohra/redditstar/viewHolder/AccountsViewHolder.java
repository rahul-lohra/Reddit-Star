package com.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 30-03-2017.
 */

public class AccountsViewHolder extends RecyclerView.ViewHolder implements SparkEventListener{

    @Bind(R.id.tv)
    public TextView tv;
    @Bind(R.id.spark_button)
    public SparkButton sparkButton;
    Context mContext;

    public AccountsViewHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        sparkButton.setEventListener(this);
    }
    public void init(String name,int isActive){
        tv.setText(name);
        if(isActive==1){
            sparkButton.setChecked(true);
        }else {
            sparkButton.setChecked(false);

        }
    }

    @Override
    public void onEvent(ImageView button, boolean buttonState) {

    }

    @Override
    public void onEventAnimationEnd(ImageView button, boolean buttonState) {

    }

    @Override
    public void onEventAnimationStart(ImageView button, boolean buttonState) {

    }

}
