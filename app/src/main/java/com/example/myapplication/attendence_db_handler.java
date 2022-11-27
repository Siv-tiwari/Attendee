package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class attendence_db_handler extends SQLiteOpenHelper {
    public attendence_db_handler(Context context){
        super(context, attendence_db_params.DB_NAME,null, parameters.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+attendence_db_params.TABLE_NAME + "("
                + attendence_db_params.KEY_ID + " INTEGER PRIMARY KEY, " + attendence_db_params.KEY_SAP + " TEXT, " + attendence_db_params.KEY_DATE + " TEXT," + attendence_db_params.KEY_P + " INTEGER," + attendence_db_params.KEY_A + " INTEGER,"+ attendence_db_params.KEY_L + " INTEGER" +")";
        Log.d("db_attend",create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // inserting
    public void add_user(attendence_data con){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(attendence_db_params.KEY_SAP,con.getSap());
        values.put(attendence_db_params.KEY_DATE,con.getDate());
        values.put(attendence_db_params.KEY_P,con.getPresent());
        values.put(attendence_db_params.KEY_A,con.getAbsent());
        values.put(attendence_db_params.KEY_L,con.getLeave());

        db.insert(attendence_db_params.TABLE_NAME,null,values);
        Log.d("db_attend","successfully inserted");
        db.close();
    }

    // fetching
    public List<attendence_data> fetching(){
        List<attendence_data> attendence_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // generating the query to read from db
        String fetch = "SELECT * FROM "+attendence_db_params.TABLE_NAME;
        Cursor cursor = db.rawQuery(fetch,null);
        if(cursor.moveToFirst()){
            do {
                attendence_data co = new attendence_data();
                co.setId(Integer.parseInt(cursor.getString(0)));
                co.setSap(cursor.getString(1));
                co.setDate(cursor.getString(2));
                co.setPresent(Integer.parseInt(cursor.getString(3)));
                co.setAbsent(Integer.parseInt(cursor.getString(4)));
                co.setLeave(Integer.parseInt(cursor.getString(5)));
                attendence_list.add(co);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return attendence_list;
    }

    // updation
    public void update_data(int status,String date){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(attendence_db_params.KEY_P,status);
        Log.d("check_pass",values.toString());
        db.update(attendence_db_params.TABLE_NAME,values, "date=?", new String[]{date});
        db.close();
    }
    // Deletion
    public void delete_contact_by_id(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(attendence_db_params.TABLE_NAME,attendence_db_params.KEY_ID +"=?",new String[]{String.valueOf(id)}); // here ? will be replaced by id
        db.close();
    }
}
