package com.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.contract.ILogin;
import com.rahul_lohra.redditstar.modal.t1_Comments.Child;
import com.rahul_lohra.redditstar.modal.t3_Link.T3_Data;
import com.rahul_lohra.redditstar.viewHolder.CommentsViewHolder;
import com.rahul_lohra.redditstar.viewHolder.PostViewDetail;

import java.util.List;

/**
 * Created by rkrde on 17-04-2017.
 */

public class SuperAdapter extends RecyclerView.Adapter {
    Context context;
    List<Child> list;
    private ILogin iLogin;
    private static final int T3_POST_TYPE = 3;
    private static final int T1_COMMENTS_TYPE = 1;

    public SuperAdapter(Context context, List<Child> list, ILogin iLogin) {
        this.context = context;
        this.list = list;
        this.iLogin = iLogin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        switch (viewType){
            case T1_COMMENTS_TYPE:
            {
                v =  new CommentsViewHolder(context,LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.list_item_comments_new, parent, false));
            }
            break;
            case T3_POST_TYPE:
                v = new PostViewDetail(context, LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.list_item_posts_detail, parent, false),iLogin);
                break;
        }
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getAdapterPosition()){
            case T1_COMMENTS_TYPE:

                break;
            case T3_POST_TYPE:
                PostViewDetail viewHolder = (PostViewDetail)holder;
                T3_Data t3_data = list.get(holder.getAdapterPosition()).t3data;


//                viewHolder.initFromNetwork(t3hasBigImage,hasThumbnail,thumbnail);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).kind.equals("t1")?T1_COMMENTS_TYPE:T3_POST_TYPE;
    }
}
