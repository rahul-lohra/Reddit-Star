package com.android.rahul_lohra.redditstar.adapter.cursor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.SubredditActivity;
import com.android.rahul_lohra.redditstar.contract.IActivity;
import com.android.rahul_lohra.redditstar.modal.FavoritesModal;
import com.android.rahul_lohra.redditstar.storage.MyDatabase;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.viewHolder.CursorRecyclerViewAdapter;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerSubreddit;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

/**
 * Created by rkrde on 29-01-2017.
 */

public class SubredditDrawerAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private final String TAG = SubredditDrawerAdapter.class.getSimpleName();
    private IActivity iActivity;
    public SubredditDrawerAdapter(Context context, Cursor cursor,IActivity iActivity) {
        super(context, cursor);
        this.context = context;
        this.iActivity = iActivity;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,Cursor cursor) {
        final String displayName = cursor.getString(cursor.getColumnIndex(MySubredditColumn.KEY_DISPLAY_NAME));
        final String subredditId = cursor.getString(cursor.getColumnIndex(MySubredditColumn.KEY_ID));
        final String fullName = cursor.getString(cursor.getColumnIndex(MySubredditColumn.KEY_NAME));
        final String nameFromFav = cursor.getString(cursor.getColumnIndex(MyFavouritesColumn.KEY_DISPLAY_NAME));

        DrawerSubreddit drawerSubreddit = (DrawerSubreddit)viewHolder;

        if(nameFromFav!=null){
            drawerSubreddit.sparkButton.setChecked(true);
        }
        drawerSubreddit.tv.setText(displayName);
        drawerSubreddit.sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {}

            @Override
            public void onEventAnimationEnd(boolean buttonState) {
                if(buttonState)
                {
                    //insert
                    Constants.insertIntoFavoritesDb(context,new FavoritesModal(displayName,fullName,subredditId));
                }else {
                    //remove
                    Constants.deleteFromFavoritesDb(context,fullName);
                }
            }
            @Override
            public void onEventAnimationStart(boolean buttonState) {}
        });

        drawerSubreddit.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubredditActivity.class);
                intent.putExtra("name",displayName);
                intent.putExtra("fullName",fullName);
                intent.putExtra("subredditId",subredditId);
                iActivity.openActivity(intent);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerSubreddit(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_subreddit, parent, false));
    }
}
