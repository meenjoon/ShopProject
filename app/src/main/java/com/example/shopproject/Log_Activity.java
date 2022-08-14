package com.example.shopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;

public class Log_Activity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스

    private EditText et_email, et_pwd;
    private Button login, joinmembership, find_idpw;
    private CheckBox cb_save;

    private Context mContext;
    String id,pw;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this; // 이거 필수!
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject");

        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        login = findViewById(R.id.login);
        joinmembership = findViewById(R.id.joinmembership);
        find_idpw = findViewById(R.id.find_idpw);
        cb_save = findViewById(R.id.cb_save);

        boolean boo = PreferenceManager.getBoolean(mContext,"check"); //로그인 정보 기억하기 체크 유무 확인
        if(boo){ // 체크가 되어있다면 아래 코드를 수행 //저장된 아이디와 암호를 가져와 셋팅한다.
            et_email.setText(PreferenceManager.getString(mContext, "id"));
            et_pwd.setText(PreferenceManager.getString(mContext, "pw"));
            cb_save.setChecked(true); //체크박스는 여전히 체크 표시 하도록 셋팅
        }

        joinmembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입창 이동(인텐트 사용)
                Intent intent = new Intent(Log_Activity.this, Log_JoinMembership.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferenceManager.setString(mContext, "id", et_email.getText().toString()); //id라는 키값으로 저장
                PreferenceManager.setString(mContext, "pw", et_pwd.getText().toString()); //pw라는 키값으로 저장

                // 로그인 요청
                String strEmail = et_email.getText().toString();
                String strPwd = et_pwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(Log_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            //로그인 성공 ! ( MainActivity 창 이동)
                            Intent intent = new Intent(Log_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(Log_Activity.this,"로그인 성공.", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(Log_Activity.this,"로그인 실패.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

//        find_idpw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mFirebaseAuth.sendPasswordResetEmail(et_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(Log_Activity.this,"재설정 메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(Log_Activity.this,"이메일이 다릅니다.", Toast.LENGTH_SHORT).show();
//                    }
//                    }
//                });
//            }
//        });


        find_idpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(view.getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog);

                Intent intent = new Intent(Log_Activity.this, Reset_Password.class);
                startActivity(intent);


            }
        });


        cb_save.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) { // 체크박스 체크 되어 있으면 //editText에서 아이디와 암호 가져와 PreferenceManager에 저장한다.
                    PreferenceManager.setString(mContext, "id", et_email.getText().toString()); //id 키값으로 저장
                    PreferenceManager.setString(mContext, "pw", et_pwd.getText().toString()); //pw 키값으로 저장
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked()); //현재 체크박스 상태 값 저장
                } else { //체크박스가 해제되어있으면
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked()); //현재 체크박스 상태 값 저장
                    PreferenceManager.clear(mContext); //로그인 정보를 모두 날림
                }
            }
        }) ;



    }

}



