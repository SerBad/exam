package com.we.exam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhou on 2017/8/3.
 */

public class ExamWordsDaoHelper extends SQLiteOpenHelper {
    public static String dataBaseName = "word";

    public ExamWordsDaoHelper(Context context) {
        super(context,"word.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + dataBaseName + " (id integer primary key ," +
                "answer varchar(10)," +
                "question varchar(50)," +
                "options varchar(150)," +
                "checkvalue varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
