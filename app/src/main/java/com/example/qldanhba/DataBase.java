package com.example.qldanhba;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy vấn không trả kết quả bao gổm: create, insert, update, delete

        public  void  QueryData(String sql){
        SQLiteDatabase database= getWritableDatabase();
        database.execSQL(sql);
        }

    //truy vấn trả kết quả: Select
        public Cursor getData(String sql){
            SQLiteDatabase sqLiteDatabase= getReadableDatabase();
            return  sqLiteDatabase.rawQuery(sql,null);
        }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
