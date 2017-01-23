package com.android.rahul_lohra.redditstar.storage;

import com.android.rahul_lohra.redditstar.storage.column.MySubreddit;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by rkrde on 23-01-2017.
 */
@Database(version = MyDatabase.VERSION)
public class MyDatabase {
    public static final int VERSION = 1;
    @Table(MySubreddit.class) public static final String MY_SUBREDDIT_TABLE = "my_subreddit_table";
}
