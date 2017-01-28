package com.android.rahul_lohra.redditstar.adapter.cursor;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.viewHolder.CursorRecyclerViewAdapter;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerNormal;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerSubreddit;

/**
 * Created by rkrde on 29-01-2017.
 */

public class SubredditDrawerAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

    Context context;
    Cursor cursor;
    public SubredditDrawerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
        this.cursor  = cursor;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        String displayName = cursor.getString(cursor.getColumnIndex(MySubredditColumn.KEY_DISPLAY_NAME));
        DrawerSubreddit drawerSubreddit = (DrawerSubreddit)viewHolder;
        drawerSubreddit.tv.setText(displayName);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerSubreddit(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_subreddit, parent, false));
    }
}
