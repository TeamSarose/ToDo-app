package com.sarose.lab_05;

import static android.os.Build.ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Create Database with columns
    public static final String DATABASE_NAME = "Todo.db";
    public static final String TABLE_NAME = "Tasks";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TASK";
    public static final String COL_3 = "DATE";


    public DatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null,1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    // Auto implement ID number
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + " TASK TEXT, DATE STRING)");
    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //Insert record
    public  boolean insertData(String TASK, String DATE ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,TASK);
        contentValues.put(COL_3,DATE);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    //View all Records
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String getAllDataQuery = "Select * from " + TABLE_NAME;
        Cursor result = db.rawQuery(getAllDataQuery, null);
        return result;
    }

    //Update existing record in database
    public  boolean updateData(String ID, String TASK, String DATE ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, TASK);
        contentValues.put(COL_3, DATE);
        int numberOfAffectedRows = db.update(TABLE_NAME, contentValues,"ID = ?", new String[] {ID});
        return (numberOfAffectedRows > 0);
    }

    //Delete the existing record
    public int deleteData(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {ID} );
    }



}
