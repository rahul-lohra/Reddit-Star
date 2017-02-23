package com.android.rahul_lohra.redditstar.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.T3_LinkSearchAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.T5_SubredditSearchAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.modal.T3_Kind;
import com.android.rahul_lohra.redditstar.modal.T5_Kind;
import com.android.rahul_lohra.redditstar.modal.search.T3_ListChild;
import com.android.rahul_lohra.redditstar.modal.search.T5_ListChild;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.search.SearchLinksService;
import com.android.rahul_lohra.redditstar.service.search.SearchSubredditsService;
import com.android.rahul_lohra.redditstar.storage.MySuggestionProvider;
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

public class SearchActivity extends AppCompatActivity {


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

    T3_ListChild t3_List_child;
    T5_ListChild t5_List_child;

    String searchQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_new);
        ButterKnife.bind(this);
        ((Initializer) getApplication()).getNetComponent().inject(this);

        isUSerLoggedIn = UserState.isUserLoggedIn(getApplicationContext());
        apiInterface = (isUSerLoggedIn) ? retrofitWithToken.create(ApiInterface.class) : retrofitWithoutToken.create(ApiInterface.class);

//        handleIntent(getIntent());

        setAdapter();

        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:{
                        doMySearch(et.getText().toString());
                    }
                        return true;

                    default:return false;
                }

            }
        });

    }

    private void setAdapter() {
        t3LinkSearchAdapter = new T3_LinkSearchAdapter(this, t3dataList, retrofitWithToken);
        t5SubredditSearchAdapter = new T5_SubredditSearchAdapter(this, t5dataList);

        rvLinks.setLayoutManager(new LinearLayoutManager(this));
        rvSubreddits.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rvLinks.setAdapter(t3LinkSearchAdapter);
        rvSubreddits.setAdapter(t5SubredditSearchAdapter);
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


//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//        handleIntent(intent);
//    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchQuery = query;
            //save Query
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            //Perform Search
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        if(!query.isEmpty()){

            getLinks(query);
            getSubreddits(query);
        }

    }

    private void getLinks(String query) {
        Intent intentLinkService = new Intent(this, SearchLinksService.class);
        if (t3_List_child != null) {
            intentLinkService.putExtra("after", t3_List_child.getAfter());
        } else {
            intentLinkService.putExtra("after", "");
        }
        intentLinkService.putExtra("query", query);
        startService(intentLinkService);
    }

    private void getSubreddits(String query) {
        Intent intentSubredditService = new Intent(this, SearchSubredditsService.class);
        if (t5_List_child != null) {
            intentSubredditService.putExtra("after", t5_List_child.getAfter());
        } else {
            intentSubredditService.putExtra("after", "");
        }
        intentSubredditService.putExtra("query", query);
        startService(intentSubredditService);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the options menu from XML
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//
////         Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();//.getActionView();
//        // Assumes current activity is the searchable activity
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
////        searchView.setOnQueryTextListener(this);
//
//        return true;
//    }

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
