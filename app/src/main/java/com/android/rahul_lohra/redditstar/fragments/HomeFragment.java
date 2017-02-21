package com.android.rahul_lohra.redditstar.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.FrontPageAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.helper.AwesomeSearchView;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponseData;
import com.android.rahul_lohra.redditstar.retrofit.ApiInterface;
import com.android.rahul_lohra.redditstar.service.GetFrontPageService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * Created by rkrde on 05-02-2017.
 */

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FrontPageResponseData frontPageResponseData = null;
    @Bind(R.id.rv)
    RecyclerView rv;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;

    FrontPageAdapter adapter;
    List<FrontPageChild> list;


    void makeApiCall(){
        Intent intent = new Intent(getActivity(), GetFrontPageService.class);
        if(frontPageResponseData!=null){
            intent.putExtra("after",frontPageResponseData.getAfter());
        }else {
            intent.putExtra("after","");
        }
        getContext().startService(intent);
    }


    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        ((Initializer) getContext().getApplicationContext()).getNetComponent().inject(this);
        list = new ArrayList<>();
        adapter = new FrontPageAdapter(HomeFragment.this,getActivity().getApplicationContext(), list,retrofitWithToken);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        setAdapter();
        makeApiCall();
        return v;
    }

    private void setAdapter(){
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
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
                if(!frontPageResponseData.getAfter().equalsIgnoreCase(GetFrontPageService.after))
                {
                    makeApiCall();
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        AwesomeSearchView searchView = (AwesomeSearchView) menu.findItem(R.id.action_search).getActionView();//.getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                getActivity().onSearchRequested();
                return true;
            default:return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
