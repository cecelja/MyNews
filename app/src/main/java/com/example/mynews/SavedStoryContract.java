package com.example.mynews;

import android.provider.BaseColumns;

public final class SavedStoryContract {

    private SavedStoryContract(){}

    public static abstract class SavedStory implements BaseColumns{

        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "url";

    }

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SavedStory.TABLE_NAME;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SavedStory.TABLE_NAME + " (" +
                    SavedStory._ID + " INTEGER PRIMARY KEY," +
                    SavedStory.COLUMN_NAME_TITLE + " TEXT," +
                    SavedStory.COLUMN_NAME_SUBTITLE + " TEXT)";

}
