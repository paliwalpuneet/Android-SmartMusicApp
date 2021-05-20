package com.example.smartmusicapp.DbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(@Nullable Context context) {
        super(context, "MyDataBase",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = " CREATE TABLE " + "Details" + "(" + "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "FirstName" + " TEXT," +  "LastName" + " TEXT,"+ "Mobile" + " TEXT,Login int)";
        db.execSQL(create);

        String create2 = " CREATE TABLE " + "Favourite" + "(" + "Id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "favourite" + " TEXT)" ;
        db.execSQL(create2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addUser(Details details)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName",details.getFirstName());
        values.put("LastName",details.getLastName());
        values.put("Mobile",details.getMobileNumber());
        values.put("Login",details.login);
        db.insert("Details",null,values);
        Log.d("DBADDUser", "addUser:Added User SuccessFully ");
    }

    public Details getUser()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM Details LIMIT 1";
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            Details detail = new Details();
            detail.setFirstName(cursor.getString(1));
            detail.setLastName(cursor.getString(2));
            detail.setMobileNumber(cursor.getString(3));
            detail.setLogin(cursor.getInt(4));
            return detail;
        }
        return null;

    }

    public void addFavourite(String favourite)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favourite",favourite);
        db.insert("Favourite",null,values);
        Log.d("DBADDFavourite", "addFavourite:Added Favourite SuccessFully ");
    }

    public List<String> getFavourites() {
        List<String> favourites = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM Favourite";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String favourite = cursor.getString(1);
                favourites.add(favourite);
            } while (cursor.moveToNext());

        }
        return favourites;
    }

    public void deleteFavourite(String favourite)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Favourite","favourite" + "=?",new String[]{favourite});
        db.close();

    }

}
