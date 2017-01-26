package com.android.rahul_lohra.redditstar.storage;

import com.android.rahul_lohra.redditstar.storage.column.MySubredditColumn;
import com.android.rahul_lohra.redditstar.storage.column.UserCredentialsColumn;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by rkrde on 23-01-2017.
 */
@Database(version = MyDatabase.VERSION)
public class MyDatabase {
    public static final int VERSION = 1;
    @Table(MySubredditColumn.class) public static final String MY_SUBREDDIT_TABLE = "my_subreddit_table";

    @Table(UserCredentialsColumn.class) public static final String USER_CREDENTIAL_TABLE = "user_credential_table";
}
