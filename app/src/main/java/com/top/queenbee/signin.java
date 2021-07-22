package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class signin extends AppCompatActivity {
    EditText edaccount,edcode;
    Button btnconfirm,btncancel;
    CheckBox cbremember;
    ImageButton ibreg;

    String account="";
    String password="";

    private SharedPreferences spregister;
    public static final int FUNC_LOGIN=1;//定義功能常數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edaccount=(EditText)findViewById(R.id.edaccount);
        edcode=(EditText)findViewById(R.id.edcode);
        btnconfirm=(Button)findViewById(R.id.btnconfirm);
        btncancel=(Button)findViewById(R.id.btncancel);
        ibreg=(ImageButton)findViewById(R.id.ibreg);
        cbremember=(CheckBox)findViewById(R.id.cbremember);

        btnconfirm.setOnClickListener(Lconfirm);
        btncancel.setOnClickListener(L1);
        ibreg.setOnClickListener(L1);
        cbremember.setOnCheckedChangeListener(ck);

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox= preferences.getString("remember","");

        if (checkbox.equals("true")){
            Intent intent = new Intent(signin.this,home.class);
            startActivity(intent);
            Log.d("mylog", "true");
        }else if (checkbox.equals("false")){
            Toast.makeText(this,"請註冊帳號",Toast.LENGTH_SHORT).show();
        }
    }
    private Button.OnClickListener Lconfirm = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            spregister=getSharedPreferences(edaccount.getText().toString(),MODE_PRIVATE);
            account=spregister.getString("account","");
            password=spregister.getString("password","");
            if(account.equals("")){
                Toast toast=Toast.makeText(signin.this,"尚未註冊",Toast.LENGTH_LONG);
                toast.show();
            }else if(account.equals(edaccount.getText().toString()) &&
                    password.equals(edcode.getText().toString())) {
                Intent homepage = new Intent(signin.this,home.class);
                Bundle user = new Bundle();

                user.putString("account",spregister.getString("account",""));
                homepage.putExtras(user);
                startActivityForResult(homepage,1);
            }else if(account.equals(edaccount.getText().toString()) && password!=(edcode.getText().toString())){
                Toast toast=Toast.makeText(signin.this,"密碼輸入錯誤",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };
    private CheckBox.OnCheckedChangeListener ck = new CheckBox.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isChecked()){
                if ((edaccount.getText().toString().equals("") || edcode.getText().toString().equals(""))){
                    Toast.makeText(signin.this,"請輸入帳號密碼",Toast.LENGTH_SHORT).show();
                    Log.d("mylog", edaccount.getText().toString());
                }else{
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(signin.this,"開啟自動登入",Toast.LENGTH_SHORT).show();
                }
            }else if (!buttonView.isChecked()){
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                Toast.makeText(signin.this,"自動登入未開啟",Toast.LENGTH_SHORT).show();
            }
        }

    };


    private Button.OnClickListener L1 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btncancel:
                    finish();
                    break;
                case R.id.ibreg:
                    Intent intent = new Intent();
                    intent.setClass(signin.this,MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

}