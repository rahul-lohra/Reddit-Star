package com.android.rahul_lohra.redditstar.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.helper.AspectRatioImageView;
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
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

public class DetailSubredditFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CustomComment>> {

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

    @OnClick(R.id.image_up_vote)
    void onClickUpVote() {
        boolean isUserLoggedIn = UserState.isUserLoggedIn(getActivity());

        if (!isUserLoggedIn) {
            snackbar.show();
        } else {

            Integer mLikes = subredditModal.getLikes();
            if(mLikes== -1)
            {
                performVote(DIRECTION_NULL);
                updateVote(DIRECTION_NULL);
                tvVote.setTextColor(ContextCompat.getColor(getActivity(),R.color.grey_700));
                updateVoteCount(1);
            }
            else if(mLikes==0){
                performVote(DIRECTION_UP);
                updateVote(DIRECTION_UP);
                tvVote.setTextColor(ContextCompat.getColor(getActivity(),R.color.light_blue_500));
                updateVoteCount(1);
            }

        }
    }

    @OnClick(R.id.image_down_vote)
    void onClickDownVote() {
        boolean isUserLoggedIn = UserState.isUserLoggedIn(getActivity());

        if (!isUserLoggedIn) {
            snackbar.show();
        } else {

            Integer mLikes = subredditModal.getLikes();
            if(mLikes==1)
            {
                performVote(DIRECTION_NULL);
                updateVote(DIRECTION_NULL);
                tvVote.setTextColor(ContextCompat.getColor(getActivity(),R.color.grey_700));
                updateVoteCount(-1);
            }
            else if(mLikes==0){
                performVote(DIRECTION_DOWN);
                updateVote(DIRECTION_DOWN);
                tvVote.setTextColor(ContextCompat.getColor(getActivity(),R.color.green_500));
                updateVoteCount(-1);
            }
        }
    }

    Snackbar snackbar;
    private final int LOADER_ID = 1;
    CommentsAdapter commentsAdapter;
    List<CustomComment> list = new ArrayList<>();
    private DetailPostModal subredditModal;
    private final String TAG = DetailSubredditFragment.class.getSimpleName();
    @Inject
    @Named("withToken")
    Retrofit retrofit;
    private ApiInterface apiInterface;
    private String id;
    private Uri uri;

    public DetailSubredditFragment() {
        // Required empty public constructor
    }

    public static DetailSubredditFragment newInstance(DetailPostModal subredditModal,String id, Uri uri) {
        DetailSubredditFragment fragment = new DetailSubredditFragment();
        Bundle args = new Bundle();
        args.putParcelable("modal",subredditModal);
        args.putString("id",id);
        args.putParcelable("uri",uri);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        if (getArguments() != null) {
            subredditModal = (DetailPostModal) getArguments().getParcelable("modal");
            id = getArguments().getString("id");
            uri = (Uri)getArguments().getParcelable("uri");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_subreddit, container, false);
        ButterKnife.bind(this, v);

        if(subredditModal!=null){
            Bundle bundle = new Bundle();
            bundle.putString("id",subredditModal.getId());
            bundle.putString("subbreddit_name",subredditModal.getSubreddit());

            getLoaderManager().initLoader(LOADER_ID,bundle,this).forceLoad();
            setAdapter();
            initData();
        }

        return v;
    }

    private void initData(){
        tvTitle.setText(subredditModal.getTitle());
        tvComments.setText(subredditModal.getCommentsCount());
        tvVote.setText(subredditModal.getUps());
        tvCategory.setText("r/"+subredditModal.getSubreddit()+"-"+subredditModal.getTime());
        tvUsername.setText(subredditModal.getAuthor());
        tvSort.setText("new");
        String bigImageUrl = subredditModal.getBigImageUrl();
        Glide.with(this).
                load(bigImageUrl)
                .centerCrop()
                .crossFade()
                .into(imageView);
        snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.please_login), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.login), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonOperations.addNewAccount(getActivity());
//                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                        snackbar1.show();
                    }
                });
    }

    private void highlightVote(Integer likes) {
        if (likes != 0) {
            Integer resId = (likes == 1) ? R.drawable.ic_arrow_upward_true : R.drawable.ic_arrow_downward_true;
            Glide.with(this)
                    .load("")
                    .placeholder(resId)
                    .into((likes == 1) ? imageUpVote : imageDownVote);

            tvVote.setTextColor((likes == 1) ? ContextCompat.getColor(getActivity(), R.color.light_blue_500) : ContextCompat.getColor(getActivity(), R.color.green_500));
        }
    }

    private void setAdapter(){
        commentsAdapter = new CommentsAdapter(getContext(),list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(commentsAdapter);
    }

    @Override
    public Loader<List<CustomComment>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CommentsLoader(
                        getContext(),
                        args.getString("subbreddit_name"),
                        args.getString("id")
                );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<CustomComment>> loader, List<CustomComment> data) {
            switch (loader.getId())
            {
                case LOADER_ID:
                    list.clear();
                    list.addAll(data);
                    this.commentsAdapter.notifyDataSetChanged();
                    break;
            }
    }

    @Override
    public void onLoaderReset(Loader<List<CustomComment>> loader) {
        switch (loader.getId()){
            case LOADER_ID :
                list.clear();
                commentsAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void performVote(@PostView.DirectionMode final int mode) {
        String token = UserState.getAuthToken(getContext());
        String auth = "bearer " + token;
        Map<String, String> map = new HashMap<String, String>();
        map.put("dir", String.valueOf(mode));
        map.put("id", subredditModal.getName());
        map.put("rank", String.valueOf(2));
        apiInterface.postVote(auth, map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "UpVote onResponse:" + response.code());
                Constants.updateLikes(getContext(),mode,subredditModal.getId());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "UpVote onFail:" + t.getMessage());
            }
        });
    }

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
}
