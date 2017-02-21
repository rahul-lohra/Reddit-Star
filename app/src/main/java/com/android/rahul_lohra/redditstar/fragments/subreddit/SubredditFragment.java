package com.android.rahul_lohra.redditstar.fragments.subreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.FrontPageAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.loader.SubredditLoader;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponseData;
import com.android.rahul_lohra.redditstar.modal.t5_Subreddit.t5_Response;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.GetSubredditListService;
import com.android.rahul_lohra.redditstar.utility.UserState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubredditFragment extends Fragment implements LoaderManager.LoaderCallbacks<FrontPageResponse> {
    /*
    1. make two requests
    2. about subreddit
    3. subreddit/hot api
     */

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;

    Retrofit retrofit;

    ApiInterface apiInterface;
    boolean isUserLoggedIn;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SubredditFragment.class.getSimpleName();
    private final int LOADER_ID = 1;


    @Bind(R.id.app_bar_image)
    ImageView appBarImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;

    List<FrontPageChild> list = new ArrayList<>();

    private String subredditName;
    private String mParam2;
    private FrontPageAdapter adapter;
    private FrontPageResponseData frontPageResponseData;

    public SubredditFragment() {
        // Required empty public constructor
    }

    public static SubredditFragment newInstance(String subredditNameParam, String param2) {
        SubredditFragment fragment = new SubredditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, subredditNameParam);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        retrofit = (isUserLoggedIn = UserState.isUserLoggedIn(getContext())) ? retrofitWithToken : retrofitWithoutToken;
        apiInterface = retrofit.create(ApiInterface.class);
        adapter = new FrontPageAdapter(SubredditFragment.this,getActivity().getApplicationContext(), list, retrofit);

        if (getArguments() != null) {
            subredditName = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_subreddit, container, false);
        ButterKnife.bind(this, v);
        setToolbar();
        setAdapter();
        getSubredditAbout();
        getSubredditList();
//        getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);

//        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if(!recyclerView.canScrollVertically(1)){
//                    getSubredditList();
//                }
//            }
//        });

        return v;
    }

    private void setAdapter(){
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
    }


    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(subredditName);
//        toolbar.setTitle(subredditName);
    }

    private void getSubredditAbout() {

        String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(getContext()) : "";
        apiInterface.getAboutSubreddit(token, subredditName).enqueue(new Callback<t5_Response>() {
            @Override
            public void onResponse(Call<t5_Response> call, Response<t5_Response> response) {
                Log.d(TAG, "getSubredditAbout onResponse");
            }

            @Override
            public void onFailure(Call<t5_Response> call, Throwable t) {
                Log.d(TAG, "getSubredditAbout onFail");
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FrontPageResponseData frontPageResponseData) {
        this.frontPageResponseData = frontPageResponseData;
        int lastPos = list.size();
        list.addAll(lastPos,frontPageResponseData.getChildren());
        adapter.notifyItemRangeInserted(lastPos,frontPageResponseData.getChildren().size());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String string) {
        if(string.equalsIgnoreCase("getNextData")){
            if(frontPageResponseData!=null)
            {
                if(!frontPageResponseData.getAfter().equalsIgnoreCase(GetSubredditListService.after))
                {
                    getSubredditList();
                }
            }
        }
    }

    private void getSubredditList() {
        String after = (frontPageResponseData!=null)?frontPageResponseData.getAfter():"";
        Intent intent = new Intent(getActivity(), GetSubredditListService.class);
        intent.putExtra("subredditName",subredditName);
        intent.putExtra("after",after);
        getContext().startService(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<FrontPageResponse> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new SubredditLoader(
                        getContext(),
                        subredditName,
                        args.getString("after")
                );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<FrontPageResponse> loader, FrontPageResponse data) {
        switch (loader.getId()) {
            case LOADER_ID:
                this.frontPageResponseData = data.getData();
                if(data!=null){
                    int lastPos = list.size();
                    list.addAll(lastPos,data.getData().getChildren());
                    adapter.notifyItemRangeInserted(lastPos,data.getData().getChildren().size());
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<FrontPageResponse> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                list.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
