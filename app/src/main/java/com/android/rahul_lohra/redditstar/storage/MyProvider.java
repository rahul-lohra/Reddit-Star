package com.android.rahul_lohra.redditstar.storage;

import android.net.Uri;

import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by rkrde on 23-01-2017.
 */
@ContentProvider(authority = MyProvider.AUTHORITY,
        database = MyDatabase.class)
public class MyProvider {
    public static final String AUTHORITY = "com.android.rahul_lohra.redditstar.storage.MyProvider";

    @TableEndpoint(table = MyDatabase.MY_SUBREDDIT_TABLE)
    public static class SubredditLists {

        @ContentUri(
                path = "my_subreddit",
                type = "vnd.android.cursor.dir/my_subreddit_item"
//                defaultSort = messageColumns._PATH + " ASC"
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/my_subreddit");

        @InexactContentUri(
                path = "my_subreddit/*",
                name = "MY_SUBREDDIT_ID",
                type = "vnd.android.cursor.item/my_subreddit_item",
                whereColumn = MySubredditColumn.KEY_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/my_subreddit/" + id);
        }
    }

    @TableEndpoint(table = MyDatabase.USER_CREDENTIAL_TABLE)
    public static class UserCredentialsLists {

        @ContentUri(
                path = "user_credentials",
                type = "vnd.android.cursor.dir/user_credentials_item"
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user_credentials");

        @InexactContentUri(
                path = "user_credentials/*",
                name = "USER_CREDENTIALS_ID",
                type = "vnd.android.cursor.item/user_credentials_item",
                whereColumn = UserCredentialsColumn._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/user_credentials/" + id);
        }
    }

    @TableEndpoint(table = MyDatabase.USER_FAVORITES_TABLE)
    public static class FavoritesLists {

        @ContentUri(
                path = "user_favorites",
                type = "vnd.android.cursor.dir/user_favorites_item"
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user_favorites");

        @InexactContentUri(
                path = "user_favorites/*",
                name = "USER_FAVORITES_ID",
                type = "vnd.android.cursor.item/user_favorites_item",
                whereColumn = UserCredentialsColumn._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/user_favorites/" + id);
        }
    }

}
