package com.rahul_lohra.redditstar.fragments.Empty;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.contract.IApiCall;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;

    @OnClick(R.id.btn_retry)
    void onClickRetry(){
//        if(mApiCall!=null)
//            mApiCall.makeApiCall();
        getActivity().onBackPressed();
//        Class c = mClazzName.getClass();

    }

    private EmptyFragmentData mData;
    private @IdRes int iconId;
    private String mClazzName;

    public EmptyFragment() {
        // Required empty public constructor
    }


    public static EmptyFragment newInstance(EmptyFragmentData param1,String clazzName) {
        EmptyFragment fragment = new EmptyFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, clazzName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = getArguments().getParcelable(ARG_PARAM1);
            mClazzName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(mData.title);
        tvSubtitle.setText(mData.subtitle);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


}
