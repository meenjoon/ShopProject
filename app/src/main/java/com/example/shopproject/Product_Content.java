package com.example.shopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Product_Content extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_content);

        Button recyclerview_item_button;

        recyclerview_item_button = (Button) findViewById(R.id.product_backmenu);



        recyclerview_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context context = view.getContext();
//                Intent intent = new Intent(context,Trade.class);
//                context.startActivity(intent);
                    finish();
            }
        });


    }


}