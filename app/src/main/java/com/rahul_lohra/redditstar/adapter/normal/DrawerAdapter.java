package com.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.activity.FavoriteActivity;
import com.rahul_lohra.redditstar.contract.IActivity;
import com.rahul_lohra.redditstar.modal.DrawerItemModal;
import com.rahul_lohra.redditstar.viewHolder.DrawerDropDown;
import com.rahul_lohra.redditstar.viewHolder.DrawerNormal;
import com.rahul_lohra.redditstar.viewHolder.DrawerSearchItem;

import java.util.List;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter {
    private Context context;
    private static final int SEARCH_TYPE = 1;
    private static final int NORMAL_TYPE = 2;
    private static final int EXPANDABLE_TYPE = 3;

    private List<DrawerItemModal> list;
    private ISubreddit iSubreddit;
    private IActivity iActivity;
    public interface ISubreddit{
        void getSubredditRecyclerView(RecyclerView recyclerView) ;
//        void openActivity(Class<?> cls);
    }

    public DrawerAdapter(Context context,List<DrawerItemModal> list,ISubreddit iSubreddit,IActivity iActivity) {
        this.context = context;
        this.list = list;
        this.iSubreddit = iSubreddit;
        this.iActivity = iActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        switch(viewType){
            case SEARCH_TYPE:
                 v = new DrawerSearchItem(LayoutInflater.from(parent.getContext()).
                         inflate(R.layout.item_drawer_search, parent, false));
                break;
            case NORMAL_TYPE:
                 v = new DrawerNormal(LayoutInflater.from(parent.getContext()).
                         inflate(R.layout.item_drawer_normal, parent, false));
                break;
            case EXPANDABLE_TYPE:
                 v = new DrawerDropDown(context,LayoutInflater.from(parent.getContext()).
                         inflate(R.layout.item_drawer_drop_down, parent, false));
                break;
        }
        return v;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String text = list.get(position).getName();
        Drawable drawable = list.get(position).getDrawable();
        switch (holder.getItemViewType()) {
            case NORMAL_TYPE:
                DrawerNormal drawerNormal = (DrawerNormal)holder;
                drawerNormal.tv.setText(text);
                drawerNormal.tv.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null);

                if(text.equals(context.getString(R.string.my_favorites))){
                    drawerNormal.tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            iActivity.openActivity(new Intent(context,FavoriteActivity.class));
//                            context.startActivity(new Intent(context, FavoriteActivity.class));
                        }
                    });
                }
                if(text.equals(context.getString(R.string.settings))) {
                    drawerNormal.tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            iActivity.openActivity(new Intent(context,FavoriteActivity.class));
//                            context.startActivity(new Intent(context, SettingsActivity.class));
                        }
                    });
                }
                break;
            case SEARCH_TYPE:
                DrawerSearchItem drawerSearchItem = (DrawerSearchItem)holder;
                drawerSearchItem.et.setHint(text);
                break;
            case EXPANDABLE_TYPE:
                DrawerDropDown drawerDropDown = (DrawerDropDown)holder;
                drawerDropDown.tv.setText(text);
                iSubreddit.getSubredditRecyclerView(drawerDropDown.rvSub);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int val = -1;
        switch (position){
//            case 0: val = SEARCH_TYPE;
//            break;
            case 1: val = EXPANDABLE_TYPE;
                break;
            default: val = NORMAL_TYPE;
        }
        return val;
    }


}
