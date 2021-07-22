package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Search extends AppCompatActivity {
    Button btnconfirm;
    ImageButton ibself;
    Spinner spname,spdate;
    LinearLayout L2;
    SqliteDatabase dbSearch;
    TextView title,ttotal,textview2,tsum;
    String subject,time3,text1,totalnum1="";
    int time1,time2,d,dn;
    detaildatabase db;
    ArrayList<Integer> sugar = new ArrayList<Integer>();
    ArrayList<Integer> totalnum = new ArrayList<Integer>();
    ArrayList<String> sumall = new ArrayList<String>();
    ArrayList<Integer> time = new ArrayList<Integer>();
    ArrayList<Integer> pollen = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new detaildatabase(this);

        Intent intent = getIntent();
        text1 = intent.getStringExtra("tview");

        dbSearch = new SqliteDatabase(this);
        //Spinner name setting
        ArrayList<String> list = dbSearch.getAllLabels();
        spname=findViewById(R.id.spname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_style,R.id.t1,list);
        spname.setAdapter(adapter);

        //Spinner date setting
        spdate=findViewById(R.id.spdate);
        ArrayList day = new ArrayList<String>();
        day.add("---");
        day.add("近一周");
        day.add("近一月");
        ArrayAdapter<String> adapterday = new ArrayAdapter<String>(this,R.layout.spinner_style,R.id.t1,day);
        spdate.setAdapter(adapterday);

        btnconfirm=findViewById(R.id.btnconfirm);
        ibself=findViewById(R.id.ibself);

        title=findViewById(R.id.title);
        ttotal=findViewById(R.id.ttotal);
        tsum=findViewById(R.id.tsum);
        textview2=findViewById(R.id.textview2);
        title.setText(text1);

        L2=findViewById(R.id.L2);
        btnconfirm.setOnClickListener(Lconfirm);
        ibself.setOnClickListener(Lself);

        spname.setOnItemSelectedListener(select);
        spdate.setOnItemSelectedListener(select);

    }
    private Spinner.OnItemSelectedListener select = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()){
                case R.id.spname:
                    subject = spname.getSelectedItem().toString();
                    break;
                case R.id.spdate:
                    Calendar rightnow = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date1 = rightnow.getTime();
                    dn = Integer.parseInt(sdf.format(date1));
                    time3 = spdate.getSelectedItem().toString();
                    if (time3.equals("近一周")){
                        rightnow.add(Calendar.DAY_OF_YEAR, -7);
                        Date date = rightnow.getTime();
                        d = Integer.parseInt(sdf.format(date));
                        Log.d("mylog", "nowtimeb: "+d);
                        Log.d("mylog", "nowtime: "+dn);
                    }else if (time3.equals("近一月")){
                        rightnow.add(Calendar.DAY_OF_YEAR, -30);
                        Date date = rightnow.getTime();
                        d = Integer.parseInt(sdf.format(date));
                        Log.d("mylog", "nowtimeb: "+d);
                        Log.d("mylog", "nowtime: "+dn);
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private Button.OnClickListener Lself = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {

            LayoutInflater inflater = LayoutInflater.from(Search.this);
            final View view = inflater.inflate(R.layout.alertdailog_style, null);

            new AlertDialog.Builder(Search.this)
                    .setTitle("--- 查詢時間 ---")
                    .setView(view)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText1 = (EditText) (view.findViewById(R.id.ed1));
                            EditText editText2 = (EditText) (view.findViewById(R.id.ed2));
                            String t1 = editText1.getText().toString();
                            String t2 = editText2.getText().toString();

                            Date date1 = null;
                            Date date2 = null;
                            DateFormat format = new SimpleDateFormat("yyyyMMdd");
                            format.setLenient(false);
                            
                            try {
                                int sum=0;
                                int time5=0;
                                int part=0;
                                String ppart="";
                                totalnum1="";
                                date1 = format.parse(t1);
                                date2 = format.parse(t2);
                                Log.d("mylog", "date1: "+date1);
                                time1 = Integer.parseInt(editText1.getText().toString());
                                time2 = Integer.parseInt(editText2.getText().toString());
                                Cursor cursor3 = db.getsugar(subject,time1,time2);
                                Log.d("mylog", "資料庫: "+cursor3.getCount());

                                if (text1.equals("糖水")){
                                    if (cursor3.getCount()>0){
                                        while (cursor3.moveToNext()){
                                            int sugarvalue = cursor3.getInt(13);
                                            int time4 = cursor3.getInt(18);
                                            sugar.add(sugarvalue);
                                            time.add(time4);
                                            Log.d("mylog", "sugarvalue: "+sugarvalue);

                                            for (int i=0;i<sugar.size();i++){
                                                sum = sum+ sugar.get(i);
                                                part = sugar.get(i);
                                                time5 = time.get(i);
                                                totalnum1 = totalnum1+time5+" : "+part+"毫升糖水"+'\n';
                                            }
                                            textview2.setText(totalnum1);
                                            sugar.clear();
                                            time.clear();
                                            if (sum<=2500){
                                                L2.setBackgroundResource(R.drawable.background5);
                                                tsum.setText("總糖水量 : "+sum);
                                            }else if (sum>2500){
                                                L2.setBackgroundResource(R.drawable.background6);
                                                tsum.setText("總糖水量 : "+sum);
                                            }
                                            Log.d("mylog", "sum1: "+sum);
                                        }
                                    }else if (cursor3.getCount()==0){
                                        Toast.makeText(Search.this,"無紀錄",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (text1.equals("蜂量")){
                                    if (cursor3.getCount()>0){
                                        while (cursor3.moveToNext()){
                                            int total = cursor3.getInt(4)-1;
                                            int time4 = cursor3.getInt(18);
                                            if (total != -1) {
                                                totalnum.add(total);
                                                time.add(time4);
                                                Log.d("mylog", "蜂量: "+total);
                                                Log.d("mylog", "時間: "+time1);
                                                for (int i=0;i<totalnum.size();i++){
                                                    sum = totalnum.get(i);
                                                    time5 = time.get(i);
                                                    totalnum1 = totalnum1+time5+" : "+"總蜂量約為 : "+sum*6000+" 隻"+'\n';
                                                    Log.d("mylog", "總蜂量約為: "+totalnum1);
                                                }
                                                ttotal.setText(totalnum1);
                                                totalnum.clear();
                                                time.clear();
                                            }
                                        }
                                    }
                                }
                                if (text1.equals("蜂糧")){
                                    if (cursor3.getCount()>0){
                                        while (cursor3.moveToNext()){
                                            int total = cursor3.getInt(14);
                                            int time4 = cursor3.getInt(18);
                                            if (!(total ==-1)){
                                                totalnum.add(total);
                                                time.add(time4);
                                                Log.d("mylog", "totalnum: "+total);
                                                for (int i=0;i<totalnum.size();i++){
                                                    sum = sum+totalnum.get(i);
                                                    part = totalnum.get(i);
                                                    time5 = time.get(i);
                                                    totalnum1 = totalnum1+time5 +" : "+part+" 餵蜂糧量公克"+'\n';
                                                    Log.d("mylog", "sum: "+sum);
                                                    Log.d("mylog", "總蜂量約為: "+totalnum1);
                                                }
                                                totalnum.clear();
                                                time.clear();
                                                ttotal.setText(totalnum1);
                                                tsum.setText("總餵蜂糧量: "+sum+"公克");
                                            }
                                        }
                                    }
                                }
                                if (text1.equals("花粉量")){
                                    if (cursor3.getCount()>0){
                                        while (cursor3.moveToNext()){
                                            int total = cursor3.getInt(4)-1;
                                            int pollenvalue = cursor3.getInt(8)-1;
                                            int time4 = cursor3.getInt(18);
                                            if (pollenvalue>-1){
                                                time.add(time4);
                                                DecimalFormat integerformat = new DecimalFormat("0%");
                                                double nu1 = (double) pollenvalue/(double) total;
                                                String ratio = integerformat.format(nu1);
                                                sumall.add(ratio);
                                                for (int i=0;i<sumall.size();i++){
                                                    ppart = sumall.get(i);
                                                    time5 = time.get(i);
                                                    totalnum1 = totalnum1+time5+" : "+ppart+"儲粉量"+'\n';
                                                }
                                                Log.d("mylog", "nu1: "+nu1);

                                                ttotal.setText(totalnum1);
                                                //totalnum.clear();
                                                //pollen.clear();
                                                time.clear();
                                                sumall.clear();
                                            }
                                        }
                                    }
                                }
                                if (text1.equals("蜜量")){
                                    if (cursor3.getCount()>0){
                                        while (cursor3.moveToNext()){
                                            int total = cursor3.getInt(4)-1;
                                            int honeyvalue = cursor3.getInt(7)-1;
                                            int time4 = cursor3.getInt(18);
                                            if (honeyvalue != -1 && total!=-1){
                                                time.add(time4);
                                                DecimalFormat integerformat = new DecimalFormat("0%");
                                                double nu1 = (double) honeyvalue/(double) total;
                                                String ratio = integerformat.format(nu1);
                                                sumall.add(ratio);
                                                for (int i=0;i<sumall.size();i++){
                                                    time5 = time.get(i);
                                                    ppart = sumall.get(i);
                                                    totalnum1 = totalnum1+time5+" : "+ppart+"儲蜜量"+'\n';
                                                }
                                                ttotal.setText(totalnum1);
                                                time.clear();
                                                sumall.clear();
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Toast.makeText(Search.this,"輸入格式錯誤",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    };


    private Button.OnClickListener Lconfirm = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            /*Cursor cursor = db.getsugar("test",20210701,20210702);
            Log.d("mylog", "cursor: "+cursor.getCount());
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    int sugarvalue = cursor.getInt(13);
                    Log.d("mylog", "sugarvalue: "+sugarvalue);}
            }*/
            int sum=0;
            int time5=0;
            int part=0;
            String ppart="";
            totalnum1="";
            if (!subject.equals("所有")&&!time3.equals("---")){
                Cursor cursor3 = db.getsugar(subject,d,dn);
                Log.d("mylog", "cursor3: "+cursor3.getCount());
                if (text1.equals("糖水")){
                    if (cursor3.getCount()>0){
                    while (cursor3.moveToNext()){
                        int sugarvalue = cursor3.getInt(13);
                        int time4 = cursor3.getInt(18);
                        sugar.add(sugarvalue);
                        time.add(time4);
                        Log.d("mylog", "sugarvalue: "+sugarvalue);

                        for (int i=0;i<sugar.size();i++){
                            sum = sum+ sugar.get(i);
                            part = sugar.get(i);
                            time5 = time.get(i);
                            totalnum1 = totalnum1+time5+" : "+part+"毫升糖水"+'\n';
                        }
                        textview2.setText(totalnum1);
                        sugar.clear();
                        time.clear();
                        if (sum<=2500){
                            L2.setBackgroundResource(R.drawable.background5);
                            tsum.setText("總糖水量 : "+sum);
                        }else if (sum>2500){
                            L2.setBackgroundResource(R.drawable.background6);
                            tsum.setText("總糖水量 : "+sum);
                        }
                        Log.d("mylog", "sum1: "+sum);
                    }
                }else if (cursor3.getCount()==0){
                    Toast.makeText(Search.this,"無紀錄",Toast.LENGTH_SHORT).show();
                    }
                }
                if (text1.equals("蜂量")){
                    if (cursor3.getCount()>0){
                        while (cursor3.moveToNext()){
                            int total = cursor3.getInt(4)-1;
                            int time4 = cursor3.getInt(18);
                            time.add(time4);
                            Log.d("mylog", "時間長度: "+time1);

                            if (total != -1) {
                                totalnum.add(total);
                                //Log.d("mylog", "蜂量: "+total);
                                for (int i=0;i<totalnum.size();i++){
                                    sum = totalnum.get(i);
                                    time5 = time.get(i);
                                    totalnum1 = totalnum1+time5+" : "+"總蜂量約為 : "+sum*6000+" 隻"+'\n';

                                }
                                totalnum.clear();
                                time.clear();
                                ttotal.setText(totalnum1);
                            }
                        }
                    }
                }
                if (text1.equals("蜂糧")){
                    if (cursor3.getCount()>0){
                        while (cursor3.moveToNext()){
                            int total = cursor3.getInt(14);
                            int time4 = cursor3.getInt(18);
                            if (!(total ==-1)){
                                totalnum.add(total);
                                time.add(time4);
                                Log.d("mylog", "totalnum: "+total);
                                for (int i=0;i<totalnum.size();i++){
                                    sum = sum+totalnum.get(i);
                                    part = totalnum.get(i);
                                    time5 = time.get(i);
                                    totalnum1 = totalnum1+time5 +" : "+part+" 餵蜂糧量公克"+'\n';
                                    Log.d("mylog", "sum: "+sum);
                                    Log.d("mylog", "總蜂量約為: "+totalnum1);
                                }
                                totalnum.clear();
                                time.clear();
                                ttotal.setText(totalnum1);
                                tsum.setText("總餵蜂糧量: "+sum+"公克");
                            }
                        }
                    }
                }
                if (text1.equals("花粉量")){
                    if (cursor3.getCount()>0){
                        while (cursor3.moveToNext()){
                            int total = cursor3.getInt(4)-1;
                            int pollenvalue = cursor3.getInt(8)-1;
                            int time4 = cursor3.getInt(18);
                            if (pollenvalue>-1){
                                //totalnum.add(total);
                                //pollen.add(pollenvalue);
                                time.add(time4);
                                DecimalFormat integerformat = new DecimalFormat("0%");
                                double nu1 = (double) pollenvalue/(double) total;
                                String ratio = integerformat.format(nu1);
                                sumall.add(ratio);
                                for (int i=0;i<sumall.size();i++){
                                    ppart = sumall.get(i);
                                    time5 = time.get(i);
                                    totalnum1 = totalnum1+time5+" : "+ppart+"儲粉量"+'\n';
                                }
                                Log.d("mylog", "nu1: "+nu1);
                                ttotal.setText(totalnum1);
                                time.clear();
                                sumall.clear();
                            }
                        }
                    }
                }
                if (text1.equals("蜜量")){
                    if (cursor3.getCount()>0){
                        while (cursor3.moveToNext()){
                            int total = cursor3.getInt(4)-1;
                            int honeyvalue = cursor3.getInt(7)-1;
                            int time4 = cursor3.getInt(18);
                            if (honeyvalue != -1 && total!=-1){
                                time.add(time4);
                                DecimalFormat integerformat = new DecimalFormat("0%");
                                double nu1 = (double) honeyvalue/(double) total;
                                String ratio = integerformat.format(nu1);
                                sumall.add(ratio);
                                for (int i=0;i<sumall.size();i++){
                                    time5 = time.get(i);
                                    ppart = sumall.get(i);
                                    totalnum1 = totalnum1+time5+" : "+ppart+"儲蜜量"+'\n';
                                }
                                ttotal.setText(totalnum1);
                                time.clear();
                                sumall.clear();
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(Search.this,"請選擇查詢值",Toast.LENGTH_SHORT).show();
            }
        }
    };
}