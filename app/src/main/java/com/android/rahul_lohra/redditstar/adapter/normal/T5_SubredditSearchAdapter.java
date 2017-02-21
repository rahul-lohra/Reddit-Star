package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.modal.T5_Kind;
import com.android.rahul_lohra.redditstar.modal.t5_Subreddit.T5_Data;
import com.android.rahul_lohra.redditstar.viewHolder.SubredditsSmallCard;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;

/**
 * Created by rkrde on 22-01-2017.
 */

public class T5_SubredditSearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<T5_Kind> list;


    public T5_SubredditSearchAdapter(Context context, List<T5_Kind> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        v = new SubredditsSmallCard(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_cards_subreddits, parent, false));
        return v;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SubredditsSmallCard viewHolder = (SubredditsSmallCard) holder;
        viewHolder.tvShare.setText(list.get(position).data.getDisplayName());
        Glide.with(context)
                .load(list.get(position).data.getIconImg())
                .centerCrop()
                .crossFade()
                .into(viewHolder.imageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
