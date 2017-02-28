package com.android.rahul_lohra.redditstar.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
        boolean isUserLoggedIn = UserState.isUserLoggedIn(getActivity());

        if (!isUserLoggedIn) {
            snackbar.show();
//            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
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

    public DetailSubredditFragment() {
        // Required empty public constructor
    }

    public static DetailSubredditFragment newInstance(DetailPostModal subredditModal) {
        DetailSubredditFragment fragment = new DetailSubredditFragment();
        Bundle args = new Bundle();
        args.putParcelable("modal",subredditModal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        if (getArguments() != null) {
            subredditModal = (DetailPostModal) getArguments().getParcelable("modal");
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

    private void performVote(@PostView.DirectionMode int mode) {
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
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "UpVote onFail:" + t.getMessage());
            }
        });
    }
}
