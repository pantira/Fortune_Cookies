package com.egco438.a13273;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQL_Helper extends SQLiteOpenHelper {
    public static final String TABLE_FORTUNE = "fortune";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESS = "message";
    public static final String COLUMN_PIC = "pictureName";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "FortuneDB.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FORTUNE + "("
            + COLUMN_ID   + " integer primary key autoincrement, "
            + COLUMN_MESS + " text not null, "
            + COLUMN_PIC  + " text not null, "
            + COLUMN_DATE + " text not null);";

    public SQL_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQL_Helper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORTUNE);
        onCreate(db);
    }
}
