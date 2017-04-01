package com.rahul_lohra.redditstar.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.rahul_lohra.redditstar.modal.FavoritesModal;
import com.rahul_lohra.redditstar.modal.comments.Child;
import com.rahul_lohra.redditstar.modal.comments.CustomComment;
import com.rahul_lohra.redditstar.modal.comments.T1data;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageChildData;
import com.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.rahul_lohra.redditstar.storage.MyProvider;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;
import com.rahul_lohra.redditstar.viewHolder.PostView;

import java.lang.annotation.Retention;
import java.util.Calendar;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by rkrde on 24-01-2017.
 */

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

    public static String getUserAgent(Context context) {
        return "android:" + context.getPackageName() + "v1.0" + "by (/u/rahul_lohra)";
    }


    public static class Subs {

        public static final String POPULAR = "popular";
        public static final String ALL = "all";
        public static final String FRONT_PAGE = "front_page";
        public static final String SUBREDDIT = "subreddit";

    }

    @Retention(SOURCE)
    @IntDef({TYPE_POST, TYPE_SEARCH, TYPE_WIDGET, TYPE_TEMP})
    public @interface ArticleType {
    }

    public static final int TYPE_POST = 1;
    public static final int TYPE_SEARCH = 2;
    public static final int TYPE_WIDGET = 3;
    public static final int TYPE_TEMP = 4;


    private static String TAG = Constants.class.getSimpleName();

    public static String[] getAccessTokenAndRefreshTokenOfActiveUser(Context context) {

        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mProjection[] = {UserCredentialsColumn.ACCESS_TOKEN, UserCredentialsColumn.REFRESH_TOKEN};
        String mSelection = UserCredentialsColumn.ACTIVE_STATE + "=?";
        String mSelectionArgs[] = {"1"};
        Cursor cursor = context.getContentResolver().query(mUri, mProjection, mSelection, mSelectionArgs, null);
        String accessToken = "";
        String refreshToken = "";
        if (cursor.moveToFirst()) {
            do {
                accessToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.ACCESS_TOKEN));
                refreshToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.REFRESH_TOKEN));

            } while (cursor.moveToNext());
        }
        cursor.close();
        String array[] = {accessToken, refreshToken};
        return array;
    }

    public static void updateAccessToken(Context context, String newToken, String refreshToken) {

        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.ACCESS_TOKEN, newToken);
        String mSelection = UserCredentialsColumn.REFRESH_TOKEN + "=?";
        String mSelectionArgs[] = {refreshToken};
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;

        int rowsUpdated = context.getContentResolver().update(mUri, cv, mSelection, mSelectionArgs);
//        Log.d(TAG,"rowsUpdated:"+rowsUpdated);

    }

    public static void updateActiveUser(Context context,int sqlId){
        //set Active
        ContentValues cvActive = new ContentValues();
        cvActive.put(UserCredentialsColumn.ACTIVE_STATE,1);
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mSelection = UserCredentialsColumn._ID + "=?";
        String mSelectionArgs[] = {String.valueOf(sqlId)};
        context.getContentResolver().update(mUri,cvActive,mSelection,mSelectionArgs);

        //set Inactive
        ContentValues cvInActive = new ContentValues();
        cvInActive.put(UserCredentialsColumn.ACTIVE_STATE,0);
        String mSelectionInActive = UserCredentialsColumn._ID + "!= ?";
        context.getContentResolver().update(mUri,cvInActive,mSelectionInActive,mSelectionArgs);

    }

    public static String getTimeDiff(long utcTime) {

        Calendar c = Calendar.getInstance();
        int utcOffset = c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET);
        Long utcMilliseconds = (c.getTimeInMillis() + utcOffset) / 1000;
        long seconds = (utcMilliseconds) - utcTime;

        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;

        String time = String.valueOf((days != 0) ? days + "d " : "" + ((hours != 0) ? hours + "h " : "") + ((minutes != 0) ? minutes + "m " : "") + ((sec != 0) ? sec + "s " : ""));
        return time;
    }


    public static void insertIntoFavoritesDb(final Context context, final FavoritesModal modal) {

        Uri mUri = MyProvider.FavoritesLists.CONTENT_URI;
        ContentValues cv = new ContentValues();
        cv.put(MyFavouritesColumn.KEY_SUBREDDIT_ID, modal.getFullName());// As modal.getSubredditId() does not contain t5_ prefix
        cv.put(MyFavouritesColumn.KEY_FULL_NAME, modal.getFullName());
        cv.put(MyFavouritesColumn.KEY_DISPLAY_NAME, modal.getDisplayName());
        String mProj[] = {MyFavouritesColumn.KEY_FULL_NAME};
        String mSelection = MyFavouritesColumn.KEY_FULL_NAME + "= ?";
        String mSelectionArgs[] = {modal.getFullName()};
        Cursor cursor = context.getContentResolver().query(mUri, mProj, mSelection, mSelectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

            } else {
                context.getContentResolver().insert(mUri, cv);
                context.getContentResolver().notifyChange(MyProvider.UserSubredditsWithFav.CONTENT_URI, null);
            }
            cursor.close();
        }

    }

    public static void deleteFromFavoritesDb(final Context context, final String fullName) {
        Uri mUri = MyProvider.FavoritesLists.CONTENT_URI;
        String mWhere = MyFavouritesColumn.KEY_FULL_NAME + "=?";
        String mSelectionArgs[] = {fullName};
        context.getContentResolver().delete(mUri, mWhere, mSelectionArgs);
        context.getContentResolver().notifyChange(MyProvider.UserSubredditsWithFav.CONTENT_URI, null);
    }

    public static void insertPostsIntoTable(Context context, FrontPageResponse modal, @ArticleType int type) {
        String mWhere = MyPostsColumn.KEY_ID + "=?";
        String mProj[] = {MyPostsColumn.KEY_ID};
        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
        List<FrontPageChild> mList = modal.getData().getChildren();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean over18 = sp.getBoolean(SpConstants.OVER_18, false);
        for (FrontPageChild frontPageChild : mList) {
            FrontPageChildData data = frontPageChild.getData();
            String id = data.getId();
            String subredditName = data.getSubreddit();
            String mSelectionArgs[] = {"t3_" + id};
//
            boolean mOver18 = data.getOver18();
            if (over18) {
                continue;
            }

            ContentValues cv = new ContentValues();
            switch (type) {

                case TYPE_POST:
                    cv.put(MyPostsColumn.TYPE_POST, 1);
                    break;
                case TYPE_SEARCH:
                    cv.put(MyPostsColumn.TYPE_SEARCH, 1);
                    break;
                case TYPE_WIDGET:
                    cv.put(MyPostsColumn.TYPE_WIDGET, 1);
                    break;
                case TYPE_TEMP:
                    cv.put(MyPostsColumn.TYPE_TEMP, 1);
                    break;

            }

            Cursor cursor = context.getContentResolver().query(mUri, mProj, mWhere, mSelectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
//                    Log.wtf(TAG,"duplicate row found : with id:"+id+",subreddit:"+subredditName);

                    context.getContentResolver().update(mUri, cv, mWhere, mSelectionArgs);
                } else {
//                    Log.wtf(TAG,"insert row : with id:"+id+",subreddit:"+subredditName);
                    ContentValues contentValues = CustomOrm.FrontPageChildDataToContentValues(data, type);
                    context.getContentResolver().insert(mUri, contentValues);
                }
                cursor.close();
            }

//            Intent intentCommentService = new Intent(context, CommentsService.class);
//                intentCommentService.putExtra(CommentsService.POST_ID,id);
//                intentCommentService.putExtra(CommentsService.SUBREDDIT_NAME,subredditName);
//                context.startService(intentCommentService);

        }
    }

    public static void updateLikes(Context context, @PostView.DirectionMode Integer dir, String id, String ups, int previousLikeValue) {
        Uri mUriPosts = MyProvider.PostsLists.CONTENT_URI;

        ContentValues cv = new ContentValues();
        cv.put(MyPostsColumn.KEY_LIKES, dir);
        String mWhere = MyPostsColumn.KEY_ID + "=?";
        String mSelectionArgs[] = {id};
        long mUps = Long.parseLong(ups) + dir;

        if ((int) dir == 1 || (int) dir == -1) {
            cv.put(MyPostsColumn.KEY_LIKES, dir);
            cv.put(MyPostsColumn.KEY_UPS, mUps);
            cv.put(MyPostsColumn.KEY_SCORE, mUps);

        } else {
            cv.put(MyPostsColumn.KEY_UPS, Long.parseLong(ups) + ((previousLikeValue == 1) ? -1 : 1));
            cv.put(MyPostsColumn.KEY_SCORE, Long.parseLong(ups) + ((previousLikeValue == 1) ? -1 : 1));
        }
        int rowsUpdated = context.getContentResolver().update(mUriPosts, cv, mWhere, mSelectionArgs);
        context.getContentResolver().notifyChange(MyProvider.PostsComments.CONTENT_URI, null);
//        System.out.println("Rows updated:"+rowsUpdated);

    }

    public static void clearPosts(Context context, @ArticleType int type) {
        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
        String mWhereArgs[] = {"1"};
        String mWhere = null;
        switch (type) {
            case TYPE_SEARCH:
                mWhere = MyPostsColumn.TYPE_SEARCH + "=?";
                break;
            case TYPE_POST:
                mWhere = MyPostsColumn.TYPE_POST + "=?";
                break;
            case TYPE_WIDGET:
                mWhere = MyPostsColumn.TYPE_WIDGET + "=?";
                break;
            case TYPE_TEMP:
                mWhere = MyPostsColumn.TYPE_TEMP + "=?";
                break;
        }
        context.getContentResolver().delete(mUri, mWhere, mWhereArgs);
    }

    public static void clearComments(Context context) {
        context.getContentResolver().delete(MyProvider.CommentsLists.CONTENT_URI, null, null);
    }

    public static void bulkInsertIntoCommentsTable(Context context, List<CustomComment> list, String postId, String subredditName) {

        if (list.size() < 1) {
            return;
        }
        String linkId = list.get(0).getChild().t1data.getLink_id();
        String id = list.get(0).getChild().t1data.getId();
        deletePreviousComments(context, linkId);

        Uri mUri = MyProvider.CommentsLists.CONTENT_URI;
        ContentValues cv[] = new ContentValues[list.size()];
        for (int i = 0; i < list.size(); ++i) {

            cv[i] = new ContentValues();
            CustomComment customComment = list.get(i);
            Child child = customComment.getChild();
            T1data t1data = child.t1data;
            cv[i].put(CommentsColumn.KEY_DEPTH, customComment.getDepth());
            cv[i].put(CommentsColumn.KEY_SUBREDDIT_ID, t1data.getSubredditId());
            cv[i].put(CommentsColumn.KEY_LIKES, t1data.getLikes());
            cv[i].put(CommentsColumn.KEY_ID, t1data.getId());
            cv[i].put(CommentsColumn.KEY_AUTHOR, t1data.getAuthor());
            cv[i].put(CommentsColumn.PARENT_ID, t1data.getParentId());
            cv[i].put(CommentsColumn.KEY_SCORE, t1data.getScore());
            cv[i].put(CommentsColumn.KEY_BODY, t1data.getBody());
            cv[i].put(CommentsColumn.KEY_DOWNS, t1data.getDowns());
            cv[i].put(CommentsColumn.KEY_SUBREDDIT, t1data.getSubreddit());
            cv[i].put(CommentsColumn.KEY_NAME, t1data.getName());
            cv[i].put(CommentsColumn.KEY_UPS, t1data.getUps());
            cv[i].put(CommentsColumn.KEY_LINK_ID, t1data.getLink_id());
            cv[i].put(CommentsColumn.KEY_CREATED, t1data.getCreated_utc());
        }
        context.getContentResolver().bulkInsert(mUri, cv);
        context.getContentResolver().notifyChange(MyProvider.PostsComments.CONTENT_URI, null);
    }

    public static void deletePreviousComments(Context context, String linkId) {
        Uri mUri = MyProvider.CommentsLists.CONTENT_URI;
        String mWhere = CommentsColumn.KEY_LINK_ID + "=?";
        String mWhereArgs[] = {linkId};
        int c = context.getContentResolver().delete(mUri, mWhere, mWhereArgs);
//        Log.wtf(TAG,"deleted row comments count: "+c+",linkId:"+linkId);
    }

    public static void findSimilarComment(Context context, String id) {
        Uri mUri = MyProvider.CommentsLists.CONTENT_URI;
        String mWhere = CommentsColumn.KEY_ID + "=?";
        String mWhereArgs[] = {id};
        String mProjection[] = {CommentsColumn.KEY_SQL_ID, CommentsColumn.KEY_BODY, CommentsColumn.KEY_SUBREDDIT};
        Cursor cursor = context.getContentResolver().query(mUri, mProjection, mWhere, mWhereArgs, null);

        if (cursor != null) {
//
            if (cursor.moveToFirst()) {
                do {

                    String sqlId = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_SQL_ID));
                    String body = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_BODY));
                    String subreddit = cursor.getString(cursor.getColumnIndex(CommentsColumn.KEY_SUBREDDIT));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


}
