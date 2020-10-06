package com.tana.nottechlab;

import android.provider.BaseColumns;

import static android.provider.BaseColumns._ID;

public final class NottechLabDBContract {

    public static class MembersEntry implements BaseColumns {
        public static final String TABLE_NAME = "member";
        public static final String COLUMN_ID = _ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_DESIGNATION = "designation";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MembersEntry.TABLE_NAME + " (" +
                    MembersEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MembersEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    MembersEntry.COLUMN_DOB + " INTEGER NOT NULL, " +
                    MembersEntry.COLUMN_DESIGNATION + " TEXT NOT NULL)";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + MembersEntry.TABLE_NAME;
}
