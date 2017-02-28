package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.fragments.DetailSubredditFragment;
import com.android.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<CustomComment>> {

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

    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;


    private final int LOADER_ID = 1;
    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailPostModal subredditModal;
    private final String TAG = DetailActivity.class.getSimpleName();

    @OnClick(R.id.image_up_vote)
    void onClickUpVote() {
        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);

        if (!isUserLoggedIn) {
            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        } else {

            Integer mLikes = subredditModal.getLikes();
            if(mLikes!=null){
                if(mLikes==1)
                {
                    performVote(PostView.DIRECTION_NULL);
//                    frontPageChildData.setLikes(null);
                }
            }else {
                performVote(PostView.DIRECTION_UP);
//                frontPageChildData.setLikes(false);
            }

        }
    }

    @OnClick(R.id.image_down_vote)
    void onClickDownVote() {
        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);

        if (!isUserLoggedIn) {
            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        } else {

            Integer mLikes = subredditModal.getLikes();
            if(mLikes!=null){
                if(mLikes==1)
                {
                    performVote(PostView.DIRECTION_NULL);
//                    frontPageChildData.setLikes(null);
                }
            }else {
                performVote(PostView.DIRECTION_DOWN);
//                frontPageChildData.setLikes(false);
            }
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_subreddit);
        ButterKnife.bind(this);
        ((Initializer)getApplicationContext()).getNetComponent().inject(this);
        Intent intent = getIntent();
        subredditModal = (DetailPostModal) intent.getParcelableExtra("modal");
        apiInterface = retrofit.create(ApiInterface.class);

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
        tvCategory.setText("r/" + subredditModal.getSubreddit() + "-" + subredditModal.getTime());
        tvUsername.setText(subredditModal.getAuthor());
        tvSort.setText("new");
        String bigImageUrl = subredditModal.getBigImageUrl();
        Glide.with(this).
                load(bigImageUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);

        Integer likes = subredditModal.getLikes();

        if (likes != null) {
            highlightVote(likes);
        }
    }

    private void highlightVote(Integer likes) {
        Integer resId = (likes==1) ? R.drawable.ic_arrow_upward_true : R.drawable.ic_arrow_downward_true;
        Glide.with(this)
                .load(resId)
                .into((likes==1) ? imageUpVote : imageDownVote);
    }

    private void setAdapter() {
        commentsAdapter = new CommentsAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(commentsAdapter);
    }

    private void performVote(@PostView.DirectionMode int mode) {
        String token = UserState.getAuthToken(this);
        String auth = "bearer " + token;
        Map<String, String> map = new HashMap<String, String>();
        map.put("dir", String.valueOf(mode));
        map.put("id", subredditModal.getName());
        map.put("rank", String.valueOf(2));
        apiInterface.postVote(auth, map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "UpVote onResponse:" + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "UpVote onFail:" + t.getMessage());
            }
        });
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
        switch (loader.getId()) {
            case LOADER_ID:
                list.clear();
                list.addAll(data);
                this.commentsAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<CustomComment>> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
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
