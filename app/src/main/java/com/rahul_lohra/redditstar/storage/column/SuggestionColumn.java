package com.rahul_lohra.redditstar.storage.column;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rkrde on 25-03-2017.
 */
@SuppressWarnings("HardCodedStringLiteral")
public interface SuggestionColumn {
    @DataType(INTEGER)
    @PrimaryKey
    String KEY_SQL_ID = "_id";

    @DataType(TEXT)
    @NotNull
    @Unique
    String KEY_SUGGESTION = "key_suggestion";
}
