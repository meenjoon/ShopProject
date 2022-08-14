package com.example.shopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView manual;
    TextView trade;
    Button logout;
    public static final int manualintent = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    public static final int tradeintent = 1002; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manual = (TextView)findViewById(R.id.manual);
        trade  = (TextView) findViewById(R.id.trade);
        logout = findViewById(R.id.logout);

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Manual.class);
                startActivityForResult(intent,manualintent);

            }
        });

        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),Trade.class);
//                startActivityForResult(intent,tradeintent);

                Intent intent = new Intent(getApplicationContext(),Trade.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Log_phone.class);
                startActivity(intent);
                finish();
            }
        });



    }
}