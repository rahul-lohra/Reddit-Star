package com.rahul_lohra.redditstar.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.fragments.FavouriteFragment;

public class FavoriteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if(null==savedInstanceState){
            showReplyFragment();
        }
    }
    private void showReplyFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, FavouriteFragment.newInstance(), FavouriteFragment.class.getSimpleName())
                .commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_fav, menu);
//        return true;
//    }
}
