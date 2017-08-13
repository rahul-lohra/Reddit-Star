package com.rahul_lohra.redditstar.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rahul_lohra.redditstar.Application.Initializer;
import com.rahul_lohra.redditstar.storage.column.CommentsColumn;
import com.rahul_lohra.redditstar.storage.column.MyFavouritesColumn;
import com.rahul_lohra.redditstar.storage.column.MyPostsColumn;
import com.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.rahul_lohra.redditstar.storage.column.SuggestionColumn;
import com.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

import javax.inject.Inject;

/**
 * Created by rkrde on 23-01-2017.
 */
@SuppressWarnings("HardCodedStringLiteral")
@Database(version = MyDatabase.VERSION)
public class MyDatabase {

    private static String TAG = "MyDatabase";

    public static final int VERSION = 12;
    @Table(MySubredditColumn.class)
    public static final String MY_SUBREDDIT_TABLE = "my_subreddit_table";

    @Table(UserCredentialsColumn.class)
    public static final String USER_CREDENTIAL_TABLE = "user_credential_table";

    @Table(MyFavouritesColumn.class)
    public static final String USER_FAVORITES_TABLE = "user_favorites_table";

    @Table(MyPostsColumn.class)
    public static final String USER_POSTS_TABLE = "user_posts_table";

    @Table(CommentsColumn.class)
    public static final String COMMENTS_TABLE = "comments_table";

    @Table(SuggestionColumn.class)
    public static final String SUGGESTION_TABLE = "suggestion_table";

    @OnUpgrade
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade", "oldVersion:" + oldVersion + ",newVersion:" + newVersion);
        if (oldVersion != newVersion) {
            switch (newVersion) {
                case 2:
                    db.beginTransaction();
                    try {
                        String execSql = "CREATE TABLE IF NOT EXISTS " + SUGGESTION_TABLE + " ( "
                                + SuggestionColumn.KEY_SUGGESTION + " TEXT NOT NULL UNIQUE, "
                                + SuggestionColumn.KEY_SQL_ID + " INTEGER PRIMARY KEY "
                                + " )";
                        db.execSQL(execSql);
                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        db.endTransaction();
                    }
                    break;
                case 4: {
                    String migration = "ALTER TABLE " + USER_CREDENTIAL_TABLE
                            + " ADD  "
                            + UserCredentialsColumn.ACTIVE_STATE + " INTEGER NOT NULL DEFAULT -1" +
                            "";
                    db.beginTransaction();
                    try {
                        db.execSQL(migration);
                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        Log.e("onUpgrade", "ImgurData executing database migration: %s" + migration);

                    } finally {
                        db.endTransaction();
                    }

                }
                break;
                default:{
                    Log.d(TAG,"run default case");
                    db.beginTransaction();
                    try {
                        String execSql="drop table IF EXISTS "+MY_SUBREDDIT_TABLE;
                        db.execSQL(execSql);

                        execSql="drop table IF EXISTS "+USER_CREDENTIAL_TABLE;
                        db.execSQL(execSql);

                        execSql="drop table IF EXISTS "+USER_FAVORITES_TABLE;
                        db.execSQL(execSql);

                        execSql="drop table IF EXISTS "+USER_POSTS_TABLE;
                        db.execSQL(execSql);

                        execSql="drop table IF EXISTS "+SUGGESTION_TABLE;
                        db.execSQL(execSql);

                        execSql="drop table IF EXISTS "+COMMENTS_TABLE;
                        db.execSQL(execSql);

                        com.rahul_lohra.redditstar.storage.generated.MyDatabase.getInstance(Initializer.getInstance()).onCreate(db);
                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        db.endTransaction();

                    }
                }
                break;
            }
//            db.close();
        }
    }

}
