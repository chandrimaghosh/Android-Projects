package edu.neu.madcourse.chandrimaghosh.MemorMe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by chandrimaghosh on 4/11/17.
 */

public class MemorMeDataHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MemorMe.db";
    public static final String DATABASE_VERSION  = "MemorMe.db";
    public static final String CONTACTS_TABLE_NAME = "ActivityLogging";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_TIME = "name";
    public static final String CONTACTS_COLUMN_ACTIVITY = "activity";
    public static final String CONTACTS_COLUMN_CONFIDENCESCORE = "confidence_score";
    public static final String CONTACTS_COLUMN_Location = "location";

    private HashMap hp;

    public MemorMeDataHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
