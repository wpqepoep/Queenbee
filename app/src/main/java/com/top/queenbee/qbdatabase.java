package com.top.queenbee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class qbdatabase extends SQLiteOpenHelper {
    public static final String databaseName = "qbdatabase.db";
    public static final String tableName = "qbdatabaseTable";
    public static final String col_1 = "id";
    public static final String col_2 = "name1";
    public static final String col_3 = "warm";
    public static final String col_4 = "eclosion";
    public static final String col_5 = "mating";
    public static final String col_6 = "enter";
    public static final String col_7 = "part";
    public static final String col_8 = "time";
    public static final String col_9 = "name2";

    //this constructor for creating the database
    public qbdatabase(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name1 TEXT,warm Integer,eclosion Integer,mating Integer,enter Integer,part Integer,time Date,name2 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + databaseName);//drop table if exist
        onCreate(sqLiteDatabase); //and create new table
    }

    //function for inserting on sqlite database
    public long insertData(String name1,String warm,String eclosion,String mating,String enter,String part,int time,String name2) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();//for accessing database data
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,name1);
        contentValues.put(col_3,warm);
        contentValues.put(col_4,eclosion);
        contentValues.put(col_5,mating);
        contentValues.put(col_6,enter);
        contentValues.put(col_7,part);
        contentValues.put(col_8,time);
        contentValues.put(col_9,name2);

        long id = sqLiteDatabase.insert(tableName, null, contentValues);
        return id;
    }
    public Cursor data(String name){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();//for accessing database data
        Cursor cursor = sqliteDatabase.rawQuery("select * from "+tableName+" WHERE "+col_9+"=?",new String[]{name});
        return cursor;
    }

    public Cursor getdetail(String name1,String name2){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();//for accessing database data
        Cursor cursor = sqliteDatabase.rawQuery("select * from "+tableName+" WHERE "+col_2+"=? and "+col_9+"=? ",new String[]{name1,name2});
        return cursor;
    }
    //for updating database data
    public boolean update(String name1,String warm,String eclosion,String mating,String enter,String part,int time,String name2,String id){
        try{
            SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_1,id);
            contentValues.put(col_2,name1);
            contentValues.put(col_3,warm);
            contentValues.put(col_4,eclosion);
            contentValues.put(col_5,mating);
            contentValues.put(col_6,enter);
            contentValues.put(col_7,part);
            contentValues.put(col_8,time);
            contentValues.put(col_9,name2);

            sqliteDatabase.update(tableName,contentValues,col_1+"=?", new String[]{id});
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    //for deleting database data
    public boolean delete(String id){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        sqliteDatabase.delete(tableName,col_1+" = ?",new String[]{id});
        return  true;
    }
}
