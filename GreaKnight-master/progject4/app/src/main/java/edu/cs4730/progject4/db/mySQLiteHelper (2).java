package edu.cs4730.progject4.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alex on 10/23/2016.
 */
public class mySQLiteHelper extends SQLiteOpenHelper {

    public static final String KEY_NAME = "Name";
    public static final String KEY_CATNAME = "CatName";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TYPE = "CheckNum";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CAT = "Category";
    public static final String KEY_ROWID = "_id";   //required field for the cursorAdapter
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "myCheckBook.db";

    public static final String TABLE_CAT = "category";
    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String TABLE_CHECKING = "checking";

    private static final int DATABASE_VERSION = 1;
    // Table creation sql statement
    private static final String CREATE_TABLE_CAT =
            "CREATE TABLE " + TABLE_CAT + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +  //this line is required for the cursorAdapter.
                    KEY_CATNAME + " TEXT );";

    // table creation sql statement for accounts
    private static final String CREATE_TABLE_LIST =
            "CREATE TABLE " + TABLE_ACCOUNTS + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +  //this line is required for the cursorAdapter.
                    KEY_NAME + " TEXT );";

    // table creation sql statement for checking
    private static final String CREATE_TABLE_CHECKING =
            "CREATE TABLE " + TABLE_CHECKING + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +  //this line is required for the cursorAdapter.
                    KEY_DATE + " TEXT, " +
                    KEY_TYPE + " TEXT, " +
                    KEY_NAME + " TEXT, " +
                    KEY_AMOUNT + " REAL, " +
                    KEY_CAT + " INTEGER, " +
                    "FOREIGN KEY(" + KEY_CAT + ") REFERENCES " + TABLE_CAT + "(" + KEY_CAT + ")" +
                    " );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CAT);
        db.execSQL(CREATE_TABLE_CHECKING);
        db.execSQL(CREATE_TABLE_LIST);
        db.execSQL("\ninsert into accounts(Name) values (\"checking\");");

    }
    public mySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS category");
        Cursor mCursor = db.rawQuery("select * from accounts", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                db.execSQL("DROP TABLE IF EXISTS " + mCursor.getString(mCursor.getColumnIndex(KEY_NAME)));
                mCursor.moveToNext();
            }
        }
        db.execSQL("DROP TABLE IF EXISTS accounts");
        onCreate(db);
    }




}
