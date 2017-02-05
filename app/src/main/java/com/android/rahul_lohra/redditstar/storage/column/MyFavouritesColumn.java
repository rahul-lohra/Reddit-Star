package com.android.rahul_lohra.redditstar.storage.column;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;

/**
 * Created by rkrde on 05-02-2017.
 */

public interface MyFavouritesColumn {
    @DataType(INTEGER)
    @PrimaryKey
    String KEY_SQL_ID = "_id";

    @DataType(REAL)
    @NotNull @Unique
    String KEY_SUBREDDIT_ID = "subreddit_id";

    @DataType(INTEGER)
    @NotNull @DefaultValue("-1")
    String KEY_RANK = "rank";

}
