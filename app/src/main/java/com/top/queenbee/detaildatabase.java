package com.top.queenbee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class detaildatabase extends SQLiteOpenHelper {
    public static final String databaseName = "detail1.db";
    public static final String tableName = "detailTable1";
    public static final String col_1 = "id";
    public static final String col_2 = "name";
    public static final String col_3 = "qbyes";
    public static final String col_4 = "qbnull";
    public static final String col_5 = "totalnum";
    public static final String col_6 = "warm";
    public static final String col_7 = "eclosion";
    public static final String col_8 = "honey";
    public static final String col_9 = "pollen";
    public static final String col_10 = "empty";
    public static final String col_11 = "queen";
    public static final String col_12 = "plus";
    public static final String col_13 = "minus";
    public static final String col_14 = "sugar";
    public static final String col_15 = "food";
    public static final String col_16 = "from1";
    public static final String col_17 = "to1";
    public static final String col_18 = "detail";
    public static final String col_19 = "datetime";


    //this constructor for creating the database
    public detaildatabase(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT,qbyes TSXT,qbnull TEXT,totalnum INTEGER,warm INTEGER,eclosion INTEGER,honey INTEGER," +
                "pollen INTEGER,empty INTEGER,queen INTEGER,plus INTEGER,minus INTEGER,sugar TEXT,"+
                "food TEXT,from1 TEXT,to1 Text,detail TEXT,dateTime Date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + databaseName);//drop table if exist
        onCreate(sqLiteDatabase); //and create new table
    }

    //function for inserting on sqlite database
    public long insertData(String name, String qbyes, String qbnull, int totalnum, int warm,
                           int eclosion, int honey, int pollen, int empty, int queen,
                           int plus, int minus, String sugar,String food, String from1, String to1,
                           String detail, int datetime) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();//for accessing database data
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,name);
        contentValues.put(col_3, qbyes);
        contentValues.put(col_4, qbnull);
        contentValues.put(col_5, totalnum);
        contentValues.put(col_6, warm);
        contentValues.put(col_7, eclosion);
        contentValues.put(col_8, honey);
        contentValues.put(col_9, pollen);
        contentValues.put(col_10, empty);
        contentValues.put(col_11, queen);
        contentValues.put(col_12, plus);
        contentValues.put(col_13, minus);
        contentValues.put(col_14, sugar);
        contentValues.put(col_15, food);
        contentValues.put(col_16, from1);
        contentValues.put(col_17, to1);
        contentValues.put(col_18, detail);
        contentValues.put(col_19, datetime);
        long id = sqLiteDatabase.insert(tableName, null, contentValues);
        return id;
    }
    public Cursor display(String name,int dateTime){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();//for accessing database data
        Cursor cursor = sqliteDatabase.rawQuery("select * from "+tableName+" WHERE "+col_2+"=? and "+col_19+"=?", new String[]{name, String.valueOf(dateTime)});
        return cursor;

    }
    //for updating database data
    public boolean update(String name, String qbyes, String qbnull, int totalnum, int warm,
                          int eclosion, int honey, int pollen, int empty, int queen,
                          int plus, int minus, String sugar, String food,String from1, String to1,
                          String detail, int datetime, String id){
        try{
            SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_1,id);
            contentValues.put(col_2,name);
            contentValues.put(col_3, qbyes);
            contentValues.put(col_4, qbnull);
            contentValues.put(col_5, totalnum);
            contentValues.put(col_6, warm);
            contentValues.put(col_7, eclosion);
            contentValues.put(col_8, honey);
            contentValues.put(col_9, pollen);
            contentValues.put(col_10, empty);
            contentValues.put(col_11, queen);
            contentValues.put(col_12, plus);
            contentValues.put(col_13, minus);
            contentValues.put(col_14, sugar);
            contentValues.put(col_15, food);
            contentValues.put(col_16, from1);
            contentValues.put(col_17, to1);
            contentValues.put(col_18, detail);
            contentValues.put(col_19, datetime);
            sqliteDatabase.update(tableName,contentValues,col_1+"=?", new String[]{id});
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
   public Cursor getsugar(String name,int date,int date1){
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery("select * from " + tableName + " WHERE " + col_2 + "=? and " + col_19 + " between " + date + " and " + date1 +" order by " + col_19 + "", new String[]{name});
       return cursor;
   }
        //for deleting database data
    public boolean delete(String id){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        sqliteDatabase.delete(tableName,col_1+" = ?",new String[]{id});
        return  true;
    }
}
