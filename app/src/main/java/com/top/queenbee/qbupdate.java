package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class qbupdate extends AppCompatActivity {
    EditText ed1,edwarm,edeclosion,edmating,edenter,edpart;
    TextView t1;
    Button btn1;
    qbdatabase db;
    String id,name1,warm,eclosion,mating,enter,part,name2;
    int time;

    public static int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbupdate);

        db = new qbdatabase(this);
        SQLiteDatabase qbdatabase = db.getWritableDatabase();//設定資料庫

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        id= intent.getStringExtra("id");
        name1 = intent.getStringExtra("name1");
        warm= intent.getStringExtra("warm");
        eclosion= intent.getStringExtra("eclosion");
        mating= intent.getStringExtra("mating");
        enter= intent.getStringExtra("enter");
        part= intent.getStringExtra("part");
        String d= intent.getStringExtra("time");
        time = Integer.parseInt(d);
        name2= intent.getStringExtra("name2");

        t1 = findViewById(R.id.t1);
        ed1 = findViewById(R.id.ed1);
        edwarm = findViewById(R.id.edwarm);
        edeclosion = findViewById(R.id.edeclosion);
        edmating = findViewById(R.id.edmating);
        edenter = findViewById(R.id.edenter);
        edpart = findViewById(R.id.edpart);
        btn1 = findViewById(R.id.btn1);

        t1.setText(name2);
        ed1.setText(name1);
        edwarm.setText(warm);
        edeclosion.setText(eclosion);
        edmating.setText(mating);
        edenter.setText(enter);
        edpart.setText(part);

        btn1.setOnClickListener(Lsave);
    }
    private Button.OnClickListener Lsave = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {

            if(edwarm.getText().length() == 0 && edeclosion.getText().length() == 0
                    && edmating.getText().length() == 0 && edenter.getText().length() == 0
                    && edpart.getText().length() == 0){//here
                Toast.makeText(getApplicationContext(),"請輸入日期",Toast.LENGTH_SHORT).show();
            }else{
                if (edwarm.getText().length() == 0 && (!(edeclosion.getText().length() == 0)
                        || !(edmating.getText().length() == 0) || !(edenter.getText().length() == 0)
                        || !(edpart.getText().length() == 0))){
                    Toast.makeText(getApplicationContext(),"請按照順程序輸入時間",Toast.LENGTH_SHORT).show();
                }else if (edwarm.getText().length() == 0 || edeclosion.getText().length() == 0 && (
                        !(edmating.getText().length() == 0) || !(edenter.getText().length() == 0)
                                || !(edpart.getText().length() == 0))){
                    Toast.makeText(getApplicationContext(),"請按照順程序輸入時間",Toast.LENGTH_SHORT).show();
                }else if (!(edwarm.getText().length() == 0) && edeclosion.getText().length() == 0
                        && edmating.getText().length() == 0 && edenter.getText().length() == 0
                        && edpart.getText().length() == 0 ){
                    String t1 = edwarm.getText().toString();

                    Date date1 = null;
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
                    format1.setLenient(false);
                    Log.d("mylog", "t1: "+t1);

                    try {
                        date1 = format1.parse(t1);
                        update();
                        backToqb();
                    }catch (Exception e) {
                        Toast.makeText(qbupdate.this,"輸入格式錯誤",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else if (edwarm.getText().length() == 0 || edeclosion.getText().length() == 0
                        || edmating.getText().length() == 0 && (!(edenter.getText().length() == 0)
                        || !(edpart.getText().length() == 0))){
                    Toast.makeText(getApplicationContext(),"請按照順程序輸入時間",Toast.LENGTH_SHORT).show();
                }else{
                    String t1 = edwarm.getText().toString();
                    String t2 = edeclosion.getText().toString();
                    String t3 = edmating.getText().toString();
                    String t4 = edenter.getText().toString();
                    String t5 = edpart.getText().toString();

                    Date date1 = null;
                    Date date2 = null;
                    Date date3 = null;
                    Date date4 = null;
                    Date date5 = null;

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    format.setLenient(false);

                    try {
                        if (!t1.equals("") && !t2.equals("") && t3.equals("")){
                            date1 = format.parse(t1);
                            date2 = format.parse(t2);
                            update();
                            backToqb();
                        }else if (!t1.equals("") && !t2.equals("") && !t3.equals("") && t4.equals("")){
                            date1 = format.parse(t1);
                            date2 = format.parse(t2);
                            date3 = format.parse(t3);
                            Log.d("mylog", "date3: "+date3);
                            update();
                            backToqb();
                        }else if (!t1.equals("") && !t2.equals("") && !t3.equals("")
                                && !t4.equals("") && t5.equals("")){
                            date1 = format.parse(t1);
                            date2 = format.parse(t2);
                            date3 = format.parse(t3);
                            date4 = format.parse(t4);
                            update();
                            backToqb();
                        }else if (!t1.equals("") && !t2.equals("") && !t3.equals("")
                                && !t4.equals("") && !t5.equals("")){
                            date1 = format.parse(t1);
                            date2 = format.parse(t2);
                            date3 = format.parse(t3);
                            date4 = format.parse(t4);
                            date5 = format.parse(t5);
                            update();
                            backToqb();
                        }
                    }catch(Exception e) {
                        Toast.makeText(qbupdate.this, "輸入格式錯誤", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    public void update(){
        if (db.update(ed1.getText().toString(),edwarm.getText().toString(),edeclosion.getText().toString(),
                edmating.getText().toString(),edenter.getText().toString(),edpart.getText().toString(),
                time,name2,id)==true){
            Toast.makeText(getApplicationContext(),"資料已修改",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
        }
    }
    public void backToqb() {
        Intent intent1 = new Intent(qbupdate.this,queenbee.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_CODE,intent1);
        finish();
    }
}