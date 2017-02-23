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
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.transfer.DetailSubredditModal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailSubredditFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CustomComment>> {
    private static final String ARG_ID = "arg_id";
    private static final String ARG_SUBREDDIT = "arg_subreddit";
    private static final String ARG_UPS = "arg_ups";
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_COMMENTS_COUNT = "arg_comments_count";
    private static final String ARG_THUMBNAIL = "arg_thumnail";
    private static final String ARG_TIME = "arg_time";
    private static final String ARG_AUTHOR = "arg_author";


    @Bind(R.id.imageView4)
    ImageView imageView4;
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

//    private String id;
//    private String subreddit;
//    private String ups;
//    private String title;
//    private String commentsCount;
//    private String thumbnail;
//    private String time;
//    private String author;


    private final int LOADER_ID = 1;
    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailSubredditModal subredditModal;


    public DetailSubredditFragment() {
        // Required empty public constructor
    }

    public static DetailSubredditFragment newInstance(DetailSubredditModal subredditModal) {
        DetailSubredditFragment fragment = new DetailSubredditFragment();
        Bundle args = new Bundle();
        args.putParcelable("modal",subredditModal);
//        args.putString(ARG_ID, id);
//        args.putString(ARG_SUBREDDIT, subreddit);
//        args.putString(ARG_UPS, ups);
//        args.putString(ARG_TITLE, title);
//        args.putString(ARG_COMMENTS_COUNT, commentsCount);
//        args.putString(ARG_THUMBNAIL, thumbnail);
//        args.putString(ARG_TIME, time);
//        args.putString(ARG_AUTHOR, author);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subredditModal = (DetailSubredditModal) getArguments().getParcelable("modal");
//            id = getArguments().getString(ARG_ID);
//            subreddit = getArguments().getString(ARG_SUBREDDIT);
//            ups = getArguments().getString(ARG_UPS);
//            title = getArguments().getString(ARG_TITLE);
//            commentsCount = getArguments().getString(ARG_COMMENTS_COUNT);
//            thumbnail = getArguments().getString(ARG_THUMBNAIL);
//            time = getArguments().getString(ARG_TIME);
//            author = getArguments().getString(ARG_AUTHOR);

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
        tvTitle.setText(subredditModal.getTitle()+"-"+subredditModal.getTime());
        tvComments.setText(subredditModal.getCommentsCount());
        tvVote.setText(subredditModal.getUps());
        tvCategory.setText("r/"+subredditModal.getSubreddit());
        tvUsername.setText(subredditModal.getAuthor());
        tvSort.setText("new");
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
