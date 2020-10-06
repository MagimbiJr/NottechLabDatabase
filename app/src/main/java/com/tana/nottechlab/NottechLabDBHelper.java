package com.tana.nottechlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NottechLabDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "nottech_lab.db";
    public static final int DB_VERSION = 1;

    public NottechLabDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NottechLabDBContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(NottechLabDBContract.SQL_DROP_TABLE);

        onCreate(sqLiteDatabase);
    }
}
