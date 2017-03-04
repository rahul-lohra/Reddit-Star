package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.ReplyActivity;
import com.android.rahul_lohra.redditstar.modal.comments.Child;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.comments.Example;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.CommentsViewHolder;
import com.android.rahul_lohra.redditstar.viewHolder.CursorRecyclerViewAdapter;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;


import java.util.List;

import butterknife.Bind;

/**
 * Created by rkrde on 22-01-2017.
 */

public class CommentsAdapter extends CursorRecyclerViewAdapter<RecyclerView.ViewHolder> {


    private Context context;

    public CommentsAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }


//    public CommentsAdapter(Context context, Cursor cursor) {
//        this.context = context;
//        this.list = list;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        v = new CommentsViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_comments, parent, false));
        return v;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        CommentsViewHolder viewHolder = (CommentsViewHolder)holder;
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
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)viewHolder.view_2.getLayoutParams();
        params.setMarginStart(margin);
        viewHolder.view_2.setLayoutParams(params);
        viewHolder.view_2.requestLayout();

        //set Textual Data

//        final Child child = list.get(position).getChild();
        final String comment = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_BODY));
        final String author = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_AUTHOR));
        final int upvote = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_UPS));
        final int thingId = cursor.getInt(cursor.getColumnIndex(CommentsColumn.KEY_LINK_ID));

        viewHolder.tvComment.setText(comment);
        viewHolder.tvUsername.setText(author);
        viewHolder.tvUpvoteCount.setText(String.valueOf(upvote));


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

    }


}
