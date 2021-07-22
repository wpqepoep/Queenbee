package com.top.queenbee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    TextView taccount,tcreate;
    EditText edtele,edusername,edpassword;
    Button btnregister;
    ImageButton ibenter;


    private SharedPreferences user;//儲存帳密
    String account="";//帳號
    int count=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taccount=(TextView)findViewById(R.id.taccount);
        tcreate=(TextView)findViewById(R.id.tcreate);
        edtele=(EditText)findViewById(R.id.edtele);
        edusername=(EditText)findViewById(R.id.edusername);
        edpassword=(EditText)findViewById(R.id.edpassword);
        btnregister=(Button) findViewById(R.id.btnregister);
        ibenter=(ImageButton)findViewById(R.id.ibenter);
        ibenter.setOnClickListener(Lenter);
        btnregister.setOnClickListener(Lregister);



    }

    private Button.OnClickListener Lregister = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
                if (!edtele.getText().toString().equals("") &&
                        !edusername.getText().toString().equals("") &&
                        !edpassword.getText().toString().equals("")) {
                    user = getSharedPreferences(edusername.getText().toString(), MODE_PRIVATE);
                    user.edit()
                            .putString("account", edusername.getText().toString())
                            .putString("password", edpassword.getText().toString())
                            .putString("tele", edtele.getText().toString())
                            .commit();
                    Log.d("mylog","complete");
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, signin.class);
                    startActivity(intent1);
                    edtele.setText("");
                    edusername.setText("");
                    edpassword.setText("");
                    Log.d("mylog","register");
                } else {
                    Toast toast1 = Toast.makeText(MainActivity.this, "輸入格式有誤，請再次確認", Toast.LENGTH_LONG);
                    toast1.show();
                    Log.d("mylog","all blank");
                }
            }
    };
    private Button.OnClickListener Lenter = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent u1 = new Intent(MainActivity.this,signin.class);
            startActivity(u1);
        }
    };
}
