package com.rahul_lohra.redditstar.storage.column;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rkrde on 04-03-2017.
 */

 @SuppressWarnings("HardCodedStringLiteral")
 public interface CommentsColumn {
    @DataType(INTEGER)
    @PrimaryKey
    String KEY_SQL_ID = "sql_id";

    @DataType(INTEGER)
    @NotNull
    String KEY_DEPTH = "depth";

    @DataType(TEXT)
    @NotNull
    String KEY_SUBREDDIT_ID = "subreddit_id";

    @DataType(INTEGER)
    String KEY_LIKES =  "likes";

    @DataType(TEXT)
    @NotNull @Unique
    String KEY_ID = "_id";

    @DataType(TEXT)
    String KEY_AUTHOR = "author";

    @DataType(TEXT)
    String PARENT_ID  = "parentId";

    @DataType(INTEGER)
    String KEY_SCORE = "score";

    @DataType(TEXT)
    String KEY_BODY =  "body";

    @DataType(INTEGER)
    String KEY_DOWNS = "downs";

    @DataType(TEXT)
    String KEY_SUBREDDIT = "subreddit";

    @DataType(TEXT)
    String KEY_NAME = "name";

    @DataType(INTEGER)
    String KEY_UPS =  "ups";

    @DataType(TEXT)
    @NotNull
    String KEY_LINK_ID = "link_id";
}
