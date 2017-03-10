package com.android.rahul_lohra.redditstar.fragments.subreddit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.SubredditActivity;
import com.android.rahul_lohra.redditstar.adapter.cursor.HomeAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.IFrontPageAdapter;
import com.android.rahul_lohra.redditstar.modal.FavoritesModal;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponseData;
import com.android.rahul_lohra.redditstar.modal.t5_Subreddit.T5_Data;
import com.android.rahul_lohra.redditstar.modal.t5_Subreddit.t5_Response;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.GetSubredditListService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.UserState;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubredditFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        IFrontPageAdapter

{
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
    private static final String ARG_PARAM3 = "param3";

    private static final String TAG = SubredditFragment.class.getSimpleName();
    private final int LOADER_ID = 1;
    private final int LOADER_ID_FAV = 2;



    //    @Bind(R.id.app_bar_image)
//    ImageView appBarImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;

    List<FrontPageChild> list = new ArrayList<>();
    @Bind(R.id.spark_subs)
    SparkButton sparkSubs;
    @Bind(R.id.spark_fav)
    SparkButton sparkFav;
    @Bind(R.id.nested_sv)
    NestedScrollView nestedSV;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.tv_subreddit)
    AppCompatTextView tvSubreddit;
    @Bind(R.id.tv_subs_count)
    AppCompatTextView tvSubsCount;
    @Bind(R.id.tv_detail)
    AppCompatTextView tvDetail;

    private String subredditName;
    private String subredditFullName;
    private String subredditId;
    private HomeAdapter adapter;
    private FrontPageResponseData frontPageResponseData;

    public interface ISubredditFragment {
        void sendModalAndImageView(DetailPostModal modal, ImageView imageView, String id);
    }

    private ISubredditFragment mListener;


    public SubredditFragment() {
        // Required empty public constructor
    }

    public static SubredditFragment newInstance(String subredditNameParam, String subredditFullName, String subredditId) {
        SubredditFragment fragment = new SubredditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, subredditNameParam);
        args.putString(ARG_PARAM2, subredditFullName);
        args.putString(ARG_PARAM3, subredditId);

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
//        adapter = new FrontPageAdapter(SubredditFragment.this, getActivity().getApplicationContext(), list, retrofit, this);

        if (getArguments() != null) {
            subredditName = getArguments().getString(ARG_PARAM1);
            subredditFullName = getArguments().getString(ARG_PARAM2);
            subredditId = getArguments().getString(ARG_PARAM3);
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

        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (rv.getBottom() - (v.getHeight() + v.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if (diff <= 10) {
                    // do stuff
                    Log.d(TAG, "last Position");
                    fetchNextItems();
                }
            }
        });


        sparkFav.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

                if (buttonState) {
                    //insert
                    FavoritesModal favoritesModal = new FavoritesModal(subredditName, subredditFullName, subredditId);
                    Constants.insertIntoFavoritesDb(getContext(), favoritesModal);
                } else {
                    //remove
                    Constants.deleteFromFavoritesDb(getContext(), subredditFullName);
                }
            }
        });
        sparkSubs.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                boolean isLggedIn = UserState.isUserLoggedIn(getActivity());
                if (isLggedIn) {
                    String subVal = (buttonState) ? "sub" : "unsub";
                    updateSubscription(subVal);
                } else {
                    sparkSubs.setChecked(isLggedIn);
                    Toast.makeText(getActivity(), getString(R.string.please_login), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);
        return v;
    }


    private void updateSubscription(final String subVal) {
        ApiInterface mApiInterface = retrofitWithToken.create(ApiInterface.class);
        String token = "bearer " + UserState.getAuthToken(getContext());
        mApiInterface.subscribeSubreddit_new(token, subVal, false, subredditFullName)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "Success responseCode:" + response.code());
                        if (response.code() == 200) {
                            sparkSubs.setChecked((subVal.equals("sub")));
                            //remove from db
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "fail:" + t.getMessage());
                    }
                });
    }

    private void setAdapter() {
        adapter = new HomeAdapter(getActivity(), null, this,(SubredditActivity)getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
    }


    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_black);


//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("r/"+subredditName);

        tvSubreddit.setText("r/"+subredditName);
//        String subsCount =

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
//            toolbar.setTitle(subredditName);
//            toolbar.setSubtitle("Hot");
//                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(subredditName);
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Hot");
//        }

//        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbar.setTitle("Title");
//                    toolbar.setTitle(subredditName);
//                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("haha");
//
//                    isShow = true;
//                } else if(isShow) {
//                    collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
//                    isShow = false;
//                }
//            }
//        });
    }

    private void getSubredditAbout() {

        String token = (isUserLoggedIn) ? "bearer " + UserState.getAuthToken(getContext()) : "";
        apiInterface.getAboutSubreddit(token, subredditName).enqueue(new Callback<t5_Response>() {
            @Override
            public void onResponse(Call<t5_Response> call, Response<t5_Response> response) {
                Log.d(TAG, "getSubredditAbout onResponse");
                if (response.code() == 200) {
                    T5_Data t5_data = response.body().getData();
                    String accountsActive = String.valueOf(t5_data.getAccountsActive());
                    String totalSubscriber = String.valueOf(t5_data.getSubscribers());
                    String detail = String.valueOf(t5_data.getTitle());

                    String str = String.valueOf(totalSubscriber)+" subscribers . "+String.valueOf(accountsActive)+" Online";
                    tvSubsCount.setText(str);
                    tvDetail.setText(detail);
                    sparkSubs.setChecked((t5_data.getUserIsSubscriber() != null) ? (t5_data.getUserIsSubscriber()) : (false));

                }
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
        list.addAll(lastPos, frontPageResponseData.getChildren());
        adapter.notifyItemRangeInserted(lastPos, frontPageResponseData.getChildren().size());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String string) {
//        if (string.equals("getNextData")) {
//            if (frontPageResponseData != null) {
//                if (!frontPageResponseData.getAfter().equalsIgnoreCase(GetSubredditListService.after)) {
//                    getSubredditList();
//                }
//            }
//        }
    }

    private void getSubredditList() {
        String after = (frontPageResponseData != null) ? frontPageResponseData.getAfter() : "";
        Intent intent = new Intent(getActivity(), GetSubredditListService.class);
        intent.putExtra("subredditName", subredditName);
        intent.putExtra("after", after);
        getContext().startService(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        switch (id) {
            case LOADER_ID:
                String subredditId = args.getString(ARG_PARAM2);
                Uri mUri = MyProvider.PostsLists.CONTENT_URI;
                String mWhere = MyPostsColumn.TYPE_TEMP + " =? "+" AND "+MyPostsColumn.KEY_SUBREDDIT_ID+" =? ";
                String mWhereArgs[] = {"1",subredditId};
                return new CursorLoader(getActivity(), mUri, null, mWhere, mWhereArgs, null);

            case LOADER_ID_FAV:
                String subredditIdFav = args.getString(ARG_PARAM2);
                Uri mUriFav = MyProvider.FavoritesLists.CONTENT_URI;
                String mWhereFav = MyFavouritesColumn.KEY_SUBREDDIT_ID +" =?";
                        if(null ==subredditIdFav)
                            return null;
                String mWhereArgsFav[] = {subredditIdFav};
                return new CursorLoader(getActivity(), mUriFav, null, mWhereFav, mWhereArgsFav, null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                this.adapter.swapCursor(data);
                break;
            case LOADER_ID_FAV:
                if(data!=null && data.moveToFirst()){
                    sparkFav.setChecked(true);
                    data.close();
                }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                adapter.swapCursor(null);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISubredditFragment) {
            mListener = (ISubredditFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, subredditFullName);
        getLoaderManager().initLoader(LOADER_ID, args, this);
        getLoaderManager().initLoader(LOADER_ID_FAV, args, this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void sendData(DetailPostModal modal, ImageView imageView, String id) {
        mListener.sendModalAndImageView(modal, imageView, id);
    }


    private void fetchNextItems() {
        if (frontPageResponseData != null) {
            if (!frontPageResponseData.getAfter().equalsIgnoreCase(GetSubredditListService.after)) {
                getSubredditList();
            }
        }
    }

}
