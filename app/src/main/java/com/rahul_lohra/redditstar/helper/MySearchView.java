package com.rahul_lohra.redditstar.helper;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.adapter.cursor.SearchAdapter;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.MySuggestionProvider;
import com.rahul_lohra.redditstar.storage.column.SuggestionColumn;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by rkrde on 25-03-2017.
 */

public class MySearchView extends CardView implements
        SearchAdapter.ISearchAdapter{

    AppCompatImageView imageBack;
    AppCompatEditText et;
    RecyclerView rv;
    private Context mContext;
    private String mSearchQuery;
    private SearchAdapter searchAdapter;
    private Uri mSuggestionsUri = MyProvider.SuggestionLists.CONTENT_URI;
    private String mProjection[] = {SuggestionColumn.KEY_SUGGESTION};
    private String mWhere = SuggestionColumn.KEY_SUGGESTION + " LIKE ?";

    List<String> stringList = new ArrayList<>();

    @Override
    public void onRvTextViewClick(String string) {
        mListener.doMySearch(string,true);
        stringList.clear();
        searchAdapter.notifyDataSetChanged();
    }

    public interface ISearchView {
        void doMySearch(String query, boolean isFromStart);

        void goBack();
    }

    private ISearchView mListener;

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
        et = (AppCompatEditText) view.findViewById(R.id.et);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(mContext, stringList,this);
        rv.setAdapter(searchAdapter);

        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBack();
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 1) {
                    stringList.clear();
                    String mSelectionArgs[] = {"%" + editable.toString() + "%"};
                    Cursor mCursor = mContext.getContentResolver().query(mSuggestionsUri, mProjection, mWhere, mSelectionArgs, null);
                    if (mCursor != null) {
                        if (mCursor.moveToFirst()) {
                            do {
                                String suggestion = mCursor.getString(mCursor.getColumnIndex(SuggestionColumn.KEY_SUGGESTION));
                                stringList.add(suggestion);
                            } while (mCursor.moveToNext());
                            mCursor.close();
                        }
                    }
                }
                searchAdapter.notifyDataSetChanged();
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
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String mSelectionArgs[] = {mSearchQuery};
                                    String mSelection = SuggestionColumn.KEY_SUGGESTION + " =?";
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
                    }
                    return true;
                    default:
                        return false;
                }
            }
        });
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if(mCursor!=null)
//        {
//            mCursor.close();
//        }
//    }
}
