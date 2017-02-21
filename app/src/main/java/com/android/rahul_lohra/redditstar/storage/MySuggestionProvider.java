package com.android.rahul_lohra.redditstar.storage;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by rkrde on 18-02-2017.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.android.rahul_lohra.redditstar.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
