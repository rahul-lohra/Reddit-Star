package com.rahul_lohra.redditstar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rkrde on 22-07-2017.
 */

public class Dummy extends SQLiteOpenHelper {
    public Dummy(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void abc(){
        SQLiteDatabase db = getReadableDatabase();
    }
}
