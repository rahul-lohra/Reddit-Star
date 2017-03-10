package com.android.rahul_lohra.redditstar.adapter.normal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.ReplyActivity;
import com.android.rahul_lohra.redditstar.contract.ILogin;
import com.android.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.Share;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.CommentsViewHolder;
import com.android.rahul_lohra.redditstar.viewHolder.CursorRecyclerViewAdapter;
import com.android.rahul_lohra.redditstar.viewHolder.PostViewDetail;

/**
 * Created by rkrde on 22-01-2017.
 */

public class CommentsAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private ILogin iLogin;
    private static final int POST_TYPE = 1;
    private static final int COMMENTS_TYPE = 2;
    private Activity mActivity;

    public CommentsAdapter(Activity activity,ILogin iLogin, Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
        this.iLogin = iLogin;
        this.mActivity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        int val = -1;
        switch (position){
            case 0: val = POST_TYPE;
                break;
            default: val = COMMENTS_TYPE;
        }
        return val;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        switch (viewType){
            case POST_TYPE:
            {
                v = new PostViewDetail(context,LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.list_item_posts_detail, parent, false),iLogin);
            }
            break;
            case COMMENTS_TYPE:
                v =  new CommentsViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.list_item_comments_new, parent, false));
                break;
        }
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {


        switch (holder.getItemViewType()){
            case POST_TYPE:
            {
                PostViewDetail viewHolder = (PostViewDetail)holder;
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
                final String thumbnail = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_THUMBNAIL));
                final String url = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_URL));
                final Integer likes = cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_LIKES));
                final String bigImageUrl = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));
                final String postHint =  cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_POST_HINT));
                final String domain =  cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_DOMAIN));
                final String score =  cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SCORE));

                viewHolder.setLikes(likes);
                viewHolder.setUrl(url);
                viewHolder.setScores(score);
                viewHolder.setId(id);
//                viewHolder.setUps(ups);
                viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Share share = new Share();
                        share.shareUrl(mActivity,url);
                    }
                });

                viewHolder.tvVote.setText(score);
                viewHolder.tvTitle.setText(title);
                viewHolder.tvComments.setText(commentsCount);
                viewHolder.tvDomain.setText(domain);
                viewHolder.tvVote.setTextColor(ContextCompat.getColor(context,R.color.white));
                viewHolder.tvComments.setTextColor(ContextCompat.getColor(context,R.color.white));
                viewHolder.tvShare.setTextColor(ContextCompat.getColor(context,R.color.white));
                viewHolder.tvCategory.setText("r/" + subreddit + "-" + time);
                viewHolder.tvUsername.setText(author);

            }break;
            case COMMENTS_TYPE:{
                final CommentsViewHolder viewHolder = (CommentsViewHolder)holder;
                int depth = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_DEPTH));
                //set Margin and Color
                switch (depth){
                    case 1: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
                        break;
                    case 2: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
                        break;
                    case 3: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.grey_500));
                        break;
                }

                int margin = depth *20;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.view_2.getLayoutParams();
                params.setMarginStart(margin);
//                params.addRule(RelativeLayout.ALIGN_BOTTOM,viewHolder.tvComment.getId());
                viewHolder.view_2.setLayoutParams(params);
                viewHolder.view_2.requestLayout();

                //set Textual Data
                final String comment = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_BODY));
                final String author = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_AUTHOR));
                final int upvote = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_UPS));
                final int thingId = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_LINK_ID));

                viewHolder.tvComment.setText(comment);
                viewHolder.tvUsername.setText(author);
                viewHolder.tvUpvoteCount.setText(String.valueOf(upvote));

                viewHolder.tvUsername.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int w  = viewHolder.view_2.getWidth();
                        int h = viewHolder.view_2.getHeight();
                        System.out.println();
                    }
                });

                viewHolder.tvReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Check if user is logged in or not
                        boolean mTwoPane = false;
                        boolean loggedIn = UserState.isUserLoggedIn(context);
                        if(loggedIn){
                            //TODO:check for two Pane
                            if(mTwoPane){
                                //show Fragment
                            }else {
                                //open Activity
                                Intent intent = new Intent(context, ReplyActivity.class);
                                intent.putExtra("thing_id",thingId);
                                context.startActivity(intent);
                            }
                        }else {
                            Toast.makeText(context,context.getString(R.string.please_login),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }break;
        }




    }


}
