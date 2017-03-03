package com.android.rahul_lohra.redditstar.activity;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.T3_LinkSearchAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.T5_SubredditSearchAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.fragments.SearchFragment;
import com.android.rahul_lohra.redditstar.modal.T3_Kind;
import com.android.rahul_lohra.redditstar.modal.T5_Kind;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.modal.search.T3_ListChild;
import com.android.rahul_lohra.redditstar.modal.search.T5_ListChild;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.search.SearchLinksService;
import com.android.rahul_lohra.redditstar.service.search.SearchSubredditsService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
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

public class SearchActivity extends BaseActivity implements SearchFragment.ISearchFragment

{
    private Uri uri = MyProvider.SearchLinkLists.CONTENT_URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_new);
        if(savedInstanceState==null){
            showSearchFragment();
        }
    }

    private void showSearchFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, SearchFragment.newInstance(), SearchFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void openDetailScreen(DetailPostModal modal, ImageView imageView,String id) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, imageView, imageView.getTransitionName()).toBundle();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("uri",uri);
        intent.putExtra("modal",modal);

        startActivity(intent, bundle);
    }

}
