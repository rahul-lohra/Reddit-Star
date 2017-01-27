package com.android.rahul_lohra.redditstar.storage.column;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rkrde on 24-01-2017.
 */

public interface UserCredentialsColumn {
    @DataType(INTEGER) @PrimaryKey
    String _ID = "_id";

    @DataType(TEXT)
    String NAME = "name";

    @DataType(TEXT)
    String REDDIT_ID = "reddit_id";

    @DataType(TEXT) @NotNull @Unique
    String ACCESS_TOKEN = "access_token";

    @DataType(TEXT) @NotNull
    String REFRESH_TOKEN = "refresh_token";

    @DataType(INTEGER) @NotNull @DefaultValue("-1")
    String ACTIVE_STATE = "is_active";

}
