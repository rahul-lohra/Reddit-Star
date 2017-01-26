package com.android.rahul_lohra.redditstar.storage.column;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rkrde on 23-01-2017.
 */

public interface MySubredditColumn {
    @DataType(INTEGER) @PrimaryKey
    String _ID = "_id";

    @DataType(TEXT) @NotNull
    String KEY_CHAT_BUDDY_JID = "chat_buddy_jid";
}
