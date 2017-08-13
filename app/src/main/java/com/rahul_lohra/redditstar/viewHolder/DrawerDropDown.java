package com.rahul_lohra.redditstar.viewHolder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.cursor.SubredditDrawerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DrawerDropDown extends RecyclerView.ViewHolder {
    @BindView(R.id.tv)
    public TextView tv;
    @BindView(R.id.rv_sub)
    public RecyclerView rvSub;
    private Context context;
    private Drawable icon;

    @OnClick(R.id.tv)
    void onClickTv(){
        SubredditDrawerAdapter subredditDrawerAdapter =(SubredditDrawerAdapter)rvSub.getAdapter();
        if(subredditDrawerAdapter!=null ){
            int totalItems = subredditDrawerAdapter.getItemCount();
            if(totalItems>1 && rvSub.getVisibility()==View.VISIBLE)
            {
                //hide
                rvSub.setVisibility(View.GONE);
                Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_arrow_drop_down);
                tv.setCompoundDrawablesWithIntrinsicBounds(icon,null,drawable,null);

                return;
            }

            if(rvSub.getVisibility()==View.GONE && totalItems==0)
            {
                Toast.makeText(context,context.getString(R.string.no_items),Toast.LENGTH_SHORT).show();
                return;
            }
            if(rvSub.getVisibility()==View.GONE)
            {
                rvSub.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_arrow_drop_up_black);
                tv.setCompoundDrawablesWithIntrinsicBounds(icon,null,drawable,null);

            }
        }
    }

    public DrawerDropDown(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        rvSub.setLayoutManager(new LinearLayoutManager(context));
        rvSub.setVisibility(View.GONE);
        icon = ContextCompat.getDrawable(context,R.drawable.ic_toc_black_24dp);
    }

}
