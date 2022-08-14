package com.example.shopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Buy extends AppCompatActivity {
    public static final int manualintent = 1004; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_view);

        Button mainmenu_back = (Button) findViewById( R.id.mainmenu_back);

        mainmenu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Buy.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}