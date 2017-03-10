package com.android.rahul_lohra.redditstar.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DashboardActivity;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.activity.ReplyActivity;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.ILogin;
import com.android.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.CommentsService;
import com.android.rahul_lohra.redditstar.storage.MyDatabase;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.CommonOperations;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class DetailSubredditFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    @Bind(R.id.imageView)
    AspectRatioImageView imageView;
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

    @OnClick(R.id.fab)
    void onCLicFab(){
        isUserLoggedIn = UserState.isUserLoggedIn(getActivity());
        if(isUserLoggedIn){
            startActivity(replyIntent);
        }else {
            iLogin.pleaseLogin();
        }

    }

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    private ApiInterface apiInterface;
    Snackbar snackbar;
    GlideDrawable glideDrawable = null;//required for thumnail cache


    private final int LOADER_ID = 1;
    private final int LOADER_ID_COMMENTS_POSTS = 3;
    private final String BUNDLE_LINK_ID = "link_id";
    private boolean isUserLoggedIn = false;
    public static final String ARG_ID = "arg_id";

    private String id;
    private int darkMutedColor = 0xFF333333;
    private ILogin iLogin;
    Intent replyIntent;


    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailPostModal subredditModal;
    private final String TAG = DetailSubredditFragment.class.getSimpleName();

    public DetailSubredditFragment() {
        // Required empty public constructor
    }

    public static DetailSubredditFragment newInstance(String id) {
        DetailSubredditFragment fragment = new DetailSubredditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        setHasOptionsMenu(true);
//        setRetainInstance(true);
        isUserLoggedIn = UserState.isUserLoggedIn(getContext());
        apiInterface = (isUserLoggedIn) ? retrofitWithToken.create(ApiInterface.class) : retrofitWithoutToken.create(ApiInterface.class);
        if (getArguments() != null)
            id = getArguments().getString(ARG_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_subreddit_new, container, false);
        ButterKnife.bind(this, v);
        setToolbar();
        setAdapter();
        prepareReplyIntent();

        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(getActivity());
                    }
                });

        return v;
    }
    void prepareReplyIntent(){
        replyIntent = new Intent(getActivity(),ReplyActivity.class);
        replyIntent.putExtra("thing_id",id);
    }

    private void setToolbar() {
        if(getActivity() instanceof DashboardActivity){
            return;
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });
    }

    private void setAdapter() {
        if( getActivity() instanceof DetailActivity){
            commentsAdapter = new CommentsAdapter(getActivity(),(DetailActivity)getActivity(), getContext(), null);
        }else if( getActivity() instanceof DashboardActivity){
            commentsAdapter = new CommentsAdapter(getActivity(),(DashboardActivity)getActivity(), getContext(), null);
        }
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(commentsAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = new Bundle();
        String properLinkId = id;
        bundle.putString(BUNDLE_LINK_ID, properLinkId);
        getLoaderManager().initLoader(LOADER_ID_COMMENTS_POSTS, bundle, this);
        getLoaderManager().initLoader(LOADER_ID, bundle, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                String mSeletion = MyPostsColumn.KEY_ID + "=?";
                String mSelectionArgs[] = {this.id};
                Uri mUri_1 = MyProvider.PostsLists.CONTENT_URI;
                String proj_1[] = {MyPostsColumn.KEY_SUBREDDIT, MyPostsColumn.KEY_BIG_IMAGE_URL, MyPostsColumn.KEY_POST_HINT, MyPostsColumn.KEY_THUMBNAIL, MyPostsColumn.KEY_URL};
                return new CursorLoader(getContext(), mUri_1, proj_1, mSeletion, mSelectionArgs, null);

            case LOADER_ID_COMMENTS_POSTS: {
                String linkId = args.getString(BUNDLE_LINK_ID);
                String mWhere3 = MyDatabase.USER_POSTS_TABLE + "." + MyPostsColumn.KEY_ID + "=?";
                String mWhereArgs3[] = {linkId};
                String sortOrder = MyDatabase.COMMENTS_TABLE + "." + CommentsColumn.KEY_SQL_ID + " LIMIT 50";
                return new CursorLoader(getContext(), MyProvider.PostsComments.CONTENT_URI, MyProvider.PostsComments.mProjection, mWhere3, mWhereArgs3, sortOrder);
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
        public void onLoaderReset (Loader < Cursor > loader) {
            switch (loader.getId()) {
                case LOADER_ID:
                    commentsAdapter.swapCursor(null);
                    break;
            }
        }

    private void loadImage(String thumbnail, final String bigImageUrl) {
        Glide.with(this).
                load(thumbnail)
                .centerCrop()
                .crossFade()
                .error(R.drawable.ic_reddit)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imageView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        glideDrawable = resource;
                        imageView.setImageDrawable(resource);
                        updatePalete(imageView);
                        if (bigImageUrl != null) {
                            loadBigImage(bigImageUrl, glideDrawable);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    private void updatePalete(AspectRatioImageView imageView) {
        final Drawable dr = ((ImageView) imageView).getDrawable();
        Bitmap bmap =  ((GlideBitmapDrawable)dr.getCurrent()).getBitmap();
        Palette.from(bmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                // Use generated instance
                darkMutedColor = p.getDarkMutedColor(0xFF333333);
                int mutedLightColor = p.getLightMutedColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                int mutedColor = p.getMutedColor(0xFF333333);
                collapsingToolbarLayout.setContentScrimColor(darkMutedColor);
                if(rv.getChildAt(0) instanceof CardView){
                    CardView cardView = (CardView) rv.getChildAt(0);
                    if (cardView != null) {
                        LinearLayout linearLayout = ((LinearLayout) cardView.findViewById(R.id.linear_layout));
                        if(linearLayout!=null)
                            linearLayout.setBackgroundColor(darkMutedColor);
                    }
                }

                fab.setBackgroundTintList(ColorStateList.valueOf(mutedLightColor));

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(darkMutedColor);
                }
            }
        });
    }

    private void perpareImage(Cursor cursor){
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String subreddit = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT));
                String bigImageUrl = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));
                String postHint = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_POST_HINT));
                String thumbnail = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_THUMBNAIL));
                String url = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_URL));
                CommentsService.requestComments(getContext(),this.id,subreddit);

                if (postHint != null) {
                    if (postHint.equals("image") || postHint.equals("link")|| postHint.equals("rich:video")) {

                        if(thumbnail.equals("default")){
//                            imageView.setVisibility(View.GONE);

                            loadPreview();
                            return;
                        }

                        if (thumbnail.endsWith("jpg") || thumbnail.endsWith("png") || thumbnail.endsWith("jpeg") || thumbnail.endsWith("webp")) {

                            if (url.endsWith("gifv") || url.endsWith("gif")) {
                                loadImage(thumbnail, null);
                            } else if (url.endsWith("jpg") || url.endsWith("png") || url.endsWith("jpeg") || url.endsWith("webp")) {
                                loadImage(thumbnail, bigImageUrl);
                                loadBigImage(bigImageUrl,null);
                            } else {
                                loadImage(thumbnail, null);
                            }
                        } else {
                            imageView.setVisibility(View.GONE);

                        }
                    }else {
                        loadPreview();
                    }
                }else {
                    imageView.setVisibility(View.GONE);
                }

            } else {
//                getActivity().finish();
            }

        }
    }
    private void loadPreview(){
        Glide.with(this)
                .load("")
                .placeholder(R.drawable.ic_reddit)
                .into(imageView);
    }

    private void loadBigImage (String bigImageUrl,final GlideDrawable glideDrawable){
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
                        updatePalete(imageView);
                        return false;
                    }
                })
                .into(imageView);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILogin) {
            iLogin = (ILogin) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ILogin");
        }
    }


}
