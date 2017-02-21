package com.android.rahul_lohra.redditstar.adapter.cursor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.SubredditActivity;
import com.android.rahul_lohra.redditstar.helper.ItemTouchHelperAdapter;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.viewHolder.CursorRecyclerViewAdapter;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerSubreddit;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rkrde on 29-01-2017.
 */

public class FavoritesAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> implements
        ItemTouchHelperAdapter {

    private Context context;
    private final String TAG = FavoritesAdapter.class.getSimpleName();

    public FavoritesAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final Cursor cursor) {
        final String displayName = cursor.getString(cursor.getColumnIndex(MyFavouritesColumn.KEY_SUBREDDIT_NAME));
        final String subredditId = cursor.getString(cursor.getColumnIndex(MyFavouritesColumn.KEY_SUBREDDIT_ID));

        DrawerSubreddit drawerSubreddit = (DrawerSubreddit)viewHolder;
        drawerSubreddit.tv.setText(displayName);
        drawerSubreddit.sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                Log.d(TAG,"buttonState:"+buttonState);
                Uri mUri = MyProvider.FavoritesLists.CONTENT_URI;
                if(buttonState)
                {
                    //insert
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(MyFavouritesColumn.KEY_SUBREDDIT_ID,subredditId);
//                    contentValues.put(MyFavouritesColumn.KEY_SUBREDDIT_NAME,displayName);
//                    context.getContentResolver().insert(mUri,contentValues);
                }else {
                    //remove
//                    String mWhere = MyFavouritesColumn.KEY_SUBREDDIT_ID +"=?";
//                    String mSelectionArgs []={subredditId};
//                    int rowsDeleted = context.getContentResolver().delete(mUri,mWhere,mSelectionArgs);
//                    Log.d(TAG,"rowsDeleted:"+rowsDeleted);
                }
            }
        });

        drawerSubreddit.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubredditActivity.class);
                intent.putExtra("name",displayName);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerSubreddit(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        Log.d(TAG,"from:"+fromPosition+",to:"+toPosition);
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
//                context.getContentResolver().
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
//                Collections.swap(mItems, i, i - 1);
            }
        }
    }

    @Override
    public void onItemDismiss(int position) {
        //NO USE unless swipe is already disabled
    }
}
