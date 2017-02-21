package com.android.rahul_lohra.redditstar.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.cursor.FavoritesAdapter;
import com.android.rahul_lohra.redditstar.helper.SimpleItemTouchHelperCallback;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int LOADER_ID = 1;
    private FavoritesAdapter adapter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavoritesAdapter(getActivity(), null);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, v);

        //setupRv
        setupRv();

        return v;
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
        ButterKnife.unbind(this);
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
                        MyFavouritesColumn.KEY_SUBREDDIT_NAME
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
