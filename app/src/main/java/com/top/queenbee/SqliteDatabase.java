package com.top.queenbee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {
    public static final String databaseName = "save.db";
    public static final String tableName = "saveTable";
    public static final String col_1 = "id";
    public static final String col_2 = "name";

    //this constructor for creating the database
    public SqliteDatabase(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + databaseName);//drop table if exist
        onCreate(sqLiteDatabase); //and create new table
    }

    //function for inserting on sqlite database
    public long insertData(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();//for accessing database data
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,name);
        long id = sqLiteDatabase.insert(tableName, null, contentValues);
        return id;
    }
    public Cursor display(){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();//for accessing database data
        Cursor cursor = sqliteDatabase.rawQuery("select * from "+tableName, null);
        return cursor;
    }
    //for updating database data
    public boolean update(String name,String id){
        try{
            SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_1,id);
            contentValues.put(col_2,name);
            sqliteDatabase.update(tableName,contentValues,col_1+"=?", new String[]{id});
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    //get spinner labels
    public ArrayList<String> getAllLabels(){
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "select " + col_2 + " from " + tableName + "";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                list.add("所有");
                while (cursor.moveToNext()) {
                    String pname = cursor.getString(cursor.getColumnIndex("name"));
                    list.add(pname);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }
    //for deleting database data
    public boolean delete(String id){
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        sqliteDatabase.delete(tableName,col_1+" = ?",new String[]{id});
        return  true;
    }
}
