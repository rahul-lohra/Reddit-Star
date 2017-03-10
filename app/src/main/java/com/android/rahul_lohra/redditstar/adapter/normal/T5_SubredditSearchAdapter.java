package com.android.rahul_lohra.redditstar.adapter.normal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.modal.T5_Kind;
import com.android.rahul_lohra.redditstar.modal.t5_Subreddit.Elephant;
import com.android.rahul_lohra.redditstar.viewHolder.SubredditsSmallCard;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by rkrde on 22-01-2017.
 */

public class T5_SubredditSearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<T5_Kind> list;

    public interface IT5_SubredditSearchAdapter{
         void sendData(String name,String fullName,String subredditId);
        void getNextSubreddit();
    }
    private IT5_SubredditSearchAdapter mListener;


    public T5_SubredditSearchAdapter(Context context, List<T5_Kind> list,IT5_SubredditSearchAdapter mListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
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
        final Elephant t5_data = list.get(position).data;
//        final String name = t5_data.getName();
//        final String subreddit = t5_data.getSubreddit();
//        final String thingId = t5_data.getName();
//        final String author = t5_data.getAuthor();
//        final long createdUtc = t5_data.getCreatedUtc();
//
//        final Preview preview = t5_data.getPreview();
//        final String thumbnail = (preview!=null)?t5_data.getThumbnail():"";

        if (position == list.size() - 1 && list.size()>4) {
            mListener.getNextSubreddit();
        }

        viewHolder.tvShare.setText(list.get(position).data.getDisplayName());
        Glide.with(context)
                .load(list.get(position).data.getIconImg())
                .centerCrop()
                .crossFade()
                .into(viewHolder.imageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null)
                {
                    mListener.sendData(t5_data.getDisplayName(),t5_data.getName(),t5_data.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
