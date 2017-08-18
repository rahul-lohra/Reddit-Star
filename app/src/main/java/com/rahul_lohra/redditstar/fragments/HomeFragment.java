package com.rahul_lohra.redditstar.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.Utility.CommonOperations;
import com.rahul_lohra.redditstar.Utility.MyUrl;
import com.rahul_lohra.redditstar.Utility.SpConstants;
import com.rahul_lohra.redditstar.activity.DashboardActivity;
import com.rahul_lohra.redditstar.activity.SearchActivity;
import com.rahul_lohra.redditstar.adapter.cursor.HomeAdapter;
import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.contract.IApiCall;
import com.rahul_lohra.redditstar.contract.IFrontPageAdapter;
import com.rahul_lohra.redditstar.fragments.Empty.EmptyFragment;
import com.rahul_lohra.redditstar.fragments.Empty.EmptyFragmentData;
import com.rahul_lohra.redditstar.modal.custom.AfterModal;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageResponseData;
import com.rahul_lohra.redditstar.service.GetFrontPageService;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.rahul_lohra.redditstar.Utility.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by rkrde on 05-02-2017.
 */

public class HomeFragment extends BaseFragment implements
        IFrontPageAdapter,
        IApiCall,
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    SharedPreferences sp;
    private HomeAdapter adapter;
    String mAfterOfLink = "";
    private final int LOADER_ID = 1;
    String filterParam_1 = MyUrl.Filter_Param_1.HOT, filterParam_2 = MyUrl.Filter_Param_2.DEFAULT;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    int layoutType;
    boolean pendingIntroAnimation;

    @Override
    public void makeApiCall() {
        makeApiCall(filterParam_1, filterParam_2, "");
    }

    public interface IHomeFragment {
        void sendModalAndImageView(DetailPostModal modal, ImageView imageView, String id);

        void showLoginSnackBar();

        void setSubTitle(String subtitle);

        void showIntroAnimation();
    }

    private IHomeFragment mListener;


    private void makeApiCall(@MyUrl.Filter_Param_1.FilterType String filterParam_1,
                             @MyUrl.Filter_Param_2.FilterMore String filterParam_2,
                             String afterOfLink
    ) {
        hideEmptyView();
        this.mAfterOfLink = afterOfLink;
        Intent intent = new Intent(getActivity(), GetFrontPageService.class);
        intent.putExtra("after", mAfterOfLink);
        intent.putExtra("filterParam_1", filterParam_1);
        intent.putExtra("filterParam_2", filterParam_2);
        getContext().startService(intent);
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        String mProj[] = {MyPostsColumn.KEY_ID};
        Cursor cursor = getContext().getContentResolver().query(MyProvider.PostsLists.CONTENT_URI, mProj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //Dont make api Call
                Timber.d("make api call");
            } else {
                Timber.d("make api call");
                Constants.clearPosts(getContext(), Constants.TYPE_POST);
                Constants.clearComments(getContext());
                makeApiCall(filterParam_1, filterParam_2, mAfterOfLink);
            }
            cursor.close();
        }
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        layoutType = sp.getInt(SpConstants.LAYOUT_TYPE, HomeAdapter.DEFAULT);
        if(null == savedInstanceState){
            pendingIntroAnimation = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        setAdapter();
//        makeApiCall();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EmptyFragment myFragment = (EmptyFragment) getActivity().getSupportFragmentManager().findFragmentByTag(EmptyFragment.class.getSimpleName());
                if (myFragment != null && myFragment.isVisible()) {
                    // add your code here
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .remove(myFragment)
                            .commit();
                }
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                CountDownTimer countDownTimer = new CountDownTimer(7000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        if (swipeRefresh != null) {
                            if (swipeRefresh.isRefreshing()) {
                                swipeRefresh.setRefreshing(false);
                            }
                        }
                    }
                };
                countDownTimer.start();
            }
        });

        // Configure the refreshing colors
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;
    }

    private void setAdapter() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(2);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new HomeAdapter(getActivity(), null, this, (DashboardActivity) getActivity());
        switch (layoutType) {
            case HomeAdapter.GALLERY:
                adapter.setLayoutManagerType(HomeAdapter.GALLERY);
                rv.setLayoutManager(staggeredGridLayoutManager);
                break;
            case HomeAdapter.LIST:
                adapter.setLayoutManagerType(HomeAdapter.LIST);
                rv.setLayoutManager(linearLayoutManager);
                break;
            default:
            case HomeAdapter.DEFAULT:
                adapter.setLayoutManagerType(HomeAdapter.DEFAULT);
                rv.setLayoutManager(linearLayoutManager);
                break;

        }

        rv.setAdapter(adapter);
        rv.setHasFixedSize(false);
    }

    private void showEmptyView() {
        if (adapter.getItemCount() < 1) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, EmptyFragment.newInstance(new EmptyFragmentData("error", "error", 0), ""), EmptyFragment.class.getSimpleName())
                    .commit();

        }
    }

    private void hideEmptyView() {
        EmptyFragment myFragment = (EmptyFragment) getActivity().getSupportFragmentManager().findFragmentByTag(EmptyFragment.class.getSimpleName());
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(myFragment)
                    .commit();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String string) {
        if (string.equalsIgnoreCase("getNextData")) {
            makeApiCall(filterParam_1, filterParam_2, mAfterOfLink);
        } else if (string.equalsIgnoreCase("error")) {
            showEmptyView();
            swipeRefresh.setRefreshing(false);

        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(AfterModal afterModal) {
        this.mAfterOfLink = afterModal.getmAfterLink();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        if(pendingIntroAnimation){
            pendingIntroAnimation = false;
             mListener.showIntroAnimation();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
//                getActivity().onSearchRequested();
                startActivity(new Intent(getActivity(), SearchActivity.class));
                return true;
            case R.id.action_change_view:
                return true;
            case R.id.action_list:
                sp.edit().putInt(SpConstants.LAYOUT_TYPE, HomeAdapter.LIST).apply();
                adapter.setLayoutManagerType(HomeAdapter.LIST);
                rv.setLayoutManager(linearLayoutManager);
                rv.setAdapter(adapter);
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.action_cards:
                return true;
            case R.id.action_gallery:
                sp.edit().putInt(SpConstants.LAYOUT_TYPE, HomeAdapter.GALLERY).apply();
                adapter.setLayoutManagerType(HomeAdapter.GALLERY);
                rv.setLayoutManager(staggeredGridLayoutManager);
                rv.setAdapter(adapter);
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.action_hot:
                mListener.setSubTitle(getString(R.string.hot));
                filterParam_1 = MyUrl.Filter_Param_1.HOT;
                filterParam_2 = MyUrl.Filter_Param_2.DEFAULT;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.action_new:
                mListener.setSubTitle(getString(R.string._new));
                filterParam_1 = MyUrl.Filter_Param_1.NEW;
                filterParam_2 = MyUrl.Filter_Param_2.DEFAULT;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.action_rising:
                mListener.setSubTitle(getString(R.string.rising));
                filterParam_1 = MyUrl.Filter_Param_1.RISING;
                filterParam_2 = MyUrl.Filter_Param_2.DEFAULT;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.cont_action_hour:
                mListener.setSubTitle(getString(R.string.hour));
                filterParam_1 = MyUrl.Filter_Param_1.CONTROVERSIAL;
                filterParam_2 = MyUrl.Filter_Param_2.HOUR;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.cont_action_day:
                mListener.setSubTitle(getString(R.string.day));
                filterParam_1 = MyUrl.Filter_Param_1.CONTROVERSIAL;
                filterParam_2 = MyUrl.Filter_Param_2.DAY;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.cont_action_week:
                mListener.setSubTitle(getString(R.string.week));
                filterParam_1 = MyUrl.Filter_Param_1.CONTROVERSIAL;
                filterParam_2 = MyUrl.Filter_Param_2.WEEK;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.cont_action_month:
                mListener.setSubTitle(getString(R.string.month));
                filterParam_1 = MyUrl.Filter_Param_1.CONTROVERSIAL;
                filterParam_2 = MyUrl.Filter_Param_2.MONTH;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.cont_action_year:
                mListener.setSubTitle(getString(R.string.year));
                filterParam_1 = MyUrl.Filter_Param_1.CONTROVERSIAL;
                filterParam_2 = MyUrl.Filter_Param_2.YEAR;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.cont_action_all_time:
                mListener.setSubTitle(getString(R.string.all_time));
                filterParam_1 = MyUrl.Filter_Param_1.CONTROVERSIAL;
                filterParam_2 = MyUrl.Filter_Param_2.ALL_TIME;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.top_action_hour:
                mListener.setSubTitle(getString(R.string.hour));
                filterParam_1 = MyUrl.Filter_Param_1.TOP;
                filterParam_2 = MyUrl.Filter_Param_2.HOUR;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.top_action_day:
                mListener.setSubTitle(getString(R.string.day));
                filterParam_1 = MyUrl.Filter_Param_1.TOP;
                filterParam_2 = MyUrl.Filter_Param_2.DAY;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.top_action_week:
                mListener.setSubTitle(getString(R.string.week));
                filterParam_1 = MyUrl.Filter_Param_1.TOP;
                filterParam_2 = MyUrl.Filter_Param_2.WEEK;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.top_action_month:
                mListener.setSubTitle(getString(R.string.month));
                filterParam_1 = MyUrl.Filter_Param_1.TOP;
                filterParam_2 = MyUrl.Filter_Param_2.MONTH;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.top_action_year:
                filterParam_1 = MyUrl.Filter_Param_1.TOP;
                mListener.setSubTitle(getString(R.string.year));
                filterParam_2 = MyUrl.Filter_Param_2.YEAR;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;
            case R.id.top_action_all_time:
                filterParam_1 = MyUrl.Filter_Param_1.TOP;
                mListener.setSubTitle(getString(R.string.all_time));
                filterParam_2 = MyUrl.Filter_Param_2.ALL_TIME;
                deleteArticlesAndComments();
                makeApiCall(filterParam_1, filterParam_2, "");
                return true;


            default:
                return false;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        layoutType = sp.getInt(SpConstants.LAYOUT_TYPE, HomeAdapter.DEFAULT);
        switch (layoutType) {
            case HomeAdapter.GALLERY:
                menu.findItem(R.id.action_gallery).setChecked(true);
                break;
            default:
            case HomeAdapter.LIST:
                menu.findItem(R.id.action_list).setChecked(true);
                break;

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IHomeFragment) {
            mListener = (IHomeFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Timber.d("onCreateLoader");
        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
        String mWhere = MyPostsColumn.TYPE_POST + "=?";
        String mWhereArgs[] = {"1"};
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(), mUri, null, mWhere, mWhereArgs, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.d("onLoadFinished");
        switch (loader.getId()) {
            case LOADER_ID:
                this.adapter.swapCursor(data);
                if (data != null) {
                    if (data.moveToFirst()) {
                        swipeRefresh.setRefreshing(false);
                        hideEmptyView();
                    }

                }

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Timber.d("onLoadFinished");
        switch (loader.getId()) {
            case LOADER_ID:
                adapter.swapCursor(null);
                break;
        }
    }

}
