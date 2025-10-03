package com.mgUnicorn.eh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbmanager extends SQLiteOpenHelper {

    private static final String dbname="dbinfo";
    public dbmanager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String qry="create table tbl_contact( id integer primary key autoincrement,mobile text,clinic text,degree text,reg text,address text)";
        sqLiteDatabase.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        String qry="DROP TABLE IF EXISTS tbl_contact";
        sqLiteDatabase.execSQL(qry);
        onCreate(sqLiteDatabase);

    }

    public String addRecord(String mobile,String clinic,String degree,String reg,String address){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("mobile",mobile);
        cv.put("clinic",clinic);
        cv.put("degree",degree);
        cv.put("reg",reg);
        cv.put("address",address);


        float res=db.insert("tbl_contact",null,cv);

        if(res==-1)
            return "Failed";

        else
            return "Successfully Inserted";

    }



    public Cursor readalldata(){


        SQLiteDatabase db=this.getWritableDatabase();

        String qry="select * from tbl_contact order by id desc";
        Cursor cursor=db.rawQuery(qry,null);
        return cursor;
    }
}
