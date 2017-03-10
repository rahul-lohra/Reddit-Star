package com.android.rahul_lohra.redditstar.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;
import com.android.rahul_lohra.redditstar.adapter.normal.CommentsAdapter;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.CommentsColumn;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsFragment extends Fragment  implements
        LoaderManager.LoaderCallbacks<Cursor>
{
    private static final String ARG_LINKID = "param1";
    private static final String ARG_SUBREDDIT = "param2";
    @Bind(R.id.rv)
    RecyclerView rv;

    private String linkId;
    private String subredditId;
    CommentsAdapter commentsAdapter;

    private final int LOADER_ID_COMMENTS = 2;
    private final String BUNDLE_LINK_ID = "link_id";

    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance(String linkId, String subredditId) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LINKID, linkId);
        args.putString(ARG_SUBREDDIT, subredditId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            linkId = getArguments().getString(ARG_LINKID);
            subredditId = getArguments().getString(ARG_SUBREDDIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, v);
//        commentsAdapter = new CommentsAdapter(getActivity(), null);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(commentsAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            linkId = getArguments().getString(ARG_LINKID);
            subredditId = getArguments().getString(ARG_SUBREDDIT);
            getLoaderManager().initLoader(LOADER_ID_COMMENTS,getArguments(),this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri commentsUri = MyProvider.CommentsLists.CONTENT_URI;
        switch (id) {
//            case LOADER_ID:
//                return new CursorLoader(this,mUriReadTable,null,null,null,null);
            case LOADER_ID_COMMENTS:
            {
                String linkId = args.getString(ARG_LINKID);
                String mWhereComments = CommentsColumn.KEY_LINK_ID +"=?";
                String mWhereCommentsArgs[] = {linkId};
                String sortOrder = CommentsColumn.KEY_SQL_ID+" LIMIT 200";
                return new CursorLoader(getActivity(),commentsUri,null,mWhereComments,mWhereCommentsArgs,sortOrder);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
//            case LOADER_ID:
//                setDataInView(data);
//                break;
            case LOADER_ID_COMMENTS:
            {
                commentsAdapter.swapCursor(data);
//                if(data!=null){
//                    if(!data.moveToFirst()){
//                    }
//
//                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
//            case LOADER_ID:
//                subredditModal = null;
//                break;
            case LOADER_ID_COMMENTS:{
                commentsAdapter.swapCursor(null);
            }
        }
    }
}
