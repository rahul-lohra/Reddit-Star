package com.android.rahul_lohra.redditstar.adapter.cursor;

import android.app.Activity;
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
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.SubredditActivity;
import com.android.rahul_lohra.redditstar.contract.IFrontPageAdapter;
import com.android.rahul_lohra.redditstar.fragments.SearchFragment;
import com.android.rahul_lohra.redditstar.helper.ItemTouchHelperAdapter;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChildData;
import com.android.rahul_lohra.redditstar.modal.frontPage.Preview;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.Share;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.CursorRecyclerViewAdapter;
import com.android.rahul_lohra.redditstar.viewHolder.DrawerSubreddit;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;
import com.varunest.sparkbutton.SparkEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.android.rahul_lohra.redditstar.utility.Constants.updateLikes;

/**
 * Created by rkrde on 29-01-2017.
 */

public class HomeAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> implements
        ItemTouchHelperAdapter {

    private Activity activity;
    private final String TAG = HomeAdapter.class.getSimpleName();
    private IFrontPageAdapter iFrontPageAdapter;


    public HomeAdapter(Activity activity, Cursor cursor, IFrontPageAdapter iFrontPageAdapter) {
        super(activity, cursor);
        this.activity = activity;
        this.iFrontPageAdapter = iFrontPageAdapter;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        final PostView postView = (PostView) holder;

//        final FrontPageChildData frontPageChildData = list.get(position).getData();
        final String sqlId = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SQL_ID));
        final String id = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_ID));
        final String subreddit = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT));
        final String subredditId = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT_ID));

        final String name = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_NAME));
        final String author = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_AUTHOR));
        final long createdUtc = cursor.getLong(cursor.getColumnIndex(MyPostsColumn.KEY_CREATED_UTC));
        final String time = Constants.getTimeDiff(createdUtc);
        final String ups = String.valueOf(cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_UPS)));
        final String title = String.valueOf(cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_TITLE)));
        final String commentsCount = String.valueOf(cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_COMMENTS_COUNT)));
//        final Preview preview = frontPageChildData.getPreview();
        final String thumbnail = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_THUMBNAIL));
        final String url = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_URL));
        final Integer likes = cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_LIKES));
        final String bigImageUrl = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));

        postView.setLikes(likes);

//        if(!(fragment instanceof SearchFragment)){

        if(cursor.isLast()){
            EventBus.getDefault().post("getNextData");
        }

//            if (position == list.size() - 1 && list.size()>4) {
//                EventBus.getDefault().post("getNextData");
//            }
//        }

        postView.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPostModal modal = new DetailPostModal(id,
                        subreddit,ups,title,commentsCount,thumbnail,time,author,bigImageUrl,likes,name);
                iFrontPageAdapter.sendData(modal,postView.imageView);
            }
        });

        postView.imageUpVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String thingId = name;
                boolean loggedIn = UserState.isUserLoggedIn(activity);

                if (!loggedIn) {
                    iFrontPageAdapter.pleaseLogin();
                    Toast.makeText(activity, activity.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer mLikes = postView.getLikes();
                if(mLikes!=null){
                    if(mLikes==0)
                    {
                        postView.performVote(PostView.DIRECTION_NULL,thingId);
//                        frontPageChildData.setLikes(null);
                        updateLikes(activity.getApplicationContext(),null,id);
                    }
                }else {
                    //upvote
                    postView.performVote(PostView.DIRECTION_UP,thingId);
//                    frontPageChildData.setLikes(true);
                    updateLikes(activity.getApplicationContext(),1,id);
                }

            }
        });

//        postView.imageView.setOnClickListener((View v)->{});

        postView.imageDownVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean loggedIn = UserState.isUserLoggedIn(activity);
                String thingId = name;
                if (!loggedIn) {
                    iFrontPageAdapter.pleaseLogin();
                    Toast.makeText(activity, activity.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer mLikes = postView.getLikes();
                if(mLikes!=null){
                    if(mLikes==1)
                    {
                        postView.performVote(PostView.DIRECTION_NULL,thingId);
                        updateLikes(activity.getApplicationContext(),null,id);
//                        frontPageChildData.setLikes(null);
                    }
                }else {
                    //upvote
                    postView.performVote(PostView.DIRECTION_DOWN,thingId);
                    updateLikes(activity.getApplicationContext(),0,id);
//                    frontPageChildData.setLikes(false);
                }
            }
        });

        postView.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share share = new Share();
                share.shareUrl(activity,url);
            }
        });

        //set Textual Data

        if (thumbnail != null) {
            Glide.with(activity)
                    .load(thumbnail)
                    .centerCrop()
                    .crossFade()
                    .into(postView.imageView);
        }else {
            postView.imageView.setVisibility(View.GONE);
        }

        postView.tvVote.setText(ups);
        postView.tvTitle.setText(subreddit);
        postView.tvDetail.setText(title);
        postView.tvComments.setText(commentsCount);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostView(activity,LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_posts, parent, false));
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
