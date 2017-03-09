package com.android.rahul_lohra.redditstar.storage.column;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import java.util.List;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rkrde on 23-01-2017.
 */

public interface MySubredditColumn {
    @DataType(INTEGER)
    @PrimaryKey
    String KEY_SQL_ID = "_id";

    @DataType(REAL)
    @NotNull
    String KEY_ID = "subreddit_id";

    @DataType(TEXT)
    @NotNull
    String KEY_DISPLAY_NAME = "subreddit_display_name";

    @DataType(TEXT)
    String KEY_HEADER_IMAGE = "headerImg";

    @DataType(TEXT)
    String KEY_TITLE = "title";

    @DataType(INTEGER)
    @NotNull
    String KEY_OVER_18 = "over18";

    @DataType(TEXT)
    String KEY_ICON_IMAGE = "iconImg";

    @DataType(INTEGER)
    String KEY_ACCOUNTS_ACTIVE = "accountsActive";

    @DataType(INTEGER)
    String KEY_SUBSCRIBERS_COUNT = "subscribers";

    @DataType(TEXT)
    String KEY_LANGUAGE = "lang";

    @DataType(TEXT)
    String KEY_COLOR = "keyColor";

    @DataType(TEXT)
    String KEY_NAME = "name";

    @DataType(TEXT)
    String KEY_URL = "url";

    @DataType(INTEGER)
    String KEY_USER_IS_MODERTOR = "userIsModerator";

    @DataType(TEXT)
    String KEY_SUBREDDIT_TYPE = "subredditType";

    @DataType(TEXT)
    String KEY_SUBMISSION_TYPE = "submissionType";

    @DataType(INTEGER)
    String USER_IS_SUBSCRIBER = "userIsSubscriber";

}
