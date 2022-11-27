package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class db_handler extends SQLiteOpenHelper {
    public db_handler(Context context){
        super(context, parameters.DB_NAME,null,parameters.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+parameters.TABLE_NAME + "("
                + parameters.KEY_ID + " INTEGER PRIMARY KEY, " + parameters.KEY_FULL_NAME + " TEXT, " + parameters.KEY_USER_NAME + " TEXT," + parameters.KEY_SAP + " TEXT," + parameters.KEY_ROLL_NO + " TEXT,"+ parameters.KEY_PASSWORD + " TEXT" +")";
        Log.d("db_siv",create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // inserting
    public void add_user(data con){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(parameters.KEY_FULL_NAME,con.getFull_name());
        values.put(parameters.KEY_USER_NAME,con.getUser_name());
        values.put(parameters.KEY_SAP,con.getSap());
        values.put(parameters.KEY_ROLL_NO,con.getRoll_no());
        values.put(parameters.KEY_PASSWORD,con.getPassword());

        db.insert(parameters.TABLE_NAME,null,values);
        Log.d("db_siv","successfully inserted");
        db.close();
    }

    // fetching
    public List<data> fetching(){
        List<data> contacts_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // generating the query to read from db
        String fetch = "SELECT * FROM "+parameters.TABLE_NAME;
        Cursor cursor = db.rawQuery(fetch,null);
        if(cursor.moveToFirst()){
            do {
                data co = new data();
                co.setId(Integer.parseInt(cursor.getString(0)));
                co.setFull_name(cursor.getString(1));
                co.setUser_name(cursor.getString(2));
                co.setSap(cursor.getString(3));
                co.setRoll_no(cursor.getString(4));
                co.setPassword(cursor.getString(5));
                contacts_list.add(co);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return contacts_list;
    }

    // updation password
    public void update_data(String new_password,String old_password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(parameters.KEY_PASSWORD,new_password);
        Log.d("check_pass",values.toString());
        db.update(parameters.TABLE_NAME,values, "password=?", new String[]{old_password});
        db.close();
    }
}
