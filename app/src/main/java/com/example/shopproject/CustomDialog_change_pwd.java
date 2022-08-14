package com.example.shopproject;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomDialog_change_pwd extends Dialog implements AdapterView.OnItemSelectedListener{

    private EditText change_pwd;
    private EditText change_pwd2;
    private  Button change_button;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 변수 생성
    private DatabaseReference mDatabaseRef; // 파이어베이스 실시간 데이터베이스 변수 생성


    public CustomDialog_change_pwd(Context context, String contents)
    {
        super(context);
        setContentView(R.layout.custom_dialog_change_pwd); //custom_dialog_change_pwd 적용될 xml 연결
        change_pwd  = findViewById(R.id.change_pwd);//custom_dialog_change_pwd 적용될 xml 연결
        change_pwd2 = findViewById(R.id.change_pwd2);//custom_dialog_change_pwd 적용될 xml 연결
        change_button = findViewById(R.id.change_button);//custom_dialog_change_pwd 적용될 xml 연결

        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 인스턴스 획득
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject"); // 파이어베이스 DB 인스턴스 획득 및 shopproject 상위 가지 형성

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //

        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = change_pwd.getText().toString().trim();
                String pwd2 = change_pwd2.getText().toString().trim();

                mDatabaseRef.child("UserAccount").child(contents).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        UserAccount user = snapshot.getValue(UserAccount.class);

                        if(pwd.equals(pwd2)) {
                            mFirebaseAuth.sendPasswordResetEmail(user.getEmailId()).addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "설정하신 이메일로 비밀번호 변경 메세지를 발송하였습니다.("+user.getEmailId()+")", Toast.LENGTH_LONG).show();
                                    dismiss();
                                }
                            });
//                            firebaseUser.updatePassword(pwd).addOnCompleteListener(task -> {
//                                if (task.isSuccessful()) {
//                                    mDatabaseRef.child("UserAccount").child(contents).child("password").setValue(pwd);
//                                    Toast.makeText(context, "비밀번호가 변경 되었습니다.", Toast.LENGTH_LONG).show();
//                                    dismiss();
//
//                                } else {
//                                    Toast.makeText(context, "비밀번호 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        }
                        else {
                            Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}