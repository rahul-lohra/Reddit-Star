package com.android.rahul_lohra.redditstar.storage;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by rkrde on 23-01-2017.
 */
@Database(version = MyDatabase.VERSION)
public class MyDatabase {
    public static final int VERSION = 1;
    @Table(MySubredditColumn.class) public static final String MY_SUBREDDIT_TABLE = "my_subreddit_table";

    @Table(UserCredentialsColumn.class) public static final String USER_CREDENTIAL_TABLE = "user_credential_table";

    @Table(MyFavouritesColumn.class) public static final String USER_FAVORITES_TABLE = "user_favorites_table";

//    @OnUpgrade
//    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.d("onUpgrade","oldVersion:"+oldVersion+",newVersion:"+newVersion);
//        if(oldVersion!=newVersion){
//            switch (newVersion){
//                case 4:
//                    String migration = "ALTER TABLE "+USER_CREDENTIAL_TABLE
//                            +" ADD  "
//                            + UserCredentialsColumn.ACTIVE_STATE +" INTEGER NOT NULL DEFAULT -1" +
//                            "";
//                    db.beginTransaction();
//                    try {
//                        db.execSQL(migration);
//                        db.setTransactionSuccessful();
//                    } catch (Exception e) {
//                        Log.e("onUpgrade", "Error executing database migration: %s"+ migration);
//                        break;
//                    } finally {
//                        db.endTransaction();
//                    }
//
//            }
//            db.close();

//        }
//    }
}
