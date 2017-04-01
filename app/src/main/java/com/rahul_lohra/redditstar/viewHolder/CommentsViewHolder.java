package com.rahul_lohra.redditstar.viewHolder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.Constants;
import com.rahul_lohra.redditstar.dataModel.Comments;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rkrde on 22-01-2017.
 */
public class CommentsViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.view_2)
    public View view_2;
    @Bind(R.id.tv_username)
    public TextView tvUsername;
    @Bind(R.id.tv_comment)
    public TextView tvComment;
    @Bind(R.id.tv_time)
    public TextView tvTime;
    @Bind(R.id.tv_upvote_count)
    public TextView tvUpvoteCount;
    @Bind(R.id.view2)
    public View view2;
    @Bind(R.id.tv_reply)
    public TextView tvReply;
    @Bind(R.id.parent_comments)
    public RelativeLayout parentLayout;
    @Bind(R.id.tv_hidden_child_counts)
    public TextView tvHiddenChildCounts;

    private Context mContext;
    private int hiddenChildCommentsCounts = 0;
    private int isExpanded = 0,depth;
    private String hiddenBy;
    private String cId,sqlId;
    Uri mUri = MyProvider.CommentsLists.CONTENT_URI;
    String mProjection[] ={CommentsColumn.KEY_SQL_ID};

    @OnClick(R.id.parent_comments)
   void onComments(){
       performOperationsOnChildComments();
   }


    public CommentsViewHolder(Context context,View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
    }

    public void init(Comments comments){
        this.sqlId = comments.getSqlId();
        this.hiddenChildCommentsCounts = comments.getHiddenChildCounts();
        this.isExpanded = comments.getIsExpanded();
        this.hiddenBy = comments.getHiddenBy();
        this.depth = comments.getDepth();
        this.cId = comments.getcId();
        tvComment.setText(comments.getBody());
        tvUsername.setText(comments.getAuthor());
        tvUpvoteCount.setText(String.valueOf(comments.getUpVote()));
        tvTime.setText(comments.getTime().substring(0,3));
        if(hiddenChildCommentsCounts ==0)
            tvHiddenChildCounts.setVisibility(View.GONE);
        else
            tvHiddenChildCounts.setVisibility(View.VISIBLE);
        tvHiddenChildCounts.setText("+"+String.valueOf(hiddenChildCommentsCounts));

    }

    private synchronized void hideChildComments(){
        Constants.showToast(mContext,"Hidding comments");
        //Find end Point : till where you should update
        String mSelection_1 = CommentsColumn.KEY_SQL_ID+" >? AND "+CommentsColumn.KEY_DEPTH+" <= ?";
        String mSelectionArrgs_1[]={sqlId,String.valueOf(depth)};
        String sortOrder = CommentsColumn.KEY_SQL_ID+" ASC LIMIT 1";
        Cursor cursor = mContext.getContentResolver().query(mUri,mProjection,mSelection_1,mSelectionArrgs_1,sortOrder);
        String endSqlId = "";
        if(cursor!=null){

            if(cursor.moveToFirst()){
                endSqlId = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_SQL_ID));
                ContentValues cv = new ContentValues();
                cv.put(CommentsColumn.KEY_IS_EXPANDED,0);
                cv.put(CommentsColumn.KEY_HIDDEN_BY,cId);
                String mWhere = CommentsColumn.KEY_DEPTH +">? AND "+CommentsColumn.KEY_SQL_ID+" >? AND "+CommentsColumn.KEY_SQL_ID+" <? AND "+CommentsColumn.KEY_IS_EXPANDED+" =?";
                String mSelectionArgs[] = {String.valueOf(depth),sqlId,endSqlId,String.valueOf(1)};
                int rowsUpdated = mContext.getContentResolver().update(mUri,cv,mWhere,mSelectionArgs);

                ContentValues cv_2 = new ContentValues();
                cv_2.put(CommentsColumn.KEY_HIDDEN_CHILD_COUNTS,rowsUpdated);
                String mWhere_2 = CommentsColumn.KEY_SQL_ID +" =?";
                String mSelectionArgs_2[] = {sqlId};
                int rowsUpdated_2 = mContext.getContentResolver().update(mUri,cv_2,mWhere_2,mSelectionArgs_2);
                mContext.getContentResolver().notifyChange(MyProvider.PostsComments.CONTENT_URI, null);
            }
                cursor.close();
        }

    }

    private void showChildComments(){
        Constants.showToast(mContext,"Showing comments");
        ContentValues cv_1 = new ContentValues();
        cv_1.put(CommentsColumn.KEY_IS_EXPANDED,1);
        cv_1.put(CommentsColumn.KEY_HIDDEN_BY,"");
        String mWhere = CommentsColumn.KEY_HIDDEN_BY+ "=?";
        String mSelectionArgs[] = {cId};
        mContext.getContentResolver().update(mUri,cv_1,mWhere,mSelectionArgs);

        ContentValues cv_2 = new ContentValues();
        cv_2.put(CommentsColumn.KEY_HIDDEN_CHILD_COUNTS,0);
        String mWhere_2 = CommentsColumn.KEY_SQL_ID +" =?";
        String mSelectionArgs_2[] = {sqlId};
        int rowsUpdated_2 = mContext.getContentResolver().update(mUri,cv_2,mWhere_2,mSelectionArgs_2);
        mContext.getContentResolver().notifyChange(MyProvider.PostsComments.CONTENT_URI, null);

    }

    private void performOperationsOnChildComments(){
        if(hiddenChildCommentsCounts>=1){
            showChildComments();
        }else {
            hideChildComments();
        }
    }


}
