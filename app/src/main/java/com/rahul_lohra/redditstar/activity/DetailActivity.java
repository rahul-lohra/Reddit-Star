package com.rahul_lohra.redditstar.activity;

import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Priority;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.rahul_lohra.redditstar.contract.ILogin;
import com.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.rahul_lohra.redditstar.helper.AspectRatioKenBurnsImageView;
import com.rahul_lohra.redditstar.service.CommentsService;
import com.rahul_lohra.redditstar.storage.MyDatabase;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.rahul_lohra.redditstar.Utility.CommonOperations;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rahul_lohra.redditstar.service.CommentsService.TYPE_LOADING;
import static com.rahul_lohra.redditstar.service.CommentsService.TYPE_LOAD_FAIL;
import static com.rahul_lohra.redditstar.service.CommentsService.TYPE_LOAD_SUCCESS;

public class DetailActivity extends BaseActivity implements
        ILogin,
        CommentsService.ICommentsService,
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.imageView)
    AspectRatioImageView imageView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    CommentsAdapter commentsAdapter;
    CommentsService commentsService;

    private final int LOADER_ID = 1;
    private final int LOADER_ID_COMMENTS_POSTS = 3;
    private final String BUNDLE_LINK_ID = "link_id";
    private boolean isUserLoggedIn = false;
    public static final String ARG_ID = "arg_id";

    GlideDrawable glideDrawable = null;//required for thumnail cache


    private String id;
    private int darkMutedColor = 0xFF333333;
    Intent replyIntent;
    @OnClick(R.id.fab)
    void onCLicFab(){
        isUserLoggedIn = UserState.isUserLoggedIn(this);
        if(isUserLoggedIn){
            startActivity(replyIntent);
        }else {
            pleaseLogin();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        commentsService = new CommentsService(getApplicationContext());
        commentsService.setICommentsService(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Bundle bundle = new Bundle();
        String properLinkId = id;
        bundle.putString(BUNDLE_LINK_ID, properLinkId);
        getSupportLoaderManager().initLoader(LOADER_ID_COMMENTS_POSTS, bundle, this);
        getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);
        setToolbar();
        setAdapter();
        prepareReplyIntent();
//        if (savedInstanceState == null) {
//            showSearchFragment();
//        }
        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(DetailActivity.this);
                    }
                });
    }

    void prepareReplyIntent(){
        replyIntent = new Intent(this,ReplyActivity.class);
        replyIntent.putExtra("thing_id",id);
    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
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

    private void setAdapter() {
        commentsAdapter = new CommentsAdapter(this,this, this, null);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(commentsAdapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        showLoader();
        switch (id) {
            case LOADER_ID:
                String mSeletion = MyPostsColumn.KEY_ID + "=?";
                String mSelectionArgs[] = {this.id};
                Uri mUri_1 = MyProvider.PostsLists.CONTENT_URI;
                String proj_1[] = {MyPostsColumn.KEY_SUBREDDIT, MyPostsColumn.KEY_BIG_IMAGE_URL, MyPostsColumn.KEY_POST_HINT, MyPostsColumn.KEY_THUMBNAIL, MyPostsColumn.KEY_URL,MyPostsColumn.KEY_IS_BIG_IMAGE_URL_HAS_IMAGE,MyPostsColumn.KEY_IS_THUMBNAIL_HAS_IMAGE};
                return new CursorLoader(this, mUri_1, proj_1, mSeletion, mSelectionArgs, null);

            case LOADER_ID_COMMENTS_POSTS: {
                String linkId = args.getString(BUNDLE_LINK_ID);
                String mWhere3 = MyDatabase.USER_POSTS_TABLE + "." + MyPostsColumn.KEY_ID + "=? AND "+MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_IS_EXPANDED+" = ?";
                String mWhereArgs3[] = {linkId,String.valueOf(1)};
                String sortOrder = MyDatabase.COMMENTS_TABLE + "." + CommentsColumn.KEY_SQL_ID + " LIMIT 50";
                return new CursorLoader(this, MyProvider.PostsComments.CONTENT_URI, MyProvider.PostsComments.mProjection, mWhere3, mWhereArgs3, sortOrder);
            }
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch (loader.getId()) {
            case LOADER_ID:
                perpareImage(cursor);
                break;
            case LOADER_ID_COMMENTS_POSTS:
                commentsAdapter.swapCursor(cursor);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                commentsAdapter.swapCursor(null);
                break;
        }
    }

    private void showLoader(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoader(){
        progressBar.setVisibility(View.GONE);
    }

    private synchronized void loadImage(String thumbnail, final String bigImageUrl) {

        Glide.with(DetailActivity.this)
                .load(bigImageUrl)
//               .placeholder(glideDrawable)
                .priority(Priority.IMMEDIATE)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        if(true)
            return;

        Glide.with(this).
                load(thumbnail)
                .centerCrop()
                .crossFade()
//                .placeholder(R.drawable.ic_reddit)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imageView.setVisibility(View.GONE);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        System.out.println("Set Pallete Image");
                        glideDrawable = resource;
//                        imageView.setImageDrawable(resource);
                        updatePalete(resource);
                        if (bigImageUrl != null) {
//                            loadBigImage(bigImageUrl, glideDrawable);
                            Glide.with(DetailActivity.this)
                                    .load(bigImageUrl)
                                    .placeholder(glideDrawable)
                                    .centerCrop()
                                    .priority(Priority.IMMEDIATE)
                                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            System.out.println("Set Big Image");
                                            return false;
                                        }
                                    })
                                    .into(imageView);

                        }
                        return true;
                    }
                })
                .into(imageView);
        System.out.println("Set small Image");

    }

    private synchronized void updatePalete(GlideDrawable glideDrawable) {
//        final Drawable dr = glideDrawable;
        Bitmap bmap = ((GlideBitmapDrawable) glideDrawable.getCurrent()).getBitmap();
        Palette.from(bmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // Use generated instance
                darkMutedColor = p.getDarkMutedColor(0xFF333333);
                int mutedLightColor = p.getLightMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                int mutedColor = p.getMutedColor(0xFF333333);
//                collapsingToolbarLayout.setContentScrimColor(darkMutedColor);
                if (rv.getChildAt(0) instanceof CardView) {
                    CardView cardView = (CardView) rv.getChildAt(0);
                    if (cardView != null) {
                        LinearLayout linearLayout = ((LinearLayout) cardView.findViewById(R.id.linear_layout));
                        if (linearLayout != null)
                            linearLayout.setBackgroundColor(darkMutedColor);
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

    private synchronized void perpareImage(Cursor cursor) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String subreddit = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT));
                String bigImageUrl = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));
                String postHint = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_POST_HINT));
                String thumbnail = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_THUMBNAIL));
                String url = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_URL));
                final int thumbnailHasIMage =  cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_IS_THUMBNAIL_HAS_IMAGE));
                final int bigImageUrlHasImage =  cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_IS_BIG_IMAGE_URL_HAS_IMAGE));

                commentsService.requestComments(this, this.id, subreddit);

                loadImageNew(bigImageUrl,bigImageUrlHasImage,thumbnail,thumbnailHasIMage);
                if(true)
                    return;

                if (postHint != null) {
                    if (postHint.equals("image") || postHint.equals("link")|| postHint.equals("rich:video")) {

                        if (thumbnail.endsWith("jpg") || thumbnail.endsWith("png") || thumbnail.endsWith("jpeg") || thumbnail.endsWith("webp")) {

                            if (url.endsWith("gifv") || url.endsWith("gif")) {
                                loadImage(thumbnail, null);
                            } else if (url.endsWith("jpg") || url.endsWith("png") || url.endsWith("jpeg") || url.endsWith("webp")) {
                                loadImage(thumbnail, bigImageUrl);
                            } else if(bigImageUrl!=null && (bigImageUrl.startsWith("http"))){
                                loadImage(thumbnail, bigImageUrl);
                            }  else {
                                loadImage(thumbnail, null);
                            }
                        } else {
                            imageView.setVisibility(View.GONE);

                        }
                    }else {
//                        loadPreview();
                    }
                }else {
                    imageView.setVisibility(View.GONE);
                }
            }
        }
    }

    private void loadImageNew(final String bigImageUrl, int bigImageUrlHasImage, final String thumbnail, final int thumbnailHasImage) {
        if(bigImageUrlHasImage ==1){
            Glide.with(this)
                    .load(bigImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.e("error:",bigImageUrl);
                            e.printStackTrace();
//                            loadThumbnail(thumbnail,thumbnailHasImage);
//                            collapsingToolbarLayout.setVisibility(View.GONE);
                            imageView.setVisibility(View.GONE);
                            imageView.setTransitionName("");
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            updatePalete(resource);
                            imageView.setImageDrawable(resource);
                            return true;
                        }
                    })
                    .into(imageView);
            imageView.setTransitionName("profile");
            imageView.setVisibility(View.VISIBLE);
        }else {
//            loadThumbnail(thumbnail,thumbnailHasImage);
//            collapsingToolbarLayout.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            imageView.setTransitionName("");
        }


}
    private void loadThumbnail(String thumbnail,int thumbnailHasImage) {

        if(thumbnailHasImage==1){
            if (thumbnail != null) {
                imageView.setTransitionName("profile");
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(thumbnail)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                updatePalete(resource);
                                imageView.setImageDrawable(resource);
                                return true;
                            }
                        })
                        .into(imageView);
            }
        }else {
            imageView.setTransitionName("");
            imageView.setVisibility(View.GONE);
        }
    }

            private synchronized void loadBigImage (String bigImageUrl,final GlideDrawable glideDrawable){
                Glide.with(this).
                        load(bigImageUrl)
                        .centerCrop()
                        .crossFade()
                        .placeholder(glideDrawable)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                imageView.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                imageView.setImageDrawable(resource);
                                updatePalete(resource);
                                return false;
                            }
                        });
//                        .into(imageView);
            }

            @Override
            public void pleaseLogin () {
                snackbar.show();
            }

            private synchronized void loadPreview(){
                Glide.with(this)
                        .load("")
                        .placeholder(R.drawable.ic_reddit)
                .into(imageView);
            }

    @Override
    public void commentsLoadedState(int commentsLoadType) {
        switch (commentsLoadType){
            case TYPE_LOADING:
                showLoader();
                break;
            case TYPE_LOAD_SUCCESS:
            case TYPE_LOAD_FAIL:
                hideLoader();
        }
    }
}

