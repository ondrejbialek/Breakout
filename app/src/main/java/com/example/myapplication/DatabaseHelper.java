package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ScoreDatabase.db";
    public static final String TABLE_NAME = "score_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SCORE";
    public static final String COL_3 = "DIFFICULTY";
    public static final String COL_4 = "SCOREDATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE INTEGER, DIFFICULTY VARCHAR(10), SCOREDATE VARCHAR(15))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(int score, String difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, score);
        contentValues.put(COL_3, difficulty);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(new Date());
        contentValues.put(COL_4, date);
        db.insert(TABLE_NAME,null, contentValues);
    }

    public Cursor select(String difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE DIFFICULTY = '" + difficulty + "' ORDER BY SCORE DESC LIMIT 3", null);
        return cursor;
    }
}
