package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.fragments.HomeFragment;
import com.android.rahul_lohra.redditstar.modal.comments.DummyAdapter;
import com.android.rahul_lohra.redditstar.modal.comments.Example;
import com.android.rahul_lohra.redditstar.modal.transfer.DetailSubredditModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        if (null == savedInstanceState) {

            showDetailSubredditFragment((DetailSubredditModal)intent.getParcelableExtra("modal"));

        }
    }

    void showDetailSubredditFragment(DetailSubredditModal modal) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, DetailSubredditFragment.newInstance(modal), DetailSubredditFragment.class.getSimpleName())
                .commit();
    }

}
