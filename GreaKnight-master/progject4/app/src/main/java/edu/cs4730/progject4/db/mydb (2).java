package edu.cs4730.progject4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.compat.BuildConfig;

import java.util.Arrays;

/**
 * Created by Alex on 10/24/2016.
 */

public class mydb {
    private mySQLiteHelper DBHelper;
    public SQLiteDatabase db;

    public mydb(Context ctx) {
        DBHelper = new mySQLiteHelper(ctx);
    }

    public void open() throws SQLException {
        db = DBHelper.getWritableDatabase();
    }

    public boolean isOpen() throws SQLException {
        return db.isOpen();
    }

    public void close() {
        DBHelper.close();
        db.close();
    }

    public long cpInsert(String TableName, ContentValues values) {
        return db.insert(TableName, null, values);
    }

    public Cursor cpQuery(String TableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TableName);
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor cpQueryJoin(String TableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TableName + " INNER JOIN " + mySQLiteHelper.TABLE_CAT + " ON " + mySQLiteHelper.KEY_CAT + " = " + "category._id");
        if (projection != null) {
            projection = (String[]) append(projection, mySQLiteHelper.KEY_CATNAME);
        }
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    public int cpUpdate(String TableName, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(TableName, values, selection, selectionArgs);
    }

    public int cpDelete(String TableName, String selection, String[] selectionArgs) {
        return db.delete(TableName, selection, selectionArgs);
    }

    public void emptyName() {
        db.delete(mySQLiteHelper.TABLE_CHECKING, null, null);
    }

    static <T> T[] append(T[] arr, T element) {
        int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    public String getAccountname(int id) {
        Cursor c = cpQuery(mySQLiteHelper.TABLE_ACCOUNTS, new String[]{mySQLiteHelper.KEY_NAME}, "_id= " + id + BuildConfig.FLAVOR, null, null);
        if (c == null) {
            return BuildConfig.FLAVOR;
        }
        c.moveToFirst();
        return c.getString(c.getColumnIndex(mySQLiteHelper.KEY_NAME));
    }


    public void rawInsert(String sql) {
        db.execSQL(sql);
    }

    public Cursor rawQuery(String sql) {
        return db.rawQuery(sql, null);
    }

    public void shinny() {
        DBHelper.onUpgrade(db, 7, 8);
    }
}