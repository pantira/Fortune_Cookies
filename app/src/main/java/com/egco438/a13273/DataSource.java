package com.egco438.a13273;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benz on 11/1/2016.
 */
public class DataSource {
    private SQLiteDatabase database;
    private SQL_Helper dbHelper;
    private String[] allColumns = {SQL_Helper.COLUMN_ID,
                                   SQL_Helper.COLUMN_MESS,
                                   SQL_Helper.COLUMN_PIC,
                                   SQL_Helper.COLUMN_DATE};

    public DataSource(Context context) {
        dbHelper = new SQL_Helper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Fortune createFortune(String message, String picture, String date) {
        ContentValues values = new ContentValues();
        values.put(SQL_Helper.COLUMN_MESS, message);
        values.put(SQL_Helper.COLUMN_PIC, picture);
        values.put(SQL_Helper.COLUMN_DATE, date);
        long insertID = database.insert(SQL_Helper.TABLE_FORTUNE, null, values);
        Cursor cursor = database.query(SQL_Helper.TABLE_FORTUNE, allColumns, SQL_Helper.COLUMN_ID + " = " + insertID, null, null, null, null);
        cursor.moveToFirst();
        Fortune newComment = cursorToFortune(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteFortune(Fortune comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id:" + id);
        database.delete(SQL_Helper.TABLE_FORTUNE, SQL_Helper.COLUMN_ID + " = " + id, null);
    }

    public List<Fortune> getAllFortune() {
        List<Fortune> fortune_list = new ArrayList<>();
        Cursor cursor = database.query(SQL_Helper.TABLE_FORTUNE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Fortune comment = cursorToFortune(cursor);
            fortune_list.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return fortune_list;
    }

    public Fortune cursorToFortune(Cursor cursor) {
        Fortune comment = new Fortune(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return comment;
    }
}
