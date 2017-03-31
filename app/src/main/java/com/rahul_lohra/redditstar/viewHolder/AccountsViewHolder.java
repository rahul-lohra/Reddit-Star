package com.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.adapter.cursor.AccountsAdapter;
import com.rahul_lohra.redditstar.modal.FavoritesModal;
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
    private Context mContext;
    private int mSqlId;
    public interface IAccountsViewHolder{
        void disableAnonymousUser();
    }
    private IAccountsViewHolder mListener;

    public AccountsViewHolder(Context context, View itemView, IAccountsViewHolder listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        sparkButton.setEventListener(this);
        this.mListener = listener;
    }
    public void init(String name,int isActive,int sqlId){
        tv.setText(name);
        if(isActive==1){
            sparkButton.setChecked(true);
        }else {
            sparkButton.setChecked(false);

        }
        this.mSqlId = sqlId;
    }

    @Override
    public void onEvent(ImageView button, boolean buttonState) {
//        button.setClickable(false);
    }

    @Override
    public void onEventAnimationEnd(ImageView button, boolean buttonState) {
//        if(buttonState)
//        {
//            //insert
//            mContext.getContentResolver().
//        }else {
//            //remove
//            Constants.deleteFromFavoritesDb(context,fullName);
//        }
        Constants.updateActiveUser(mContext,mSqlId);
        if(mListener!=null)
            mListener.disableAnonymousUser();

    }

    @Override
    public void onEventAnimationStart(ImageView button, boolean buttonState) {
//        button.setClickable(false);
    }

}
