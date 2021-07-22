package com.top.queenbee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.top.queenbee.SqliteDatabase.tableName;

public class detail extends AppCompatActivity {
    TextView tname;
    CalendarView calendarView;
    Button btnbee,btnanalyse,btntask;
    ImageButton ibbee;
    String selectedDate;
    detaildatabase db;
    String name,smonth,sdate,sub;
    Cursor cursor;
    int formatedate;

    public static final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new detaildatabase(this);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        sub = intent.getStringExtra("subject");

        tname=(TextView)findViewById(R.id.tname);
        tname.setText(sub);

        name=tname.getText().toString();//Select data,bundle
        calendarView=findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(set1);

        btnbee=findViewById(R.id.btnbee);
        ibbee=findViewById(R.id.ibbee);
        btnanalyse=findViewById(R.id.btnanalyse);
        btntask=findViewById(R.id.btntask);

        btnbee.setOnClickListener(LQueenB);
        ibbee.setOnClickListener(LQueenB);
        btnanalyse.setOnClickListener(LQueenB);
        btntask.setOnClickListener(LQueenB);

    }


    private CalendarView.OnDateChangeListener set1 = new CalendarView.OnDateChangeListener(){
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            if (month<10){
                smonth = "0"+Integer.toString(month+1);
            }else{
                smonth=Integer.toString(month+1);
            }
            if (dayOfMonth<10){
                sdate = "0"+Integer.toString(dayOfMonth);
            }else{
                sdate=Integer.toString(dayOfMonth);
            }
            selectedDate = (Integer.toString(year)+smonth+sdate);
            formatedate = Integer.parseInt((Integer.toString(year)+smonth+sdate));
            Log.d("mylog","formatedate="+formatedate);

            cursor = db.display(name,formatedate);
            Log.d("mylog","cursor="+cursor.getCount());

            if (cursor.getCount()>0){
                while (cursor.moveToNext()) {//重新指向
                    String id=cursor.getString(0);
                    String name=cursor.getString(1);
                    Log.d("mylog","id="+cursor.getString(0));
                    String qbyes= cursor.getString(2);
                    String qbnull= cursor.getString(3);
                    String totalnum=cursor.getString(4);
                    Log.d("mylog","totalnum="+totalnum);
                    String warm=cursor.getString(5);
                    String eclosion=cursor.getString(6);
                    String honey=cursor.getString(7);
                    String pollen=cursor.getString(8);
                    String empty=cursor.getString(9);
                    String queen=cursor.getString(10);
                    String plus=cursor.getString(11);
                    String minus=cursor.getString(12);
                    String sugar=cursor.getString(13);
                    String food=cursor.getString(14);
                    String from1=cursor.getString(15);
                    String to1=cursor.getString(16);
                    String detail=cursor.getString(17);
                    String d=cursor.getString(18);

                    Intent intent = new Intent(detail.this, UpdateActivity.class);
                    Log.d("mylog","intent="+"yes");
                    intent.putExtra("name",name);
                    intent.putExtra("qbyes",qbyes);
                    intent.putExtra("qbnull",qbnull);
                    intent.putExtra("totalnum",totalnum);
                    intent.putExtra("warm",warm);
                    intent.putExtra("eclosion",eclosion);
                    intent.putExtra("honey",honey);
                    intent.putExtra("pollen",pollen);
                    intent.putExtra("empty",empty);
                    intent.putExtra("queen",queen);
                    intent.putExtra("plus",plus);
                    intent.putExtra("minus",minus);
                    intent.putExtra("sugar",sugar);
                    intent.putExtra("food",food);
                    intent.putExtra("from1",from1);
                    intent.putExtra("to1",to1);
                    intent.putExtra("detail",detail);
                    intent.putExtra("date",d);
                    intent.putExtra("ID",id);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }else if(cursor.getCount()== 0){
                Intent intent = new Intent(detail.this,AddDataActivity.class);
                intent.putExtra("bundlename", tname.getText().toString());
                intent.putExtra("selectedDate",selectedDate);
                startActivityForResult(intent,REQUEST_CODE);
                Log.d("mylog","selectedDate="+selectedDate);
                Log.d("mylog","name="+tname.getText().toString());
                Log.d("mylog","REQUEST_CODE="+REQUEST_CODE);
            }
        }
    };

    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == AddDataActivity.RESULT_CODE) {
            tname.setText(data.getStringExtra("bundlename"));

        }
    }

    private Button.OnClickListener LQueenB = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btnbee:
                case R.id.ibbee:
                    Intent intent1 = new Intent(detail.this,queenbee.class);
                    intent1.putExtra("subject",sub);
                    startActivity(intent1);
                    break;
                case R.id.btnanalyse:
                    Intent intent = new Intent(detail.this,Analysis.class);
                    startActivity(intent);
                    break;
                case R.id.btntask:
                    break;
            }

        }
    };
}