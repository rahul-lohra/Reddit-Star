package com.android.rahul_lohra.redditstar.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.rahul_lohra.redditstar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsFragment extends Fragment {
    private static final String ARG_LINKID = "param1";
    private static final String ARG_SUBREDDIT = "param2";
    @Bind(R.id.rv)
    RecyclerView rv;

    private String linkId;
    private String subredditId;

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
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            linkId = getArguments().getString(ARG_LINKID);
            subredditId = getArguments().getString(ARG_SUBREDDIT);
//            getLoaderManager().initLoader(LOADER_ID_COMMENTS,bundle,this);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
