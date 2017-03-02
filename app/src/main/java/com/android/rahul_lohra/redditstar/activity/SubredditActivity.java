package com.android.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.fragments.subreddit.SubredditFragment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.storage.MyProvider;

public class SubredditActivity extends BaseActivity implements SubredditFragment.ISubredditFragment {

    private Uri uri = MyProvider.TempLists.CONTENT_URI;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreddit);

        if (null == savedInstanceState) {
            String name = getIntent().getStringExtra("name");
            String fullName = getIntent().getStringExtra("fullName");
            String subredditId = getIntent().getStringExtra("subredditId");


            showSubredditFragment(name, fullName, subredditId);
        }
    }

    void showSubredditFragment(@NonNull String name, @NonNull String fullName, @NonNull String subredditId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SubredditFragment.newInstance(name, fullName, subredditId), SubredditFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void sendModalAndImageView(DetailPostModal modal, ImageView imageView, String id) {
//        Toast.makeText(this,"Item clicked",Toast.LENGTH_SHORT).show();
//        if (mTwoPane) {
//            showDetailSubredditFragment(modal);
//        } else {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("modal", modal);
        intent.putExtra("id", id);
        intent.putExtra("uri", uri);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent, bundle);

//        }
    }
}
