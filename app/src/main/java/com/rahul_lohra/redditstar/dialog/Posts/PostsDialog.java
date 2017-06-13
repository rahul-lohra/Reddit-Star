package com.rahul_lohra.redditstar.dialog.Posts;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.ActivityPortal;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rkrde on 15-04-2017.
 */

public class PostsDialog extends DialogFragment{
    RecyclerView rv;
    DetailPostModal detailPostModal;
    List<PostDialogData> list = new ArrayList<>();
    PostDialogAdapter adapter;

    private final int PROFILE = 0;
    private final int SUBREDDIT = 1;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.dialog_posts,null, true);
        builder.setView(view);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        Bundle bundle = getArguments();
        detailPostModal = bundle.getParcelable("detail");
        list.add(new PostDialogData("About "+detailPostModal.getAuthor(),R.drawable.ic_history_black));
        list.add(new PostDialogData("Go to r/"+detailPostModal.getSubreddit(),R.drawable.ic_history_black));

        setAdapter();
        return builder.create();
    }


    
    private void setAdapter(){
        adapter = new PostDialogAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
    }

    private class PostDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PostDialogVIew(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_normal, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PostDialogVIew view = (PostDialogVIew)holder;
            view.tv.setText(list.get(position).name);
            view.tv.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(getContext(),list.get(position).drawableId),null,null,null);

            switch (holder.getAdapterPosition()){
                case PROFILE:
                    view.tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                    break;
                case SUBREDDIT:
                    view.tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityPortal.openSubredditActivity(detailPostModal.getSubreddit(),detailPostModal.getSubredditId(),getActivity());
                            PostsDialog.this.dismiss();
                        }
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

   public class PostDialogVIew extends RecyclerView.ViewHolder {

        @Bind(R.id.tv)
        public TextView tv;

        public PostDialogVIew(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
