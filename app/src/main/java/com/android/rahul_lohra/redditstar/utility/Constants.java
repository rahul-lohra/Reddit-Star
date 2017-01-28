package com.android.rahul_lohra.redditstar.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.rahul_lohra.redditstar.storage.MyProvider;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

/**
 * Created by rkrde on 24-01-2017.
 */

public class Constants {
    public static final String AUTHORIZATION = "Authorization";
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

    public static void updateAccessToken(Context context,String newToken,String refreshToken){

        ContentValues cv = new ContentValues();
        cv.put(UserCredentialsColumn.ACCESS_TOKEN,newToken);
        String mSelection = UserCredentialsColumn.REFRESH_TOKEN +"=?";
        String mSelectionArgs[] ={refreshToken};
        Uri mUri = MyProvider.UserCredentialsLists.CONTENT_URI;

        int rowsUpdated = context.getContentResolver().update(mUri,cv,mSelection,mSelectionArgs);
        Log.d(TAG,"rowsUpdated:"+rowsUpdated);

    }


}
