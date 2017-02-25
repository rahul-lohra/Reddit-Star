package com.android.rahul_lohra.redditstar.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.DetailActivity;
import com.android.rahul_lohra.redditstar.activity.SearchActivity;
import com.android.rahul_lohra.redditstar.activity.SubredditActivity;
import com.android.rahul_lohra.redditstar.adapter.normal.T3_LinkSearchAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.T5_SubredditSearchAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.T3_Kind;
import com.android.rahul_lohra.redditstar.modal.T5_Kind;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.modal.search.T3_ListChild;
import com.android.rahul_lohra.redditstar.modal.search.T5_ListChild;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.search.SearchLinksService;
import com.android.rahul_lohra.redditstar.service.search.SearchSubredditsService;
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
import retrofit2.Retrofit;

public class SearchFragment extends Fragment implements
        T5_SubredditSearchAdapter.IT5_SubredditSearchAdapter,
        T3_LinkSearchAdapter.IT3_LinkSearchAdapter{

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    @Inject
    @Named("withoutToken")
    Retrofit retrofitWithoutToken;
    ApiInterface apiInterface;
    boolean isUSerLoggedIn;

    private final String TAG = SearchActivity.class.getSimpleName();
    @Bind(R.id.rv_subreddits)
    RecyclerView rvSubreddits;
    @Bind(R.id.rv_links)
    RecyclerView rvLinks;
    @Bind(R.id.et)
    AppCompatEditText et;
    T3_LinkSearchAdapter t3LinkSearchAdapter;
    T5_SubredditSearchAdapter t5SubredditSearchAdapter;

    List<T3_Kind> t3dataList = new ArrayList<>();
    List<T5_Kind> t5dataList = new ArrayList<>();

    T3_ListChild t3_List_child;//Link
    T5_ListChild t5_List_child;//Subreddit

    String searchQuery;



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
                        doMySearch(et.getText().toString());
                    }
                    return true;

                    default:
                        return false;
                }

            }
        });

        return v;
    }

    private void setAdapter() {
        t3LinkSearchAdapter = new T3_LinkSearchAdapter(getActivity(), t3dataList, retrofitWithToken,this);
        t5SubredditSearchAdapter = new T5_SubredditSearchAdapter(getActivity(), t5dataList, this);

        rvLinks.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSubreddits.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvLinks.setAdapter(t3LinkSearchAdapter);
        rvSubreddits.setAdapter(t5SubredditSearchAdapter);
    }

    private void doMySearch(String query) {
        if (!query.isEmpty()) {

            getLinks(query);
            getSubreddits(query);
        }

    }

    private void getLinks(String query) {
        Intent intentLinkService = new Intent(getActivity(), SearchLinksService.class);
        if (t3_List_child != null) {
            intentLinkService.putExtra("after", t3_List_child.getAfter());
        } else {
            intentLinkService.putExtra("after", "");
        }
        intentLinkService.putExtra("query", query);
        getActivity().startService(intentLinkService);
    }

    private void getSubreddits(String query) {
        Intent intentSubredditService = new Intent(getActivity(), SearchSubredditsService.class);
        if (t5_List_child != null) {
            intentSubredditService.putExtra("after", t5_List_child.getAfter());
        } else {
            intentSubredditService.putExtra("after", "");
        }
        intentSubredditService.putExtra("query", query);
        getActivity().startService(intentSubredditService);
    }

    @Override
    public void sendData(String name,String fullName) {
        Intent intent = new Intent(getActivity(), SubredditActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("fullName",fullName);
        startActivity(intent);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(T3_ListChild t3_List_child) {
        this.t3_List_child = t3_List_child;
        int lastPos = t3dataList.size();
        t3dataList.addAll(lastPos, t3_List_child.children);
        t3LinkSearchAdapter.notifyItemRangeInserted(lastPos, t3_List_child.children.size());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String string, String type) {
        if (string.equals("getNextData") && type.equals("link")) {
            if (t3_List_child != null) {
                if (!t3_List_child.getAfter().equalsIgnoreCase(SearchLinksService.after)) {
                    getLinks(searchQuery);
                }
            }
        } else if (string.equals("getNextData") && type.equals("subreddit")) {
            if (t5_List_child != null) {
                if (!t5_List_child.getAfter().equalsIgnoreCase(SearchSubredditsService.after)) {
                    getSubreddits(searchQuery);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
