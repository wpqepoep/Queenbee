package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class home extends AppCompatActivity {
    ListView listView;
    SqliteDatabase db;
    ArrayList<Information> arrayList;
    ArrayList<String> selectList = new ArrayList<String>();
    ArrayList<Integer> unDeleteSelect = new ArrayList<Integer>();
    String name1;

    ArrayAdapter arrayAdapter;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new SqliteDatabase(this);
        SQLiteDatabase sqliteDatabase = db.getWritableDatabase();//設定資料庫

        listView = findViewById(R.id.ListviewId);

        arrayList=new ArrayList<Information>();//陣列儲存資料

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//新增按鈕


        fab.setOnClickListener(new View.OnClickListener() {//新增蜂箱
            @Override
            public void onClick(View view) {
                AlertDialog.Builder editDialog = new AlertDialog.Builder(home.this);
                editDialog.setTitle("--- 蜂箱名稱 ---");

                final EditText editText = new EditText(home.this);
                editDialog.setView(editText);

                editDialog.setPositiveButton("儲存", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(home.this,home.class);
                        name1 = editText.getText().toString();
                        insertData();
                        startActivity(intent);//新增資料
                    }
                });
                editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        //...
                    }
                });
                editDialog.show();
            }
        });

        view();//刷新頁面

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//查看資料
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(home.this, detail.class);//跳頁
                intent.putExtra("subject",arrayList.get(i).getSubject());
                startActivity(intent);
            }
        });

    }
    public void view() {
        Cursor cursor = db.display();
        while (cursor.moveToNext()) {
            Information information = new Information(cursor.getString(0),cursor.getString(1));
            arrayList.add(information);
        }
        //Collections.reverse(arrayList);//新增的資料在最上方
        arrayAdapter = new InformationAdapter(this, arrayList);//passing context and arrayList to arrayAdapter
        listView.setAdapter(arrayAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);//setting choice mode
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {//method for multiChoice option此元件可以一次設定多個介面

            //checking state Item on Click mode or not
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                String id = arrayList.get(i).getId();//for getting database Id
                //if double click Item color will be white
                if(selectList.contains(id) && count>0){
                    listView.getChildAt(i).setBackgroundColor(Color.WHITE);
                    selectList.remove(id);
                    count--;
                }
                //else item color will be gray
                else{
                    selectList.add(arrayList.get(i).getId());
                    listView.getChildAt(i).setBackgroundColor(Color.GRAY);
                    unDeleteSelect.add(i);//item position storing on new arrayList
                    count++;
                }
                actionMode.setTitle(count+" item selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                /*MenuInflater inflater = actionMode.getMenuInflater();//for connecting menu with main menu here
                inflater.inflate(R.menu.selector_layout,menu);*/
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            //this method for taking action like delete,share
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                /*if(menuItem.getItemId() == R.id.deleteContextMenuId){
                    for(String i : selectList){
                        db.delete(i);
                        arrayAdapter.remove(i);
                        Toast.makeText(getApplicationContext(),count+" item Deleted",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(home.this,home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    arrayAdapter.notifyDataSetChanged();
                    actionMode.finish();
                    count = 0;
                }*/
                return true;
            }

            //this method for destroying actionMode
            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                for(int i: unDeleteSelect){
                    listView.getChildAt(i).setBackgroundColor(Color.WHITE);//reset all selected item with gray color
                }
                count = 0;//reset count here
                unDeleteSelect.clear();
                selectList.clear();
            }
        });
    }
    public void insertData(){
        long l = -1;

        Date date = new Date();
        String d = (String) android.text.format.DateFormat.format("dd/MM/yyyy",date);

        if(name1.equals("")){//here
            Toast.makeText(getApplicationContext(),"請輸入文字",Toast.LENGTH_SHORT).show();
        }
        else{
            l = db.insertData(name1);
        }

        if(l>=0){
            Toast.makeText(getApplicationContext(),"紀錄成功",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "紀錄失敗", Toast.LENGTH_SHORT).show();
        }
    }

}