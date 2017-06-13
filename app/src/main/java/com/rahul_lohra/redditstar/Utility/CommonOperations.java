package com.rahul_lohra.redditstar.Utility;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.activity.WebViewActivity;
import com.rahul_lohra.redditstar.modal.custom.DetailPostModal;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;

import static com.rahul_lohra.redditstar.Utility.MyUrl.AUTH_URL;
import static com.rahul_lohra.redditstar.Utility.MyUrl.CLIENT_ID;
import static com.rahul_lohra.redditstar.Utility.MyUrl.REDIRECT_URI;
import static com.rahul_lohra.redditstar.Utility.MyUrl.STATE;

/**
 * Created by rkrde on 28-02-2017.
 */

public class CommonOperations {
    public static void addNewAccount(Activity activity){
        String scopeArray[] = activity.getResources().getStringArray(R.array.scope);
        String scope = MyUrl.getProperScope(scopeArray);
        String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, scope);
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }

    public static DetailPostModal getDetailModalFromCursor(Cursor cursor){
        final String sqlId = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SQL_ID));
        final String id = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_ID));
        final String subreddit = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT));
        final String subredditId = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SUBREDDIT_ID));
        final String name = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_NAME));
        final String author = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_AUTHOR));
        final long createdUtc = cursor.getLong(cursor.getColumnIndex(MyPostsColumn.KEY_CREATED_UTC));
        final String time = Constants.getTimeDiff(createdUtc);
        final String ups = String.valueOf(cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_UPS)));
        final String title = String.valueOf(cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_TITLE)));
        final String commentsCount = String.valueOf(cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_COMMENTS_COUNT)));
//        final Preview preview = frontPageChildData.getPreview();
        final String thumbnail = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_THUMBNAIL));
        final String url = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_URL));
        final Integer likes = cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_LIKES));
        final String bigImageUrl = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_BIG_IMAGE_URL));
        final String postHint = cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_POST_HINT));
        final String domain =  cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_DOMAIN));
        final int thumbnailHasIMage =  cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_IS_THUMBNAIL_HAS_IMAGE));
        final int bigImageUrlHasImage =  cursor.getInt(cursor.getColumnIndex(MyPostsColumn.KEY_IS_BIG_IMAGE_URL_HAS_IMAGE));
        final String score =  cursor.getString(cursor.getColumnIndex(MyPostsColumn.KEY_SCORE));

        return new DetailPostModal(id,
                subreddit,
                postHint,
                ups,
                title,
                commentsCount,
                thumbnail,
                time,
                author,
                likes,
                name,
                bigImageUrl,
                url,
                domain,
                thumbnailHasIMage,
                bigImageUrlHasImage,
                score,
                subredditId);

    }
}
