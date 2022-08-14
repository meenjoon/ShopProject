package com.example.shopproject;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomDialog_find_id extends Dialog implements AdapterView.OnItemSelectedListener{

    private EditText find_id_name;
    private EditText find_id_phone;
    private  Button find_id_button;

    private DatabaseReference mDatabaseRef; // 파이어베이스 실시간 데이터베이스 변수 생성

    public CustomDialog_find_id(Context context)
    {
        super(context);
        setContentView(R.layout.custom_dialog_find_id); // CustomDialog_find_id에 적용될 xml 연결
        find_id_name  = findViewById(R.id.find_id_name); // CustomDialog_find_id에 적용될 xml 연결
        find_id_phone = findViewById(R.id.find_id_phone); // CustomDialog_find_id에 적용될 xml 연결
        find_id_button = findViewById(R.id.find_id_button); // CustomDialog_find_id에 적용될 xml 연결

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject"); // 파이어베이스 DB 인스턴스 획득 및 shopproject 상위 가지 형성

        find_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = find_id_name.getText().toString().trim(); //사용자가 입력한 글자 추출
                String phone = find_id_phone.getText().toString().trim(); //사용자가 입력한 글자 추출

                mDatabaseRef.child("UserAccount").child(phone).addValueEventListener(new ValueEventListener() {
                //파이어베이스의 실시간 데이터베이스에서 shopproject-UserAccount-(내가 입력한 전화번호)의 위치애 들어간다.
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) { //작업이 완료가 되었을때

                        UserAccount user = snapshot.getValue(UserAccount.class);//UserAccount의 객체를 생성하는데 이것이 파이어베이스의 실시간 데이터베이스에서 데이터를 가져온다.

                        if (user != null && name.equals(user.getName())) {
                        //만약 파이어베이스의 실시간 데이터베이스에서 shopproject-UserAccount-(내가 입력한 전화번호)에 데이터가 null 값이아니고(내가 입력한 전화번호가 존재한다면) 또한 내가 입력한 이름과 파이어베이스의 가져온 이름과 일치한다면
                            Toast.makeText(context, "당신의 아이디는 [ " + user.getEmailId() + " ] 입니다.",Toast.LENGTH_LONG).show(); // 실시간 데이터베이스로부터 shopproject-UserAccount-(내가 입력한 전화번호)속에 있는 이메일ID 데이터를 Toast메세지로 띄운다.
                            dismiss();//Dialog 창 닫기
                        }
                        else { // 그게 아니라면
                            Toast.makeText(context, "이름과 전화번호가 일치하는 정보를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
                            //일치하는 정보가 없다고 Toast 메세지 띄우기
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { //중간에 작업에 오류가 났을때

                    }
                });

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}