package com.example.shopproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_Activity extends AppCompatActivity implements SendEventListener {

    BottomNavigationView bottomNavigationView;

    static String loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bottomNavigationView = findViewById(R.id.bottomNavi); //만들었던 main_activity의 bottomNavi 연결하기

        //처음화면 설정
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame,new Fragment1_home()).commit();

        //바텀 네비게이션 뷰 안의 아이템 설정(각 아이템 클릭 이벤트)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //각 item 클릭시 id값을 가져와 replace()를 이용해 FrameLayout에 각 Fragemnt.xml띄위기
                    case R.id.item_fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment1_home()).commit();
                        break;
                    case R.id.item_fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment2_near()).commit();
                        break;
                    case R.id.item_fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment3_chat()).commit();
                        break;
                    case R.id.item_fragment4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment4_me()).commit();
                        break;

                }

                return true;
            }
        });

    }

    @Override
    public void sendmail(String s) {
        loc = s;
    }
}