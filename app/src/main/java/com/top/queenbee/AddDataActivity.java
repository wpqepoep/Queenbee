package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddDataActivity extends AppCompatActivity {
    Spinner sptotalnum,spwarm,speclosion,sphoney,sppollen,spempty,spqueen,spplus,spminus;
    TextView ttime,tname;
    EditText edsugar,edfood,edfrom,edto,eddetail;
    RadioGroup rdqueenb;
    RadioButton cbyes,cbnull;
    Button cancelBt,btnsave,shareBt;
    detaildatabase mydb;
    String stcbyes,stcbnyll,d;
    int ds;
    int intotalnum,intwarm,inteclosion,inthoney,intpollen,intempty,intqueen,intplus,intminus;


    public static int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        mydb = new detaildatabase(this);

        Intent intent = getIntent(); //取得傳入的 Intent 物件
        Bundle bundlename = intent.getExtras();
        String name1 = bundlename.getString("bundlename");
        d=bundlename.getString("selectedDate");
        ds = Integer.parseInt(d);
        Log.d("mylog","savetime="+ds);

        //Textview
        tname=findViewById(R.id.tname);
        tname.setText(name1);
        ttime=findViewById(R.id.ttime);
        ttime.setText(d);

        //Spinner
        sptotalnum =findViewById(R.id.sptotalnum);
        spwarm=findViewById(R.id.spwarm);
        speclosion=findViewById(R.id.speclosion);
        sphoney=findViewById(R.id.sphoney);
        sppollen=findViewById(R.id.sppollen);
        spempty=findViewById(R.id.spempty);
        spqueen=findViewById(R.id.spqueen);
        spplus=findViewById(R.id.spplus);
        spminus=findViewById(R.id.spminus);

        //Edittext
        edsugar=findViewById(R.id.edsugar);
        edfood=findViewById(R.id.edfood);
        edfrom=findViewById(R.id.edfrom);
        edto=findViewById(R.id.edto);
        eddetail=findViewById(R.id.eddetail);

        //Radiogroup
        rdqueenb=findViewById(R.id.rdqueenb);
        cbyes=findViewById(R.id.cbyes);
        cbnull=findViewById(R.id.cbnull);
        rdqueenb.setOnCheckedChangeListener(radiobtnchk);

        //Spinner setting
        ArrayList num0 = new ArrayList<Integer>();
        num0.add("---");
        for (int i = 0; i <= 100; i++) {
            num0.add(Integer.toString(i)+"埤");
        }
        ArrayAdapter<Integer> spinnerArrayAdapter0 = new ArrayAdapter<Integer>(
                this,R.layout.spinner_style, num0);

        spinnerArrayAdapter0.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        sptotalnum.setAdapter(spinnerArrayAdapter0);

        ArrayList num = new ArrayList<Integer>();
        num.add("---");
        for (int i = 0; i <= 100; i++) {
            num.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this,R.layout.spinner_style, num);

        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spwarm.setAdapter(spinnerArrayAdapter);
        speclosion.setAdapter(spinnerArrayAdapter);
        sphoney.setAdapter(spinnerArrayAdapter);
        sppollen.setAdapter(spinnerArrayAdapter);
        spempty.setAdapter(spinnerArrayAdapter);

        ArrayList num1 = new ArrayList<Integer>();
        num1.add("---");
        for (int i = 0; i <= 20; i++) {
            num1.add(Integer.toString(i)+"個");
        }
        ArrayAdapter<Integer> spinnerArrayAdapter1 = new ArrayAdapter<Integer>(
                this,R.layout.spinner_style, num1);
        spqueen.setAdapter(spinnerArrayAdapter1);

        ArrayList num2 = new ArrayList<Integer>();
        num2.add("---");
        for (int i = 0; i <= 15; i++) {
            num2.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter2 = new ArrayAdapter<Integer>(
                this,R.layout.spinner_style, num2);

        spplus.setAdapter(spinnerArrayAdapter2);
        spminus.setAdapter(spinnerArrayAdapter2);

        //Set Spinner listener
        sptotalnum.setOnItemSelectedListener(select);
        spwarm.setOnItemSelectedListener(select);
        speclosion.setOnItemSelectedListener(select);
        sphoney.setOnItemSelectedListener(select);
        sppollen.setOnItemSelectedListener(select);
        spempty.setOnItemSelectedListener(select);
        spqueen.setOnItemSelectedListener(select);
        spplus.setOnItemSelectedListener(select);
        spminus.setOnItemSelectedListener(select);

        //cancelBt = findViewById(R.id.cacelButtonId);

        //Button setting
        btnsave =findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                backTodetail();
            }
        });

    }

    private RadioGroup.OnCheckedChangeListener radiobtnchk = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.cbyes){
                stcbyes = "true";
            }else{
                stcbyes = "false";
            }
            if (checkedId == R.id.cbnull){
                stcbnyll = "true";
            }else{
                stcbnyll = "false";
            }
        }
    };

    //spinner item select
    private Spinner.OnItemSelectedListener select = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()){
                case R.id.sptotalnum:
                    intotalnum = (int) sptotalnum.getItemIdAtPosition(position);
                    break;
                case R.id.spwarm:
                    intwarm = (int) spwarm.getItemIdAtPosition(position);
                    break;
                case R.id.speclosion:
                    inteclosion= (int) speclosion.getItemIdAtPosition(position);
                    break;
                case R.id.sphoney:
                    inthoney = (int) sphoney.getItemIdAtPosition(position);
                    break;
                case R.id.sppollen:
                    intpollen = (int) sppollen.getItemIdAtPosition(position);
                    break;
                case R.id.spempty:
                    intempty = (int) spempty.getItemIdAtPosition(position);
                    break;
                case R.id.spqueen:
                    intqueen = (int) spqueen.getItemIdAtPosition(position);
                    break;
                case R.id.spplus:
                    intplus = (int) spplus.getItemIdAtPosition(position);
                    break;
                case R.id.spminus:
                    intminus = (int) spminus.getItemIdAtPosition(position);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void insertData(){
        long l = -1;

        //Date date = new Date();
        //String d = (String) android.text.format.DateFormat.format("yyyyMd",date);

        if(tname.getText().length() == 0){//here
            Toast.makeText(getApplicationContext(),"請輸入文字",Toast.LENGTH_SHORT).show();
        }
        else{
            l = mydb.insertData(
                    tname.getText().toString(),//here
                    stcbyes,stcbnyll, intotalnum, intwarm,inteclosion,inthoney,
                    intpollen,intempty,intqueen,intplus,intminus,
                    edsugar.getText().toString(),edfood.getText().toString(),edfrom.getText().toString(),
                    edto.getText().toString(), eddetail.getText().toString(),ds);
            Log.d("mylog","tname="+tname.getText().toString());
            Log.d("mylog","stcbyes="+stcbyes);
            Log.d("mylog","stcbnyll="+stcbnyll);
            Log.d("mylog","sttotalnum="+intotalnum);
            Log.d("mylog","stwarm="+intwarm);
            Log.d("mylog","steclosion="+inteclosion);
            Log.d("mylog","sthoney="+inthoney);
            Log.d("mylog","stpollen="+intpollen);
            Log.d("mylog","stempty="+intempty);
            Log.d("mylog","stqueen="+intqueen);
            Log.d("mylog","stplus="+intplus);
            Log.d("mylog","stminus="+intminus);
            Log.d("mylog","edsugar="+edsugar.getText().toString());
            Log.d("mylog","edfrom="+edfrom.getText().toString());
            Log.d("mylog","edto="+edto.getText().toString());
            Log.d("mylog","eddetail="+eddetail.getText().toString());
            Log.d("mylog","date="+ds);
        }

        if(l>=0){
            Toast.makeText(getApplicationContext(),"紀錄成功",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "紀錄失敗", Toast.LENGTH_SHORT).show();
        }
    }

    public void backTodetail()
    {
        Intent intent1 = new Intent(AddDataActivity.this,detail.class);
        intent1.putExtra("bundlename",tname.getText().toString());
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(RESULT_CODE,intent1);
        finish();
    }
}