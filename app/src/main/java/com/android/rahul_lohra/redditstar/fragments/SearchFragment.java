package com.android.rahul_lohra.redditstar.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.activity.SubredditActivity;
import com.android.rahul_lohra.redditstar.adapter.cursor.HomeAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.T3_LinkSearchAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.T5_SubredditSearchAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.IFrontPageAdapter;
import com.android.rahul_lohra.redditstar.modal.T5_Kind;
import com.android.rahul_lohra.redditstar.modal.custom.AfterModal;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.modal.search.T3_ListChild;
import com.android.rahul_lohra.redditstar.modal.search.T5_ListChild;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.search.SearchLinksService;
import com.android.rahul_lohra.redditstar.service.search.SearchSubredditsService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.utility.Constants;
import com.android.rahul_lohra.redditstar.utility.UserState;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class    SearchFragment extends BaseFragment implements
        T5_SubredditSearchAdapter.IT5_SubredditSearchAdapter,
        T3_LinkSearchAdapter.IT3_LinkSearchAdapter,
        LoaderManager.LoaderCallbacks<Cursor>,
        IFrontPageAdapter {

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;
    ApiInterface apiInterface;
    boolean isUSerLoggedIn;

    private final String TAG = SearchFragment.class.getSimpleName();
    @Bind(R.id.rv_subreddits)
    RecyclerView rvSubreddits;
    @Bind(R.id.rv_links)
    RecyclerView rvLinks;
    @Bind(R.id.et)
    AppCompatEditText et;
    @Bind(R.id.nested_sv)
    NestedScrollView nestedSV;

//    T3_LinkSearchAdapter t3LinkSearchAdapter;
    HomeAdapter linkAdapter;
    T5_SubredditSearchAdapter t5SubredditSearchAdapter;
    private final int LOADER_ID = 1;

//    List<T3_Kind> t3dataList = new ArrayList<>();
    List<T5_Kind> t5dataList = new ArrayList<>();

//    T3_ListChild t3_List_child;//Link
    T5_ListChild t5_List_child;//Subreddit

    String searchQuery;
    String afterOfLink = "";
    String afterOfSubreddit;

    public interface ISearchFragment{
        void openDetailScreen(DetailPostModal modal, ImageView imageView);
    }

    private ISearchFragment mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,v);
        isUSerLoggedIn = UserState.isUserLoggedIn(getContext());
        apiInterface = (isUSerLoggedIn) ? retrofitWithToken.create(ApiInterface.class) : retrofitWithoutToken.create(ApiInterface.class);
        setAdapter();
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH: {
                        doMySearch(et.getText().toString(),true);
                        searchQuery = et.getText().toString();
                    }
                    return true;
                    default:
                        return false;
                }
            }
        });
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
//                int b = rvLinks.getBottom();
//                int h = v.getHeight();
//                int y = v.getScrollY();

                int diff = (rvLinks.getBottom() - (v.getHeight() + v.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if (diff <= 10) {
                    // do stuff
                    Log.d(TAG,"last Position");
                    getLinks(searchQuery,false);
                }
            }
        });

        return v;
    }



    private void setAdapter() {
        linkAdapter = new HomeAdapter(getActivity(),null,this);
//        t3LinkSearchAdapter = new T3_LinkSearchAdapter(getActivity(), t3dataList, retrofitWithToken,this);
        t5SubredditSearchAdapter = new T5_SubredditSearchAdapter(getActivity(), t5dataList, this);

        rvLinks.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSubreddits.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

//        rvLinks.setAdapter(t3LinkSearchAdapter);
        rvLinks.setAdapter(linkAdapter);
        rvSubreddits.setAdapter(t5SubredditSearchAdapter);
    }

    private void doMySearch(String query,boolean isFromStart) {
        /*
         TODO://isFromStart is true only from search Bar else isFromStart is false(for getting next items)
         */
        if(isFromStart)
        {
//            t3dataList.clear();
            t5dataList.clear();
//            t3LinkSearchAdapter.notifyDataSetChanged();
            Uri mUri = MyProvider.SearchLinkLists.CONTENT_URI;
            Constants.clearTable(getContext(),mUri);
            t5SubredditSearchAdapter.notifyDataSetChanged();
        }
        if (!query.isEmpty()) {
            getLinks(query,isFromStart);
            getSubreddits(query,isFromStart);
        }

    }

    private void getLinks(String query,boolean isFromStart) {
        if(afterOfLink!=null){
            Intent intentLinkService = new Intent(getActivity(), SearchLinksService.class);
            String mAfter = isFromStart?"":afterOfLink;
            intentLinkService.putExtra("after", mAfter);
            intentLinkService.putExtra("query", query);
            getActivity().startService(intentLinkService);
        }
    }

    private void getSubreddits(@NonNull String query, boolean isFromStart) {
        Intent intentSubredditService = new Intent(getActivity(), SearchSubredditsService.class);
        String after = isFromStart?"":(t5_List_child.getAfter()!=null)?t5_List_child.getAfter():"";
        intentSubredditService.putExtra("after", after);
        intentSubredditService.putExtra("query", query);
        getActivity().startService(intentSubredditService);
    }

    @Override
    public void sendData(String name,String fullName,String subredditId) {
        Intent intent = new Intent(getActivity(), SubredditActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("fullName",fullName);
        intent.putExtra("subredditId",subredditId);

        startActivity(intent);
    }

    @Override
    public void getNextSubreddit() {
        if (t5_List_child != null) {
            if (!t5_List_child.getAfter().equalsIgnoreCase(SearchSubredditsService.after)) {
                getSubreddits(searchQuery,false);
            }
        }
    }

    @Override
    public void sendLink(DetailPostModal modal, ImageView imageView) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName()).toBundle();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("modal", modal);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent, bundle);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(T5_ListChild t5_List_child) {
        this.t5_List_child = t5_List_child;
        int lastPos = t5dataList.size();
        t5dataList.addAll(lastPos, t5_List_child.children);
        t5SubredditSearchAdapter.notifyItemRangeInserted(lastPos, t5_List_child.children.size());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(AfterModal afterModal) {
        this.afterOfLink = afterModal.getmAfterLink();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(T3_ListChild t3_List_child) {
//        this.t3_List_child = t3_List_child;
//        int lastPos = t3dataList.size();
//        t3dataList.addAll(lastPos, t3_List_child.children);
//        t3LinkSearchAdapter.notifyItemRangeInserted(lastPos, t3_List_child.children.size());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String string, String type) {
        if (string.equals("getNextData") && type.equals("link")) {
//            if (t3_List_child != null) {
//                if (!t3_List_child.getAfter().equalsIgnoreCase(SearchLinksService.after)) {
//                    getLinks(searchQuery,false);
//                }
//            }
        } else if (string.equals("getNextData") && type.equals("subreddit")) {
            if (t5_List_child != null) {
                if (!t5_List_child.getAfter().equalsIgnoreCase(SearchSubredditsService.after)) {
                    getSubreddits(searchQuery,false);
                }
            }
        }
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri mUri = MyProvider.SearchLinkLists.CONTENT_URI;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),mUri,null,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                this.linkAdapter.swapCursor(data);
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_ID:
                linkAdapter.swapCursor(null);
                break;
        }
    }


    @Override
    public void sendData(DetailPostModal modal, ImageView imageView) {
        mListener.openDetailScreen(modal,imageView);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISearchFragment) {
            mListener = (ISearchFragment) context;
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


}
