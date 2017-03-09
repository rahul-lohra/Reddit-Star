package com.android.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.contract.ILogin;
import com.android.rahul_lohra.redditstar.fragments.SearchFragment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.storage.MyProvider;

public class SearchActivity extends BaseActivity implements
        SearchFragment.ISearchFragment,
        ILogin

{
    private Uri uri = MyProvider.PostsLists.CONTENT_URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState==null){
            showSearchFragment();
        }
    }

    private void showSearchFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SearchFragment.newInstance(), SearchFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void openDetailScreen(DetailPostModal modal, ImageView imageView,String id) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("uri",uri);
        intent.putExtra("modal",modal);

        startActivity(intent, bundle);
    }

    @Override
    public void pleaseLogin() {

    }
}
