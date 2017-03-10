package com.android.rahul_lohra.redditstar.storage;

import android.net.Uri;

import com.android.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
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

    @SuppressWarnings("HardCodedStringLiteral")
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

    @SuppressWarnings("HardCodedStringLiteral")
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

    @SuppressWarnings("HardCodedStringLiteral")
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
                whereColumn = MyFavouritesColumn.KEY_SQL_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/user_favorites/" + id);
        }
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @TableEndpoint(table = MyDatabase.USER_POSTS_TABLE)
    public static class PostsLists {

        @ContentUri(
                path = "user_posts",
                type = "vnd.android.cursor.dir/user_posts_item"
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user_posts");


        @InexactContentUri(
                path = "user_posts/*",
                name = "USER_POSTS_ID",
                type = "vnd.android.cursor.item/user_posts_item",
                whereColumn = MyPostsColumn.KEY_SQL_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/user_posts/" + id);
        }
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @TableEndpoint(table = MyDatabase.COMMENTS_TABLE)
    public static class CommentsLists {

        @ContentUri(
                path = "comments",
                type = "vnd.android.cursor.dir/comments_item"
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/comments");

        @InexactContentUri(
                path = "comments/*",
                name = "COMMENTS_ID",
                type = "vnd.android.cursor.item/comments_item",
                whereColumn = CommentsColumn.KEY_SQL_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/comments/" + id);
        }
    }
    @SuppressWarnings("HardCodedStringLiteral")
    @TableEndpoint(table = MyDatabase.USER_POSTS_TABLE)
    public static class PostsComments{
        @ContentUri(
                path = "comments_posts",
                type = "vnd.android.cursor.dir/comments_posts_item",
                join = "LEFT JOIN "+MyDatabase.COMMENTS_TABLE+ " ON "+ MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_LINK_ID+ " = "+MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_ID
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/comments_posts");
        public static final String mProjection[]={
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_SQL_ID,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_ID,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_SUBREDDIT,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_SUBREDDIT_ID,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_NAME,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_AUTHOR,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_CREATED_UTC,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_UPS,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_TITLE,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_THUMBNAIL,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_URL,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_LIKES,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_BIG_IMAGE_URL,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_POST_HINT,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_COMMENTS_COUNT,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_DOMAIN,
                MyDatabase.USER_POSTS_TABLE+"."+MyPostsColumn.KEY_SCORE,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_SQL_ID,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_BODY,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_AUTHOR,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_UPS,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_LINK_ID,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_SQL_ID,
                MyDatabase.COMMENTS_TABLE+"."+CommentsColumn.KEY_DEPTH
        };

    }

    @SuppressWarnings("HardCodedStringLiteral")
    @TableEndpoint(table = MyDatabase.MY_SUBREDDIT_TABLE)
    public static class UserSubredditsWithFav{
        @ContentUri(
                path = "subreddit_fav",
                type = "vnd.android.cursor.dir/subreddit_fav_item",
                join = "LEFT JOIN "+MyDatabase.USER_FAVORITES_TABLE+ " ON "+ MyDatabase.USER_FAVORITES_TABLE+"."+MyFavouritesColumn.KEY_SUBREDDIT_ID+ " = "+MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_ID
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/subreddit_fav");
        public static final String mProjection[]={
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_SQL_ID,
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_NAME,
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_DISPLAY_NAME,
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_ID,
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_NAME,
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_TITLE,
                MyDatabase.MY_SUBREDDIT_TABLE+"."+MySubredditColumn.KEY_URL,
                MyDatabase.USER_FAVORITES_TABLE+"."+MyFavouritesColumn.KEY_DISPLAY_NAME
        };

    }
}
