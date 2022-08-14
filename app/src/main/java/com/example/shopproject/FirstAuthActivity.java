package com.example.shopproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FirstAuthActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(SaveSharedPreference.getUserName(FirstAuthActivity.this).length() == 0) {
            // call Login Activity
            Intent intent = new Intent(FirstAuthActivity.this, Log_phone.class);
            startActivity(intent);
            this.finish();
        } else {
            // Call Next Activity
            Intent intent = new Intent(FirstAuthActivity.this, MainActivity.class);
            intent.putExtra("STD_NUM", SaveSharedPreference.getUserName(this).toString());
            startActivity(intent);
            this.finish();
        }
    }
}