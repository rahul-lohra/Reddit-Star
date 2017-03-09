package com.android.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.contract.ILogin;
import com.android.rahul_lohra.redditstar.fragments.subreddit.SubredditFragment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;

public class SubredditActivity extends BaseActivity implements
        SubredditFragment.ISubredditFragment,ILogin {

    private Uri uri = MyProvider.PostsLists.CONTENT_URI;
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
        String mProjection[]={MyPostsColumn.KEY_POST_HINT};
        String mSelectionArgs[]={id,"image"};
        String mSelection = MyPostsColumn.KEY_ID+"= ? AND "+MyPostsColumn.KEY_POST_HINT+" =?";

        Cursor cursor = getContentResolver().query(MyProvider.PostsLists.CONTENT_URI,mProjection,mSelection,mSelectionArgs,null);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("modal", modal);
        intent.putExtra("id",id);
        intent.putExtra("uri", MyProvider.PostsLists.CONTENT_URI);

        if(cursor!=null)
        {
            if(cursor.moveToFirst()){
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
                cursor.close();
                startActivity(intent,bundle);
            }else {
                cursor.close();
                startActivity(intent);
            }
        }

    }

    @Override
    public void pleaseLogin() {

    }
}
