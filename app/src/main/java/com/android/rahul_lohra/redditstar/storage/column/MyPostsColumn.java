package com.android.rahul_lohra.redditstar.storage.column;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rkrde on 27-02-2017.
 */

public interface MyPostsColumn {
    @DataType(INTEGER)
    @PrimaryKey
    String KEY_SQL_ID = "sql_id";

    @DataType(TEXT)
    @NotNull @Unique
    String KEY_ID = "_id";

    @DataType(TEXT)
    String KEY_AUTHOR = "author";

    @DataType(TEXT)
    @NotNull
    String KEY_SUBREDDIT_ID = "subreddit_id";

    @DataType(TEXT)
    @NotNull
    String KEY_SUBREDDIT = "subreddit";

    @DataType(INTEGER)
    String KEY_UPS = "ups";

    @DataType(TEXT)
    String KEY_TITLE = "title";

    @DataType(INTEGER)
    String KEY_COMMENTS_COUNT = "comments_count";

    @DataType(INTEGER)
    String KEY_CREATED_UTC = "created_utc";

    @DataType(INTEGER)
    String KEY_CREATED = "created";

    @DataType(TEXT)
    @NotNull
    String KEY_FULL_NAME = "name";

    @DataType(INTEGER)
    @NotNull
    String KEY_CLICKED = "clicked";

    @DataType(INTEGER)
    String KEY_LIKES = "likes";

    @DataType(TEXT)
    String KEY_DOMAIN = "domain";

    @DataType(INTEGER)
    String KEY_IS_SELF = "is_self";

    @DataType(INTEGER)
    @NotNull
    String KEY_OVER_18 = "over_18";

    @DataType(TEXT)
    String KEY_PERMALINK = "permalink";

    @DataType(TEXT)
    String KEY_POST_HINT = "post_hint";

    @DataType(INTEGER)
    String KEY_SCORE = "score";

    @DataType(TEXT)
    String KEY_THUMBNAIL = "thumbnail";

    @DataType(TEXT)
    String KEY_URL = "url";

    @DataType(INTEGER)
    String KEY_VISITED = "visited";

    @DataType(INTEGER)
    String KEY_LOCKED = "locked";

    @DataType(TEXT)
    String KEY_MEDIA_EMBEDED = "media_embeded";

    @DataType(TEXT)
    String KEY_PREVIEW_VARIANT = "preview_variant";

    @DataType(TEXT)
    String KEY_PREVIEW_URL_LIST= "preview_url_list";

    @DataType(INTEGER)
    String KEY_PREVIEW_COUNT= "preview_count";

}
