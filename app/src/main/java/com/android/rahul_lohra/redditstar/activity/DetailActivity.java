package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.CommonOperations;
import com.android.rahul_lohra.redditstar.utility.Constants;
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

import static com.android.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_DOWN;
import static com.android.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_NULL;
import static com.android.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_UP;

/*
 Needs two item in intent
 1. sqlId or id
 2. uri of table
 */
public class DetailActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

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
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;
    Snackbar snackbar;

    private final int LOADER_ID = 1;
    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailPostModal subredditModal;
    private final String TAG = DetailActivity.class.getSimpleName();
    private Uri mUriReadTable;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_subreddit);
        ButterKnife.bind(this);
        ((Initializer)getApplicationContext()).getNetComponent().inject(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mUriReadTable = intent.getParcelableExtra("uri");
        subredditModal = (DetailPostModal) intent.getParcelableExtra("modal");

        apiInterface = retrofit.create(ApiInterface.class);

        String mSeletion = MyPostsColumn.KEY_ID +"=?";
        String mSelectionArgs[] = {id};

        Cursor cursor = getContentResolver().query(mUriReadTable,null,mSeletion,mSelectionArgs,null);
        setDataInView(cursor);
//            getSupportLoaderManager().initLoader(LOADER_ID,null,this);

//        if (subredditModal != null) {
//            Bundle bundle = new Bundle();
//            bundle.putString("id", subredditModal.getId());
//            bundle.putString("subbreddit_name", subredditModal.getSubreddit());

//            getSupportLoaderManager().initLoader(LOADER_ID, bundle, this).forceLoad();;
            setAdapter();
//            initData();
//        }


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

        highlightVote(likes);

        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(DetailActivity.this);
//                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                        snackbar1.show();
                    }
                });

    }

    private void highlightVote(Integer likes) {
        if(likes!=0){
            Integer resId = (likes==1) ? R.drawable.ic_arrow_upward_true : R.drawable.ic_arrow_downward_true;
            Glide.with(this)
                    .load("")
                    .placeholder(resId)
                    .into((likes==1) ? imageUpVote : imageDownVote);

            tvVote.setTextColor((likes==1)?ContextCompat.getColor(this,R.color.light_blue_500):ContextCompat.getColor(this,R.color.green_500));
        }

    }

    private void setAdapter() {
        commentsAdapter = new CommentsAdapter(this, list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(commentsAdapter);
    }

    private void performVote(@PostView.DirectionMode final int mode) {
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
                Constants.updateLikes(getApplicationContext(),mode,subredditModal.getId());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "UpVote onFail:" + t.getMessage());
            }
        });
    }

//    @Override
//    public Loader<List<CustomComment>> onCreateLoader(int id, Bundle args) {
//        switch (id) {
//            case LOADER_ID:
//                return new CommentsLoader(
//                        this,
//                        args.getString("subbreddit_name"),
//                        args.getString("id")
//                );
//        }
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<List<CustomComment>> loader, List<CustomComment> data) {
//        switch (loader.getId()) {
//            case LOADER_ID:
//                list.clear();
//                list.addAll(data);
//                this.commentsAdapter.notifyDataSetChanged();
//                break;
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<CustomComment>> loader) {
//        switch (loader.getId()) {
//            case LOADER_ID:
//                list.clear();
//                commentsAdapter.notifyDataSetChanged();
//                break;
//        }
//    }



//    void showDetailSubredditFragment(DetailPostModal modal) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_layout, DetailSubredditFragment.newInstance(modal), DetailSubredditFragment.class.getSimpleName())
//                .commit();
//    }

    private void updateVote(@PostView.DirectionMode Integer mode)
    {
        subredditModal.setLikes(mode);
        switch (mode){
            case DIRECTION_UP:{
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward_true);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);

            }
            break;
            case DIRECTION_DOWN:{
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward_true);

            }
            break;
            case DIRECTION_NULL:{
                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);
            }
            break;
            default:
        }
    }

    private void updateVoteBackground(ImageView imageView,int resId){
        Glide.with(this)
                .load("")
                .placeholder(resId)
                .into(imageView);
    }

    private void updateVoteCount(int c){

        int count = Integer.parseInt(tvVote.getText().toString());
        count =count+c;
        String newCount = String.valueOf(count);
        subredditModal.setCommentsCount(newCount);
        tvVote.setText(newCount);

    }

    @OnClick(R.id.image_up_vote)
    void onClickUpVote() {
        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);

        if (!isUserLoggedIn) {
            snackbar.show();
        } else {

            Integer mLikes = subredditModal.getLikes();
            if(mLikes== -1)
            {
                performVote(DIRECTION_NULL);
                updateVote(DIRECTION_NULL);
                tvVote.setTextColor(ContextCompat.getColor(this,R.color.grey_700));
                updateVoteCount(1);
            }
            else if(mLikes==0){
                performVote(DIRECTION_UP);
                updateVote(DIRECTION_UP);
                tvVote.setTextColor(ContextCompat.getColor(this,R.color.light_blue_500));
                updateVoteCount(1);
            }

        }
    }

    @OnClick(R.id.image_down_vote)
    void onClickDownVote() {
        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);

        if (!isUserLoggedIn) {
            snackbar.show();
        } else {

            Integer mLikes = subredditModal.getLikes();
            if(mLikes==1)
            {
                performVote(DIRECTION_NULL);
                updateVote(DIRECTION_NULL);
                tvVote.setTextColor(ContextCompat.getColor(this,R.color.grey_700));
                updateVoteCount(-1);
            }
            else if(mLikes==0){
                performVote(DIRECTION_DOWN);
                updateVote(DIRECTION_DOWN);
                tvVote.setTextColor(ContextCompat.getColor(this,R.color.green_500));
                updateVoteCount(-1);
            }
        }


    }

    @OnClick(R.id.fab)
    void onClickFab(){
        //open Reply Activity
        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);
        if (!isUserLoggedIn) {
            snackbar.show();
        } else {
            String name = subredditModal.getName();
            openReplyActivity(name);
        }
    }

    private void openReplyActivity(String thingId){
        Intent intent = new Intent(this,ReplyActivity.class);
        intent.putExtra("thing_id",thingId);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(this,
                        mUriReadTable,null,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                setDataInView(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                subredditModal = null;
                break;
        }
    }

    private void setDataInView(Cursor cursor){
        if(cursor.moveToFirst()){
            subredditModal = CommonOperations.getDetailModalFromCursor(cursor);
            if(subredditModal!=null)
                initData();
        }
        cursor.close();
    }
}
