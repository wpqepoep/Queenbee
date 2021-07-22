package com.top.queenbee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Analysis extends AppCompatActivity {
    Button btnsugar,btnbee,btnbeef,btnflower,btnqueenbee,btnqueen,btnhoney;
    String ttext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        //Textview
        btnsugar=findViewById(R.id.btnsugar);
        btnbee=findViewById(R.id.btnbee);
        btnbeef=findViewById(R.id.btnbeef);
        btnflower=findViewById(R.id.btnflower);
        btnqueenbee=findViewById(R.id.btnqueenbee);
        btnqueen=findViewById(R.id.btnqueen);
        btnhoney=findViewById(R.id.btnhoney);

        btnsugar.setOnClickListener(Lanalysis);
        btnbee.setOnClickListener(Lanalysis);
        btnbeef.setOnClickListener(Lanalysis);
        btnflower.setOnClickListener(Lanalysis);
        btnqueenbee.setOnClickListener(Lanalysis);
        btnqueen.setOnClickListener(Lanalysis);
        btnhoney.setOnClickListener(Lanalysis);
    }
    private Button.OnClickListener Lanalysis = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnsugar:
                    Intent intent = new Intent(Analysis.this,Search.class);
                    ttext="糖水";
                    intent.putExtra("tview",ttext);
                    startActivity(intent);
                    break;
                case R.id.btnbee:
                    Intent intent1 = new Intent(Analysis.this,Search.class);
                    ttext="蜂量";
                    intent1.putExtra("tview",ttext);
                    startActivity(intent1);
                    break;
                case R.id.btnbeef:
                    Intent intent2 = new Intent(Analysis.this,Search.class);
                    ttext="蜂糧";
                    intent2.putExtra("tview",ttext);
                    startActivity(intent2);
                    break;
                case R.id.btnflower:
                    Intent intent3 = new Intent(Analysis.this,Search.class);
                    ttext="花粉量";
                    intent3.putExtra("tview",ttext);
                    startActivity(intent3);
                    break;
                case R.id.btnqueenbee:
                    Intent intent4 = new Intent(Analysis.this,Search.class);
                    ttext="女王蜂週期";
                    intent4.putExtra("tview",ttext);
                    startActivity(intent4);
                    break;
                case R.id.btnqueen:
                    Intent intent5 = new Intent(Analysis.this,Search.class);
                    ttext="王台";
                    intent5.putExtra("tview",ttext);
                    startActivity(intent5);
                    break;
                case R.id.btnhoney:
                    Intent intent6 = new Intent(Analysis.this,Search.class);
                    ttext="蜜量";
                    intent6.putExtra("tview",ttext);
                    startActivity(intent6);
                    break;
            }
        }
    };
}