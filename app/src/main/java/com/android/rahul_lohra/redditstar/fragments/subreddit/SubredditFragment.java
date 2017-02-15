package com.android.rahul_lohra.redditstar.fragments.subreddit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.fragmentState.SubredditFragmentAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.t5.t5_Response;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.utility.UserState;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubredditFragment extends Fragment {
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


    SubredditFragmentAdapter fragmentAdapter;
    @Bind(R.id.app_bar_image)
    ImageView appBarImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;

    private String subredditName;
    private String mParam2;

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
        retrofit = (isUserLoggedIn = UserState.isUserLoggedIn(getContext()))?retrofitWithToken:retrofitWithoutToken;
        apiInterface = retrofit.create(ApiInterface.class);
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

         getSubredditAbout();
        return v;
    }

    private void getSubredditAbout(){

        String token = (isUserLoggedIn)?"bearer "+UserState.getAuthToken(getContext()):"";

        apiInterface.getAboutSubreddit(token,subredditName).enqueue(new Callback<t5_Response>() {
            @Override
            public void onResponse(Call<t5_Response> call, Response<t5_Response> response) {
                Log.d(TAG,"getSubredditAbout onResponse");
            }

            @Override
            public void onFailure(Call<t5_Response> call, Throwable t) {
                Log.d(TAG,"getSubredditAbout onFail");
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
