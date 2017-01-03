package com.android.rahul_lohra.redditstar.Activity.Dummy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;

import java.util.List;

/**
 * Created by rkrde on 03-01-2017.
 */

public class AdapterRedditComments extends RecyclerView.Adapter<AdapterRedditComments.ViewHolder>  {


        String TAG = "Adap_Red_Comments";
        Context context;
        List<String> stringList;

        public AdapterRedditComments(Context context, List<String> stringList){
            this.context = context;
            this.stringList = stringList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comments, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

//            @Bind(R.id.image_view)
//            AppCompatImageView imageView;
            public ViewHolder(View itemView) {
                super(itemView);
//                ButterKnife.bind(this,itemView);
            }
        }

    
}
