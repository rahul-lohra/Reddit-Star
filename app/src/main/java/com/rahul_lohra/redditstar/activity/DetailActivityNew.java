package com.rahul_lohra.redditstar.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.fragments.CommentsFragment;
import com.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.rahul_lohra.redditstar.service.CommentsService;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.rahul_lohra.redditstar.Utility.CommonOperations;
import com.rahul_lohra.redditstar.Utility.UserState;
import com.rahul_lohra.redditstar.viewHolder.PostView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

import static com.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_DOWN;
import static com.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_NULL;
import static com.rahul_lohra.redditstar.viewHolder.PostView.DIRECTION_UP;

/*
 Needs two item in intent
 1. sqlId or id
 2. uri of table
 */
public class DetailActivityNew extends BaseActivity implements
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
//    @Bind(R.id.rv)
//    RecyclerView rv;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;


    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;
    Snackbar snackbar;

    private final int LOADER_ID = 1;
    private final int LOADER_ID_COMMENTS = 2;
    private final String BUNDLE_LINK_ID = "link_id";

    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailPostModal subredditModal;
    private final String TAG = DetailActivityNew.class.getSimpleName();
    private Uri mUriReadTable;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_subreddit_2);
        ButterKnife.bind(this);
        ((Initializer)getApplicationContext()).getNetComponent().inject(this);

//        Intent intent = getIntent();
//        id = intent.getStringExtra("id");
//        mUriReadTable = intent.getParcelableExtra("uri");
//        subredditModal = (DetailPostModal) intent.getParcelableExtra("modal");
//
//        apiInterface = retrofit.create(ApiInterface.class);
//
//        String mSeletion = MyPostsColumn.KEY_ID +"=?";
//        String mSelectionArgs[] = {id};
//
//        Cursor cursor = getContentResolver().query(mUriReadTable,null,mSeletion,mSelectionArgs,null);
//        setDataInView(cursor);
//
//        String properLinkId = id;
//        showCommentsFragment(R.id.frame_layout,properLinkId,subredditModal.getSubreddit());
//
//        //requestComments
//        requestComments();
//
//        Bundle bundle = new Bundle();
//        bundle.putString(BUNDLE_LINK_ID,properLinkId);
//        getSupportLoaderManager().initLoader(LOADER_ID_COMMENTS,bundle,this);

//        setAdapter();

    }

    void showCommentsFragment(@IdRes int layoutId,String linkId,String subreddit) {
        getSupportFragmentManager().beginTransaction()
                .add(layoutId, CommentsFragment.newInstance(linkId,subreddit), CommentsFragment.class.getSimpleName())
                .commit();
    }

    private void requestComments(){
        Toast.makeText(getApplicationContext(),getString(R.string.loading_comments),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),CommentsService.class);
        intent.putExtra(CommentsService.POST_ID,subredditModal.getId());
        intent.putExtra(CommentsService.SUBREDDIT_NAME,subredditModal.getSubreddit());
        startService(intent);
    }

    private void getComments(){
        String subredditName = subredditModal.getSubreddit();
        String postId = subredditModal.getId();

        Intent intent = new Intent(this, CommentsService.class);
        intent.putExtra(CommentsService.POST_ID,postId);
        intent.putExtra(CommentsService.SUBREDDIT_NAME,subredditName);
        startService(intent);

    }

    private void initData() {
        tvTitle.setText(subredditModal.getTitle());
        tvComments.setText(subredditModal.getCommentsCount());
        tvVote.setText(subredditModal.getUps());
        tvCategory.setText("r/" + subredditModal.getSubreddit() + "-" + subredditModal.getTime());
        tvUsername.setText(subredditModal.getAuthor());
        tvSort.setText(getString(R.string.new_text));
        String bigImageUrl = subredditModal.getBigImageUrl();
        String postHint = subredditModal.getPostHint();
        if(postHint!=null){
            if(postHint.equals("image")){
                Glide.with(this).
                        load(bigImageUrl)
                        .centerCrop()
                        .crossFade()
                        .into(imageView);
            }else {
                imageView.setVisibility(View.GONE);
            }
        }

        Integer likes = subredditModal.getLikes();
        highlightVote(likes);

        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(DetailActivityNew.this);
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
//        commentsAdapter = new CommentsAdapter(this, null);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(commentsAdapter);
    }

//    private void performVoteAndUpdateLikes(@PostView.DirectionMode final int mode) {
//        String token = UserState.getAuthToken(this);
//        String auth = "bearer " + token;
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("dir", String.valueOf(mode));
//        map.put("id", subredditModal.getName());
//        map.put("rank", String.valueOf(2));
//        apiInterface.postVote(auth, map).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                Log.d(TAG, "UpVote onResponse:" + response.code());
//                Constants.updateLikes(getApplicationContext(),mode,subredditModal.getId());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d(TAG, "UpVote onFail:" + t.getMessage());
//            }
//        });
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
//                performVoteAndUpdateLikes(DIRECTION_NULL);
//                updateVote(DIRECTION_NULL);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.grey_700));
//                updateVoteCount(1);
//            }
//            else if(mLikes==0){
//                performVoteAndUpdateLikes(DIRECTION_UP);
//                updateVote(DIRECTION_UP);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.light_blue_500));
//                updateVoteCount(1);
//            }
//
//        }
//    }
//
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
//                performVoteAndUpdateLikes(DIRECTION_NULL);
//                updateVote(DIRECTION_NULL);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.grey_700));
//                updateVoteCount(-1);
//            }
//            else if(mLikes==0){
//                performVoteAndUpdateLikes(DIRECTION_DOWN);
//                updateVote(DIRECTION_DOWN);
//                tvVote.setTextColor(ContextCompat.getColor(this,R.color.green_500));
//                updateVoteCount(-1);
//            }
//        }
//    }

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
        Uri commentsUri = MyProvider.CommentsLists.CONTENT_URI;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(this,mUriReadTable,null,null,null,null);
            case LOADER_ID_COMMENTS:
            {
                String linkId = args.getString(BUNDLE_LINK_ID);
                String mWhereComments = CommentsColumn.KEY_LINK_ID +"=?";
                String mWhereCommentsArgs[] = {linkId};
                String sortOrder = CommentsColumn.KEY_SQL_ID+" LIMIT 200";
                return new CursorLoader(this,commentsUri,null,mWhereComments,mWhereCommentsArgs,sortOrder);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                setDataInView(data);
                break;
            case LOADER_ID_COMMENTS:
            {
                commentsAdapter.swapCursor(data);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                subredditModal = null;
                break;
            case LOADER_ID_COMMENTS:{
                commentsAdapter.swapCursor(null);
            }
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
