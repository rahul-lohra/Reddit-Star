package com.android.rahul_lohra.redditstar.dummy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DummyDetailActivity extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView rv;
    AdapterRedditComments adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setFakeData();
        setFakeAdapter();

    }
    void setFakeData(){
        tvVote.setText(getString(R.string.upvote_counts));
        tvComments.setText(getString(R.string.comments_counts));
        tvUsername.setText(getString(R.string.sample_username));
        tvCategory.setText(getString(R.string.sample_category));
        tvSort.setText(getString(R.string.sort_order));
        tvTitle.setText(getString(R.string.comments_title));
    }
    void setFakeAdapter() {
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.add("e");
        stringList.add("f");

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRedditComments(this, stringList);
        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);

    }
}
