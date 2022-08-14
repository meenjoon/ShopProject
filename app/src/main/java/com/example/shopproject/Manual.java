package com.example.shopproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Manual extends AppCompatActivity {
    TextView seller;
    TextView buyer;
    TextView back;
    Context mContext;
    Toast toast1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual);
        seller = (TextView)findViewById(R.id.seller);
        buyer = (TextView)findViewById(R.id.buyer);
        mContext = getApplicationContext();

        Spannable span = (Spannable) seller.getText();
        seller.setMovementMethod(LinkMovementMethod.getInstance());
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                toast1 =Toast.makeText(mContext, "1. + 버튼을 클릭하여 판매 할 품목을 올린다." +
                        "        2. 판매 할 상품의 설명을 게재한다.", Toast.LENGTH_SHORT);


                toast1.show();
            }
        }, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        seller.setMovementMethod(LinkMovementMethod.getInstance());


        span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new BackgroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span.setSpan(new RelativeSizeSpan(1.5f), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        //-------------------------------------------------

        Spannable span2 = (Spannable) buyer.getText();
        buyer.setMovementMethod(LinkMovementMethod.getInstance());
        span2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {


                Toast.makeText(mContext, "1. 원하는 물품에 들어간다.\n" +
                        "2. 채팅방이나 구입하기 버튼을 이용한다.", Toast.LENGTH_LONG).show();

            }
        }, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        buyer.setMovementMethod(LinkMovementMethod.getInstance());


        span2.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span2.setSpan(new BackgroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        span2.setSpan(new RelativeSizeSpan(1.5f), 0, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


        back = (TextView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = new Intent(Manual.this, MainActivity.class);
//                startActivity(intent1);
                finish();
            }
        });

    }
}