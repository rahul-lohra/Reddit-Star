package com.android.rahul_lohra.redditstar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailSubredditFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CustomComment>> {



    @Bind(R.id.imageView)
    AspectRatioImageView imageView;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.tv_comments)
    TextView tvComments;
    @Bind(R.id.tv_vote)
    TextView tvVote;
    @Bind(R.id.image_up_vote)
    ImageView imageUpVote;
    @Bind(R.id.image_down_vote)
    ImageView imageDownVote;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.rv)
    RecyclerView rv;



    private final int LOADER_ID = 1;
    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailPostModal subredditModal;


    public DetailSubredditFragment() {
        // Required empty public constructor
    }

    public static DetailSubredditFragment newInstance(DetailPostModal subredditModal) {
        DetailSubredditFragment fragment = new DetailSubredditFragment();
        Bundle args = new Bundle();
        args.putParcelable("modal",subredditModal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subredditModal = (DetailPostModal) getArguments().getParcelable("modal");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_subreddit, container, false);
        ButterKnife.bind(this, v);

        if(subredditModal!=null){
            Bundle bundle = new Bundle();
            bundle.putString("id",subredditModal.getId());
            bundle.putString("subbreddit_name",subredditModal.getSubreddit());

            getLoaderManager().initLoader(LOADER_ID,bundle,this).forceLoad();
            setAdapter();
            initData();
        }

        return v;
    }

    private void initData(){
        tvTitle.setText(subredditModal.getTitle());
        tvComments.setText(subredditModal.getCommentsCount());
        tvVote.setText(subredditModal.getUps());
        tvCategory.setText("r/"+subredditModal.getSubreddit()+"-"+subredditModal.getTime());
        tvUsername.setText(subredditModal.getAuthor());
        tvSort.setText("new");
        String bigImageUrl = (subredditModal.getBigImageUrlList().size()!=0)?subredditModal.getBigImageUrlList().get(0):"";
        Glide.with(this).
                load(bigImageUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    private void setAdapter(){
        commentsAdapter = new CommentsAdapter(getContext(),list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(commentsAdapter);
    }

    @Override
    public Loader<List<CustomComment>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CommentsLoader(
                        getContext(),
                        args.getString("subbreddit_name"),
                        args.getString("id")
                );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<CustomComment>> loader, List<CustomComment> data) {
            switch (loader.getId())
            {
                case LOADER_ID:
                    list.clear();
                    list.addAll(data);
                    this.commentsAdapter.notifyDataSetChanged();
                    break;
            }
    }

    @Override
    public void onLoaderReset(Loader<List<CustomComment>> loader) {
        switch (loader.getId()){
            case LOADER_ID :
                list.clear();
                commentsAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
