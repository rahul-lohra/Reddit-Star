package com.android.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.CommentsService;
import com.android.rahul_lohra.redditstar.storage.MyDatabase;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.CommonOperations;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.android.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
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

/*
 Needs two item in intent
 1. sqlId or id
 2. uri of table
 */
public class DetailActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    @Bind(R.id.imageView)
    AspectRatioImageView imageView;
    //    @Bind(R.id.tv_category)
//    TextView tvCategory;
//    @Bind(R.id.tv_title)
//    TextView tvTitle;
//    @Bind(R.id.tv_username)
//    TextView tvUsername;
//    @Bind(R.id.tv_share)
//    TextView tvShare;
//    @Bind(R.id.tv_comments)
//    TextView tvComments;
//    @Bind(R.id.tv_vote)
//    TextView tvVote;
//    @Bind(R.id.image_up_vote)
//    ImageView imageUpVote;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private int darkMutedColor = 0xFF333333;

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    private ApiInterface apiInterface;
    Snackbar snackbar;

    private final int LOADER_ID = 1;
    private final int LOADER_ID_COMMENTS = 2;
    private final int LOADER_ID_COMMENTS_POSTS = 3;

    private final String BUNDLE_LINK_ID = "link_id";

    CommentsAdapter commentsAdapter;
    //    List<CustomComment> list = new ArrayList<>();
//    private DetailPostModal subredditModal;
    private final String TAG = DetailActivity.class.getSimpleName();
    private Uri mUriReadTable;
    private String id;
    private boolean isUserLoggedIn = false;
    private String subreddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_subreddit_new);
        ButterKnife.bind(this);
        ((Initializer) getApplicationContext()).getNetComponent().inject(this);

        setToolbar();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mUriReadTable = intent.getParcelableExtra("uri");
//        subredditModal = (DetailPostModal) intent.getParcelableExtra("modal");

        isUserLoggedIn = UserState.isUserLoggedIn(getApplicationContext());
        apiInterface = (isUserLoggedIn)?retrofitWithToken.create(ApiInterface.class):retrofitWithoutToken.create(ApiInterface.class);
//
        String mSeletion = MyPostsColumn.KEY_ID +"=?";
        String mSelectionArgs[] = {id};
        String mProj[]={MyPostsColumn.KEY_SUBREDDIT,MyPostsColumn.KEY_BIG_IMAGE_URL,MyPostsColumn.KEY_POST_HINT};
        Cursor cursor = getContentResolver().query(MyProvider.PostsLists.CONTENT_URI,mProj,mSeletion,mSelectionArgs,null);

        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                subreddit = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT));
                String bigImageUrl = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));
                String postHint = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_POST_HINT));
                if (postHint != null) {
                    if (postHint.equals("image")) {
                        Glide.with(this).
                                load(bigImageUrl)
                                .centerCrop()
                                .crossFade()
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        imageView.setImageDrawable(resource);

                                        updatePalete(imageView);
                                        return false;
                                    }
                                })
                                .into(imageView);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                } else {
//                    finish();
                }
                cursor.close();
            }else {
                finish();
            }
        }

        requestComments(id);
//
        Bundle bundle = new Bundle();
        String properLinkId = id;
        bundle.putString(BUNDLE_LINK_ID, properLinkId);
        getSupportLoaderManager().initLoader(LOADER_ID_COMMENTS_POSTS, bundle, this);
        getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);

        setAdapter();


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

        //Animation Code
//        supportPostponeEnterTransition();
//        startPostponedEnterTransition();
//        setWindowAnimation();
    }

    private void setWindowAnimation(){
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setReturnTransition(slide);
    }




    private void setToolbar(){
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void updatePalete(AspectRatioImageView imageView) {

        Drawable dr = ((ImageView) imageView).getDrawable();
        Bitmap bmap =  ((GlideBitmapDrawable)dr.getCurrent()).getBitmap();
//        imageView.buildDrawingCache();
//        Bitmap bmap = imageView.getDrawingCache();
        // Asynchronous
        Palette.from(bmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // Use generated instance
                darkMutedColor = p.getDarkMutedColor(0xFF333333);
                int mutedLightColor = p.getLightMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                int mutedColor = p.getMutedColor(0xFF333333);
//                tvBody.setTextColor(darkMutedColor);
//                linearLayout.setBackgroundColor(darkMutedColor);
                collapsingToolbarLayout.setContentScrimColor(darkMutedColor);
                CardView cardView = (CardView) rv.getChildAt(0);
                if (cardView != null) {
//                    cardView.setCardBackgroundColor(darkMutedColor);
                    LinearLayout linearLayout = ((LinearLayout) cardView.findViewById(R.id.linear_layout));
                    if(linearLayout!=null){
                        linearLayout.setBackgroundColor(darkMutedColor);
//                        ((TextView)cardView.findViewById(R.id.tv_category)).setTextColor(darkMutedColor);
//                        ((TextView)cardView.findViewById(R.id.tv_username)).setTextColor(darkMutedColor);
                    }


                }

                fab.setBackgroundTintList(ColorStateList.valueOf(mutedLightColor));

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(darkMutedColor);
                }
            }
        });
    }

    private void requestComments(String linkId) {

        String mProj[] = {CommentsColumn.KEY_SQL_ID};
        String mWhere = CommentsColumn.KEY_LINK_ID + "=?";
        String mWhereArgs[] = {linkId};
        Cursor cursor = getContentResolver().query(MyProvider.CommentsLists.CONTENT_URI, mProj, mWhere, mWhereArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {


            } else {

                Toast.makeText(this, getString(R.string.loading_comments), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CommentsService.class);
                intent.putExtra(CommentsService.POST_ID,id);
                intent.putExtra(CommentsService.SUBREDDIT_NAME, subreddit);
                startService(intent);
            }
            cursor.close();
        }
    }

//    private void getComments() {
//        String subredditName = subredditModal.getSubreddit();
//        String postId = subredditModal.getId();
//
//        Intent intent = new Intent(this, CommentsService.class);
//        intent.putExtra(CommentsService.POST_ID, postId);
//        intent.putExtra(CommentsService.SUBREDDIT_NAME, subredditName);
//        startService(intent);
//
//    }

    private void initData() {
//        tvTitle.setText(subredditModal.getTitle());
//        tvComments.setText(subredditModal.getCommentsCount());
//        tvVote.setText(subredditModal.getUps());
//        tvCategory.setText("r/" + subredditModal.getSubreddit() + "-" + subredditModal.getTime());
//        tvUsername.setText(subredditModal.getAuthor());
//        tvSort.setText("new");
//        String bigImageUrl = subredditModal.getBigImageUrl();
//        String postHint = subredditModal.getPostHint();
//        if(postHint!=null){
//            if(postHint.equals("image")){
//                Glide.with(this).
//                        load(bigImageUrl)
//                        .centerCrop()
//                        .crossFade()
//                        .into(imageView);
//            }else {
//                imageView.setVisibility(View.GONE);
//            }

//        }

//        Integer likes = subredditModal.getLikes();
//
//        highlightVote(likes);


    }

    private void highlightVote(Integer likes) {
//        if(likes!=0){
//            Integer resId = (likes==1) ? R.drawable.ic_arrow_upward_true : R.drawable.ic_arrow_downward_true;
//            Glide.with(this)
//                    .load("")
//                    .placeholder(resId)
//                    .into((likes==1) ? imageUpVote : imageDownVote);
//
//            tvVote.setTextColor((likes==1)?ContextCompat.getColor(this,R.color.light_blue_500):ContextCompat.getColor(this,R.color.green_500));
//        }

    }

    private void setAdapter() {
        commentsAdapter = new CommentsAdapter(this, null);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(commentsAdapter);
    }

    private void performVote(@PostView.DirectionMode final int mode) {
        String token = UserState.getAuthToken(this);
        String auth = "bearer " + token;
        Map<String, String> map = new HashMap<String, String>();
        map.put("dir", String.valueOf(mode));
        map.put("id", subreddit);
        map.put("rank", String.valueOf(2));
        apiInterface.postVote(auth, map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "UpVote onResponse:" + response.code());
//                Constants.updateLikes(getApplicationContext(), mode, subredditModal.getId());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "UpVote onFail:" + t.getMessage());
            }
        });
    }


//    private void updateVote(@PostView.DirectionMode Integer mode)
//    {
//        subredditModal.setLikes(mode);
//        switch (mode){
//            case DIRECTION_UP:{
//                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward_true);
//                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);
//
//            }
//            break;
//            case DIRECTION_DOWN:{
//                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
//                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward_true);
//
//            }
//            break;
//            case DIRECTION_NULL:{
//                updateVoteBackground(imageUpVote,R.drawable.ic_arrow_upward);
//                updateVoteBackground(imageDownVote,R.drawable.ic_arrow_downward);
//            }
//            break;
//            default:
//        }
//    }

    private void updateVoteBackground(ImageView imageView, int resId) {
        Glide.with(this)
                .load("")
                .placeholder(resId)
                .into(imageView);
    }

    private void updateVoteCount(int c) {

//        int count = Integer.parseInt(tvVote.getText().toString());
//        count =count+c;
//        String newCount = String.valueOf(count);
//        subredditModal.setCommentsCount(newCount);
//        tvVote.setText(newCount);

    }

//    @OnClick(R.id.image_up_vote)
//    void onClickUpVote() {
//        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);
//
//        if (!isUserLoggedIn) {
//            snackbar.show();
//        } else {
//
//            Integer mLikes = subredditModal.getLikes();
//            if(mLikes== -1)
//            {
//                performVote(DIRECTION_NULL);
//                updateVote(DIRECTION_NULL);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.grey_700));
//                updateVoteCount(1);
//            }
//            else if(mLikes==0){
//                performVote(DIRECTION_UP);
//                updateVote(DIRECTION_UP);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.light_blue_500));
//                updateVoteCount(1);
//            }
//
//        }
//    }

//    @OnClick(R.id.image_down_vote)
//    void onClickDownVote() {
//        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);
//
//        if (!isUserLoggedIn) {
//            snackbar.show();
//        } else {
//
//            Integer mLikes = subredditModal.getLikes();
//            if(mLikes==1)
//            {
//                performVote(DIRECTION_NULL);
//                updateVote(DIRECTION_NULL);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.grey_700));
//                updateVoteCount(-1);
//            }
//            else if(mLikes==0){
//                performVote(DIRECTION_DOWN);
//                updateVote(DIRECTION_DOWN);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.green_500));
//                updateVoteCount(-1);
//            }
//        }
//
//
//    }

    @OnClick(R.id.fab)
    void onClickFab() {
        //open Reply Activity
        boolean isUserLoggedIn = UserState.isUserLoggedIn(this);
        if (!isUserLoggedIn) {
            snackbar.show();
        } else {
            String name = id;
            openReplyActivity(name);
        }
    }

    private void openReplyActivity(String thingId) {
        Intent intent = new Intent(this, ReplyActivity.class);
        intent.putExtra("thing_id", thingId);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID: {
                String mId = args.getString(BUNDLE_LINK_ID);
                String mProjection[] = {MyPostsColumn.KEY_POST_HINT, MyPostsColumn.KEY_BIG_IMAGE_URL};
                String mWhere = MyPostsColumn.KEY_ID + " =?";
                String mWherArgs[] = {mId};
                return new CursorLoader(this,
                        mUriReadTable, mProjection, mWhere, mWherArgs, null);

            }
            case LOADER_ID_COMMENTS:
//            {
//                String linkId = args.getString(BUNDLE_LINK_ID);
//                String mWhereComments = CommentsColumn.KEY_LINK_ID +"=?";
//                String mWhereCommentsArgs[] = {linkId};
//                String sortOrder = CommentsColumn.KEY_SQL_ID+" LIMIT 200";
//                return new CursorLoader(this,commentsUri,null,mWhereComments,mWhereCommentsArgs,sortOrder);
//            }
            case LOADER_ID_COMMENTS_POSTS: {
                String linkId = args.getString(BUNDLE_LINK_ID);

                String mWhere3 = MyDatabase.USER_POSTS_TABLE + "." + MyPostsColumn.KEY_ID + "=?";
                String mWhereArgs3[] = {linkId};
                String sortOrder = MyDatabase.COMMENTS_TABLE + "." + CommentsColumn.KEY_SQL_ID + " LIMIT 50";

                return new CursorLoader(this, MyProvider.PostsComments.CONTENT_URI, MyProvider.PostsComments.mProjection, mWhere3, mWhereArgs3, sortOrder);
            }


        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
//                setDataInView(data);
                break;
            case LOADER_ID_COMMENTS: {
                commentsAdapter.swapCursor(data);
            }
            break;
            case LOADER_ID_COMMENTS_POSTS:
                commentsAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
//                subredditModal = null;
                break;
            case LOADER_ID_COMMENTS: {
                commentsAdapter.swapCursor(null);
            }
        }
    }

//    private void setDataInView(Cursor cursor) {
//
//    }
}
