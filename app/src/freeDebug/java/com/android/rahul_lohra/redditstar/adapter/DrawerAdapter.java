package com.android.rahul_lohra.redditstar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerDropDown;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerNormal;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerSearchItem;

import java.util.List;

/**
 * Created by rkrde on 22-01-2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter {
    private Context context;
    private static final int SEARCH_TYPE = 1;
    private static final int NORMAL_TYPE = 2;
    private static final int EXPANDABLE_TYPE = 3;

    private List<String> list;

    public DrawerAdapter(Context context,List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        switch(viewType){
            case SEARCH_TYPE:
                 v = new DrawerSearchItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_edit_text, parent, false));
                break;
            case NORMAL_TYPE:
                 v = new DrawerNormal(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_normal, parent, false));
                break;
            case EXPANDABLE_TYPE:
                 v = new DrawerDropDown(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_drop_down, parent, false));
                break;
        }
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int val = NORMAL_TYPE;
        switch (position){
            case 0: val = SEARCH_TYPE;
            break;
            case 2: val = EXPANDABLE_TYPE;
        }
        return val;
    }
}
