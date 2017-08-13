package com.rahul_lohra.redditstar.adapter.normal;

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

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.activity.ReplyActivity;
import com.rahul_lohra.redditstar.contract.ILogin;
import com.rahul_lohra.redditstar.dataModel.Comments;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.Utility.Share;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.rahul_lohra.redditstar.viewHolder.CommentsViewHolder;
import com.rahul_lohra.redditstar.adapter.CursorRecyclerViewAdapter;
import com.rahul_lohra.redditstar.viewHolder.PostViewDetail;

/**
 * Created by rkrde on 22-01-2017.
 */

public class CommentsAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private Context context;
    private ILogin iLogin;
    private static final int POST_TYPE = 1;
    private static final int COMMENTS_TYPE = 2;
    private Activity mActivity;
    private ICommentsAdapter iCommentsAdapter;
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
                v =  new CommentsViewHolder(context,LayoutInflater.from(parent.getContext()).
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
                final int hasBigImage =  cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_IS_BIG_IMAGE_URL_HAS_IMAGE));
                final int hasThumbnail =  cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_IS_THUMBNAIL_HAS_IMAGE));


                viewHolder.initFromDb(hasBigImage,hasThumbnail,thumbnail,likes,score,url,id,title,commentsCount,domain,subreddit,time,author);
//                viewHolder.setUps(ups);
                viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Share share = new Share();
                        share.shareUrl(mActivity,url);
                    }
                });

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
                    case 4: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.green_500));
                        break;
                    case 5: viewHolder.view_2.setBackgroundColor(ContextCompat.getColor(context,R.color.light_blue_500));
                        break;
                }

                int margin = depth *20;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.view_2.getLayoutParams();
                params.setMarginStart(margin);
//                params.addRule(RelativeLayout.ALIGN_BOTTOM,viewHolder.tvComment.getId());
                viewHolder.view_2.setLayoutParams(params);
                viewHolder.view_2.requestLayout();

                //set Textual ImgurData
                final String sqlId = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_SQL_ID));
                final String body = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_BODY));
                final String author = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_AUTHOR));
                final int upvote = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_UPS));
                final int thingId = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_LINK_ID));
                final long createdUtc = cursor.getLong(cursor.getColumnIndex(CommentsColumn.KEY_CREATED_UTC));
                final String time = Constants.getTimeDiff(createdUtc);
                final int isExpanded =  cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_IS_EXPANDED));
                final int hiddenChildCounts =  cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_HIDDEN_CHILD_COUNTS));
                final String hiddenBy =  cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_HIDDEN_BY));
                final String cId =  cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_ID));

                Comments comments = new Comments(sqlId,cId,body,author,upvote,thingId,createdUtc,time,isExpanded,hiddenChildCounts,hiddenBy,depth);
                viewHolder.init(comments);


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

    public interface ICommentsAdapter{}



}
