package com.rahul_lohra.redditstar.helper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.cursor.SearchAdapter;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.MySuggestionProvider;
import com.rahul_lohra.redditstar.storage.column.SuggestionColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rkrde on 25-03-2017.
 */

public class MySearchView extends Toolbar implements
        SearchAdapter.ISearchAdapter,
        Animator.AnimatorListener
{

    AppCompatImageView imageBack;
    AppCompatEditText et;
    RecyclerView rv;
    AppCompatImageView imageClose;
    AppCompatImageView imageMic;
    AppCompatImageView imageOptions;

    private Context mContext;
    private String mSearchQuery;
    private SearchAdapter searchAdapter;
    private Uri mSuggestionsUri = MyProvider.SuggestionLists.CONTENT_URI;
    private String mProjection[] = {SuggestionColumn.KEY_SUGGESTION};
    private String mWhere = SuggestionColumn.KEY_SUGGESTION + " LIKE ?";
    private ObjectAnimator fadeOut = ObjectAnimator.ofFloat(this, "alpha", 0f);
    private ObjectAnimator fadeIn = ObjectAnimator.ofFloat(this, "alpha", 1f);
    private ISearchView mListener;
    List<String> stringList = new ArrayList<>();

    @Override
    public void onRvTextViewClick(String string) {
        mListener.doMySearch(string, true);
        stringList.clear();
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        imageClose.setVisibility(GONE);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    public interface ISearchView {
        void doMySearch(String query, boolean isFromStart);
        void goBack();
    }

    public MySearchView(Context context) {
        super(context);
        this.mContext = context;
    }

    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void init(ISearchView iSearchView) {
        this.mListener = iSearchView;
    }

    private void init() {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.my_search_view, this, true);
        imageBack = (AppCompatImageView) view.findViewById(R.id.image_back);
        imageClose = (AppCompatImageView) view.findViewById(R.id.image_cancel);
        imageMic = (AppCompatImageView) view.findViewById(R.id.image_mic);
        imageOptions = (AppCompatImageView) view.findViewById(R.id.image_options);
        et = (AppCompatEditText) view.findViewById(R.id.et);
        setAnimProperty();

        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(mContext, stringList, this);
        rv.setAdapter(searchAdapter);
        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBack();
            }
        });
        fadeOut.addListener(this);
        fadeOut.setTarget(imageClose);
        showInitialSuggestion();

        imageClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                et.setText("");
                stringList.clear();
                searchAdapter.notifyDataSetChanged();
                fadeOut.start();
            }
        });
        imageMic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(mContext,imageMic);
            }
        });
        imageOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(mContext,imageOptions);
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 1) {
                    imageClose.setVisibility(VISIBLE);
                    fadeIn.setTarget(imageClose);
                    fadeIn.start();
                    String mSelectionArgs[] = {"%" + editable.toString() + "%"};
                    showSuggestions(mSelectionArgs,mWhere);
                }else {
                    fadeOut.start();
                    stringList.clear();
                    searchAdapter.notifyDataSetChanged();
                }
//                searchAdapter.notifyDataSetChanged();
            }
        });

        et.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH: {
                        if (!et.getText().toString().isEmpty()) {
                            mSearchQuery = et.getText().toString();
                            mListener.doMySearch(et.getText().toString(), true);
                            String mSelectionArgs[] = {mSearchQuery};
                            String mSelection = SuggestionColumn.KEY_SUGGESTION + " =?";
                            saveSuggestion(mSelectionArgs,mSelection);
                        }
                        stringList.clear();
                        searchAdapter.notifyDataSetChanged();
                        hideKeyboardFrom(mContext,et);
                    }
                    return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void showSuggestions(final String mSelectionArgs[],final String mSelection){
        stringList.clear();
        Cursor mCursor = mContext.getContentResolver().query(mSuggestionsUri, mProjection,mSelection, mSelectionArgs, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String suggestion = mCursor.getString(mCursor.getColumnIndex(SuggestionColumn.KEY_SUGGESTION));
                    stringList.add(suggestion);
                } while (mCursor.moveToNext());
                mCursor.close();
            }
        }
        searchAdapter.notifyDataSetChanged();
    }

    private void showInitialSuggestion(){
        showSuggestions(null,null);
    }

    private void saveSuggestion(final String mSelectionArgs[],final String mSelection){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cur = mContext.getContentResolver().query(mSuggestionsUri, mProjection, mSelection, mSelectionArgs, null);
                if (cur != null) {
                    if (!cur.moveToFirst()) {
                        ContentValues cv = new ContentValues();
                        cv.put(SuggestionColumn.KEY_SUGGESTION, mSearchQuery);
                        mContext.getContentResolver().insert(mSuggestionsUri, cv);
                    }
                    cur.close();
                }
            }
        }).start();
    }

    private void setAnimProperty() {
        fadeOut.setDuration(200);
        fadeIn.setDuration(200);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
