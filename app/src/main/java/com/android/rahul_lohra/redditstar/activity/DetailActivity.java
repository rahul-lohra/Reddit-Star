package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<CustomComment>>
{

    @Bind(R.id.imageView)
    ImageView imageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_subreddit);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        subredditModal = (DetailPostModal) intent.getParcelableExtra("modal");


        if (subredditModal != null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", subredditModal.getId());
            bundle.putString("subbreddit_name", subredditModal.getSubreddit());

            getSupportLoaderManager().initLoader(LOADER_ID, bundle, this).forceLoad();
            setAdapter();
            initData();
        }

    }

    private void initData() {
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

    private void setAdapter() {
        commentsAdapter = new CommentsAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(commentsAdapter);
    }


    @Override
    public Loader<List<CustomComment>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CommentsLoader(
                        this,
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

    void showDetailSubredditFragment(DetailPostModal modal) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, DetailSubredditFragment.newInstance(modal), DetailSubredditFragment.class.getSimpleName())
                .commit();
    }

}
