package com.android.rahul_lohra.redditstar.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.rahul_lohra.redditstar.modal.FavoritesModal;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChild;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageChildData;
import com.android.rahul_lohra.redditstar.modal.frontPage.FrontPageResponse;
import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by rkrde on 24-01-2017.
 */

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

    private static String TAG = Constants.class.getSimpleName();

    public static String[] getAccessTokenAndRefreshTokenOfActiveUser(Context context){

        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;
        String mProjection[] = {UserCredentialsColumn.ACCESS_TOKEN,UserCredentialsColumn.REFRESH_TOKEN};
        String mSelection = UserCredentialsColumn.ACTIVE_STATE +"=?";
        String mSelectionArgs[]={"1"};
        Cursor cursor = context.getContentResolver().query(mUri,mProjection,mSelection,mSelectionArgs,null);
        String accessToken = "";
        String refreshToken = "";
        if(cursor.moveToFirst()){
            do{
                accessToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.ACCESS_TOKEN));
                refreshToken = cursor.getString(cursor.getColumnIndex(UserCredentialsColumn.REFRESH_TOKEN));

            }while (cursor.moveToNext());
        }
        cursor.close();
        String array[] = {accessToken,refreshToken};
        return array;
    }

    public static void updateAccessToken(Context context,String newToken, String refreshToken){

        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.ACCESS_TOKEN,newToken);
        String mSelection = UserCredentialsColumn.REFRESH_TOKEN +"=?";
        String mSelectionArgs[] ={refreshToken};
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;

        int rowsUpdated = context.getContentResolver().update(mUri,cv,mSelection,mSelectionArgs);
        Log.d(TAG,"rowsUpdated:"+rowsUpdated);

    }

    public static String getTimeDiff(long utcTime){

        Calendar c = Calendar.getInstance();
        int utcOffset = c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET);
        Long utcMilliseconds = (c.getTimeInMillis() + utcOffset)/1000;
        long seconds = (utcMilliseconds)- utcTime;

        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;

        String time=String.valueOf((days!=0)?days+"d ":""+((hours!=0)?hours+"h ":"")+((minutes!=0)?minutes+"m ":"")+((sec!=0)?sec+"s ":""));
        return time;
    }


    public static void insertIntoFavoritesDb(Context context,FavoritesModal modal){
        Uri mUri = MyProvider.FavoritesLists.CONTENT_URI;
        ContentValues cv = new ContentValues();
        cv.put(MyFavouritesColumn.KEY_SUBREDDIT_ID,modal.getSubredditId());
        cv.put(MyFavouritesColumn.KEY_FULL_NAME,modal.getFullName());
        cv.put(MyFavouritesColumn.KEY_DISPLAY_NAME,modal.getDisplayName());
        context.getContentResolver().insert(mUri,cv);
    }

    public static void deleteFromFavoritesDb(Context context,String fullName){
        Uri mUri = MyProvider.FavoritesLists.CONTENT_URI;
        String mWhere = MyFavouritesColumn.KEY_FULL_NAME +"=?";
        String mSelectionArgs[]={fullName};
        context.getContentResolver().delete(mUri,mWhere,mSelectionArgs);
    }

    public static void insertPostsIntoTable(Context context, FrontPageResponse modal, Uri mUri){
//        Uri mUri = MyProvider.PostsLists.CONTENT_URI;
        String mWhere = MyPostsColumn.KEY_ID +"=?";
        List<FrontPageChild> mList = modal.getData().getChildren();

        for(FrontPageChild frontPageChild :mList){
            FrontPageChildData data = frontPageChild.getData();
            String id  = data.getId();
            String mSelectionArgs[]={id};
            context.getContentResolver().delete(mUri,mWhere,mSelectionArgs);
            ContentValues contentValues = CustomOrm.FrontPageChildDataToContentValues(data);
            context.getContentResolver().insert(mUri,contentValues);
        }
    }

    public static void updateLikes(Context context,Integer dir,String id){
        Uri mUriPosts = MyProvider.PostsLists.CONTENT_URI;
        Uri mUriTemp = MyProvider.TempLists.CONTENT_URI;

        ContentValues cv = new ContentValues();
        cv.put(MyPostsColumn.KEY_LIKES,dir);
        String mWhere = MyPostsColumn.KEY_ID +"=?";
        String mSelectionArgs[]={id};
        context.getContentResolver().update(mUriPosts,cv,mWhere,mSelectionArgs);
        context.getContentResolver().update(mUriTemp,cv,mWhere,mSelectionArgs);
    }

    public static void clearTable(Context context,Uri mUri){
        context.getContentResolver().delete(mUri,null,null);
    }
}
