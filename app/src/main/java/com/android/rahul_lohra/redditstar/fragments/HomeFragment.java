package com.android.rahul_lohra.redditstar.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.activity.SearchActivity;
import com.android.rahul_lohra.redditstar.adapter.cursor.HomeAdapter;
import com.android.rahul_lohra.redditstar.adapter.normal.FrontPageAdapter;
import com.android.rahul_lohra.redditstar.application.Initializer;
import com.android.rahul_lohra.redditstar.contract.IFrontPageAdapter;
import com.android.rahul_lohra.redditstar.loader.CommentsLoader;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponseData;
import com.android.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.android.rahul_lohra.redditstar.service.GetFrontPageService;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;

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

/**
 * Created by rkrde on 05-02-2017.
 */

public class HomeFragment extends Fragment implements
        IFrontPageAdapter,
        LoaderManager.LoaderCallbacks<Cursor>{

    private FrontPageResponseData frontPageResponseData = null;
    @Bind(R.id.rv)
    RecyclerView rv;

    @Inject
    @Named("withToken")
    Retrofit retrofitWithToken;
//    FrontPageAdapter adapter;
    List<FrontPageChild> list;
    private HomeAdapter adapter;

    private final int LOADER_ID = 1;



    public interface IHomeFragment{
        void sendModalAndImageView(DetailPostModal modal, ImageView imageView,String id);
        void showLoginSnackBar();
    }

    private IHomeFragment mListener;


    void makeApiCall(){
        Intent intent = new Intent(getActivity(), GetFrontPageService.class);
        if(frontPageResponseData!=null){
            intent.putExtra("after",frontPageResponseData.getAfter());
        }else {
            intent.putExtra("after","");
        }
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
        list = new ArrayList<>();
//        adapter = new FrontPageAdapter(HomeFragment.this,getActivity().getApplicationContext(), list,retrofitWithToken,this);

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
        adapter = new HomeAdapter(getActivity(),null,this);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
//        rv.nested
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FrontPageResponseData frontPageResponseData) {
//        this.frontPageResponseData = frontPageResponseData;
//        int lastPos = list.size();
//        list.addAll(lastPos,frontPageResponseData.getChildren());
//        adapter.notifyItemRangeInserted(lastPos,frontPageResponseData.getChildren().size());
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
//
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();//.getActionView();
//        // Assumes current activity is the searchable activity
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setIconifiedByDefault(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
//                getActivity().onSearchRequested();
                startActivity(new Intent(getActivity(), SearchActivity.class));
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
    public void sendData(DetailPostModal modal, ImageView imageView,String id) {
        mListener.sendModalAndImageView(modal,imageView,id);
    }

    @Override
    public void pleaseLogin() {
        mListener.showLoginSnackBar();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
           getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
//        String mProjection[]=null;
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
                this.adapter.swapCursor(data);
                break;
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
}
