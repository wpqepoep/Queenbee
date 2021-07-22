package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class queenbee extends AppCompatActivity {
    TextView t1;
    ListView lv1;
    String name2;
    ArrayList<Information> arrayList;
    ArrayList<String> selectList = new ArrayList<String>();
    ArrayList<Integer> unDeleteSelect = new ArrayList<Integer>();
    ArrayAdapter arrayAdapter;
    qbdatabase db;
    //Button btn1;

    int count = 0;

    public static final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queenbee);

        //insert qbdatabase
        db = new qbdatabase(this);
        SQLiteDatabase sqliteDatabase = db.getWritableDatabase();

        arrayList=new ArrayList<Information>();

        Intent intent = getIntent(); //取得傳入的 Intent 物件
        Bundle bundlename = intent.getExtras();
        name2 = bundlename.getString("subject");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        t1 = findViewById(R.id.t1);
        t1.setText(name2);
        lv1 = findViewById(R.id.lv1);
        /*btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(Lb);*/

        fab.setOnClickListener(new View.OnClickListener() {//新增蜂箱
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(queenbee.this,qbdetail.class);
                intent.putExtra("subject",name2);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        view();

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {//查看資料
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = arrayList.get(i).getSubject();
                Cursor cursor = db.getdetail(name,name2);
                while (cursor.moveToNext()) {
                    Log.d("mylog", "cursordetail: "+cursor.getCount());
                    String id=cursor.getString(0);
                    String name1=cursor.getString(1);
                    String warm= cursor.getString(2);
                    String eclosion= cursor.getString(3);
                    String mating=cursor.getString(4);
                    String enter=cursor.getString(5);
                    String part=cursor.getString(6);
                    String time=cursor.getString(7);
                    String name2=cursor.getString(8);

                    Intent intent = new Intent(queenbee.this, qbupdate.class);//跳頁
                    intent.putExtra("id",id);
                    intent.putExtra("name1",name1);
                    intent.putExtra("warm",warm);
                    intent.putExtra("eclosion",eclosion);
                    intent.putExtra("mating",mating);
                    intent.putExtra("enter",enter);
                    intent.putExtra("part",part);
                    intent.putExtra("time",time);
                    intent.putExtra("name2",name2);
                    startActivity(intent);
                }
            }
        });
    }
    /*private Button.OnClickListener Lb = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Cursor cursor = db.data(name2);
            Log.d("mylog", "test: "+cursor.getCount());
            /*arrayAdapter = new InformationAdapter(queenbee.this, arrayList);//passing context and arrayList to arrayAdapter
            lv1.setAdapter(arrayAdapter);*/
       // }
   // };
    public void view() {

        Cursor cursor = db.data(name2);
        while (cursor.moveToNext()) {
            Information information = new Information(cursor.getString(0),cursor.getString(1));
            arrayList.add(information);
        }
        //Collections.reverse(arrayList);//新增的資料在最上方
        arrayAdapter = new InformationAdapter(this, arrayList);//passing context and arrayList to arrayAdapter
        lv1.setAdapter(arrayAdapter);

        lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);//setting choice mode
        lv1.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {//method for multiChoice option此元件可以一次設定多個介面

            //checking state Item on Click mode or not
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                String id = arrayList.get(i).getId();//for getting database Id
                //if double click Item color will be white
                if(selectList.contains(id) && count>0){
                    lv1.getChildAt(i).setBackgroundColor(Color.WHITE);
                    selectList.remove(id);
                    count--;
                }
                //else item color will be gray
                else{
                    selectList.add(arrayList.get(i).getId());
                    lv1.getChildAt(i).setBackgroundColor(Color.GRAY);
                    unDeleteSelect.add(i);//item position storing on new arrayList
                    count++;
                }
                actionMode.setTitle(count+" item selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            //this method for taking action like delete,share
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return true;
            }

            //this method for destroying actionMode
            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                for(int i: unDeleteSelect){
                    lv1.getChildAt(i).setBackgroundColor(Color.WHITE);//reset all selected item with gray color
                }
                count = 0;//reset count here
                unDeleteSelect.clear();
                selectList.clear();
            }
        });
    }
}