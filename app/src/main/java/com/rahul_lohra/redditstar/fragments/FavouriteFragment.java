package com.rahul_lohra.redditstar.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.cursor.FavoritesAdapter;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        FavoritesAdapter.IFavoritesAdapter
{

    private final int LOADER_ID = 1;
    private FavoritesAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty_view)
    AppCompatTextView tvEmptyView;
    public FavouriteFragment() {
        // Required empty public constructor
    }

    private List<String> mSubredditIdList = new ArrayList<>();

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, v);
        adapter = new FavoritesAdapter(getActivity(), null,this);
        setToolbar();
        //setupRv
        setupRv();

        return v;
    }

    private void setToolbar(){
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setupRv() {
//        ItemTouchHelper.Callback callback =
//                new SimpleItemTouchHelperCallback(adapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case LOADER_ID:
                Uri uri = MyProvider.FavoritesLists.CONTENT_URI;
                String mProjection[] = {
                        MyFavouritesColumn.KEY_SQL_ID,
                        MyFavouritesColumn.KEY_RANK,
                        MyFavouritesColumn.KEY_SUBREDDIT_ID,
                        MyFavouritesColumn.KEY_DISPLAY_NAME,
                        MyFavouritesColumn.KEY_FULL_NAME
                };
                String sortOrder = MyFavouritesColumn.KEY_RANK;
                return new CursorLoader(getActivity(), uri, mProjection, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                this.adapter.swapCursor(data);
                if(data!=null)
                {
                    if(data.moveToFirst()){
                        tvEmptyView.setVisibility(View.GONE);
                    }

                }
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

    @Override
    public void showEmptyView(boolean val) {
//        if(val){
//            tvEmptyView.setVisibility(View.VISIBLE);
//        }else {
//            tvEmptyView.setVisibility(View.GONE);
//        }
    }

    @Override
    public void insertIntoList(String subredditId) {
        mSubredditIdList.add(subredditId);
    }

    @Override
    public void removeFromList( String subredditId) {
        mSubredditIdList.remove(subredditId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fav, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
            {
                updateFavorites();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateFavorites(){
        Uri mUri = MyProvider.FavoritesLists.CONTENT_URI;
        String mWhere  = MyFavouritesColumn.KEY_SUBREDDIT_ID+" =?";
        if(mSubredditIdList.size()>0){
            for(String subredditId: mSubredditIdList){
                String mWhereArgs[]={subredditId};
                getContext().getContentResolver().delete(mUri,mWhere,mWhereArgs);
            }
            mSubredditIdList.clear();
        }else {
            getActivity().onBackPressed();
        }
    }

}
