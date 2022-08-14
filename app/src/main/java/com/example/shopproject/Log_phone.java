package com.example.shopproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Log_phone extends AppCompatActivity {


    private EditText input_phone_num;
    private EditText input_phone_num2;
    private Button sendSMS_button;
    private ImageView back;
    private  Button log;
    private CheckBox cb_save;

    private TextView id_find;

    private TextView pwd_find;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스

    private SharedPreferences appData; //SharedPreferences객체 변수 생성
    private boolean saveLoginData; //saveLoginData으 저장여부 확인을 위한 boolean값 변수 생성

    private String id;
    private String pwd;

    private CustomDialog_find_id customDialogFindidFindid;
    private CustomDialog_find_pwd customDialogFindPwd;


    static final int SMS_SEND_PERMISSON = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_phone);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

//SharedPreferences
        appData = getSharedPreferences("appData", MODE_PRIVATE); //만들어진 SharedPreferences 객체 변수에 사용할 sharedpreferences이름과 모드 설정
        load(); //로그인 정보 가져오기

        pwd_find = (TextView) findViewById(R.id.pwd_find);
        pwd_find.setPaintFlags(pwd_find.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // 언더라인이다
        id_find = (TextView) findViewById(R.id.id_find2);
        id_find.setPaintFlags(id_find.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // 언더라인이다


        input_phone_num = (EditText) findViewById(R.id.input_phone_num);
        input_phone_num2 = (EditText) findViewById(R.id.input_phone_num2);
        sendSMS_button = (Button) findViewById(R.id.log);
        back = (ImageView) findViewById(R.id.back);
        log = (Button) findViewById(R.id.log);
        cb_save = (CheckBox) findViewById(R.id.cb_save);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject");


        if(saveLoginData) { //load() 메소드 호출에서 saveLoginData의 boolean 값의 true/false 여부 확인
            input_phone_num.setText(id); //id_textview에 true라면 save()함수를 통해 저장한 editor.putString(Id)의 값을 가져오고, false라면 null 값 출력
            input_phone_num2.setText(pwd); //pwd_textview에 true라면 save()함수를 통해 저장한 editor.putString(Pwd)의 값을 가져오고, false라면 null 값 출력
            cb_save.setChecked(saveLoginData); //cb_save(체크박스) 체크여부를 saveLoginData(true/false) 값을 넣어준다.
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_phone.this, Log_before.class);

                startActivity(intent);

            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = input_phone_num.getText().toString().trim();
                String pwd =  input_phone_num2.getText().toString().trim();
                mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Log_phone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(Log_phone.this, Main_Activity.class);
                            startActivity(intent);
                            save();
                            String phone ;


                        }
                        else {
                            Toast.makeText(Log_phone.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        cb_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SaveSharedPreference.setUserName(Log_phone.this, editId.getText().toString());
            }
        });

        id_find.setOnClickListener(new View.OnClickListener() { //ID 찾기 TextView를 클릭한다.
            @Override
            public void onClick(View view) {
                customDialogFindidFindid = new CustomDialog_find_id(Log_phone.this); //Id를 찾을 수 있는 기능이 담긴 CustomDialog_find_id 객체를 생성한다.
                customDialogFindidFindid.show(); // Id를 찾을 수 있는 기능이 담긴 CustomDialog_find_id 객체를 실행시킨다.
            }
        });
        pwd_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogFindPwd = new CustomDialog_find_pwd(Log_phone.this); //Pwd를 찾을 수 있는 기능이 담긴 CustomDialog_find_pwd 객체를 생성한다.
                customDialogFindPwd.show(); // Pwd를 찾을 수 있는 기능이 담긴 CustomDialog_find_pwd 객체를 실행시킨다.
            }
        });



    }

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", cb_save.isChecked());
        editor.putString("ID", input_phone_num.getText().toString().trim());
        editor.putString("PWD", input_phone_num2.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);// SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        id = appData.getString("ID", ""); // 키 값(ID)에 저장된 값 불러오고 기본값(null)일 때는 null값 출력
        pwd = appData.getString("PWD", ""); // 키 값(PWD)에 저장된 값 불러오고 기본값(null)일 때는 null값 출력
    }



}