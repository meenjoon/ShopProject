package com.example.shopproject;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomDialog_find_pwd extends Dialog implements AdapterView.OnItemSelectedListener{
    private EditText find_pwd_email;
    private EditText find_pwd_name;
    private EditText find_pwd_phone;
    private EditText find_pwd_question;
    private  Button find_pwd_button;
    private Spinner find_pwd_spinner;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 변수 생성
    private DatabaseReference mDatabaseRef; // 파이어베이스 실시간 데이터베이스 변수 생성

    private CustomDialog_change_pwd customDialogChangePwd; // CustomDialog_change_pwd 객체 변수 생성

    public CustomDialog_find_pwd(Context context)
    {
        super(context);
        setContentView(R.layout.custom_dialog_find_pwd); //custom_dialog_find_pwd 적용될 xml 연결
        find_pwd_email  = findViewById(R.id.find_pwd_email); //custom_dialog_find_pwd 적용될 xml 연결
        find_pwd_name = findViewById(R.id.find_pwd_name); //custom_dialog_find_pwd 적용될 xml 연결
        find_pwd_phone = findViewById(R.id.find_pwd_phone); //custom_dialog_find_pwd 적용될 xml 연결
        find_pwd_question = findViewById(R.id.find_pwd_question); //custom_dialog_find_pwd 적용될 xml 연결
        find_pwd_button = findViewById(R.id.find_pwd_button); //custom_dialog_find_pwd 적용될 xml 연결

        find_pwd_spinner = findViewById(R.id.find_pwd_spinner); //custom_dialog_find_pwd 적용될 xml 연결

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.question, android.R.layout.simple_spinner_item);
        //simple_spinner_item의 xml에 question의 array 아이템들의 데이터를 생성하는 spinner의 Adapter 생성
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // dropdown 되었을 때 보여지는 View에서 사용될 layout을 별도로 설정하는 경우 사용
        find_pwd_spinner.setAdapter(adapter); //find_pwd_spinner에 adapter를 연결
        find_pwd_spinner.setOnItemSelectedListener(this); //해당 포지션에 세팅된 아이템 내용을 얻는다.

        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 인스턴스 획득
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject"); // 파이어베이스 DB 인스턴스 획득 및 shopproject 상위 가지 형성

        find_pwd_button.setOnClickListener(new View.OnClickListener() { //find_pwd_button를 클릭했을때
            @Override
            public void onClick(View view) {
                String email = find_pwd_email.getText().toString().trim(); //사용자가 입력한 글자 추출
                String name = find_pwd_name.getText().toString().trim(); //사용자가 입력한 글자 추출
                String phone = find_pwd_phone.getText().toString().trim(); //사용자가 입력한 글자 추출
                String question = find_pwd_question.getText().toString().trim(); //사용자가 입력한 글자 추출
                String spinner = find_pwd_spinner.getSelectedItem().toString().trim(); //사용자가 입력한 글자 추출


                mDatabaseRef.child("UserAccount").child(phone).addValueEventListener(new ValueEventListener() {
                //파이어베이스의 실시간 데이터베이스에서 shopproject-UserAccount-(내가 입력한 전화번호)의 위치에 들어간다.
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) { //작업이 완료가 되었을때

                        UserAccount user = snapshot.getValue(UserAccount.class); //UserAccount의 객체를 생성하는데 이것이 파이어베이스의 실시간 데이터베이스에서 데이터를 가져온다.

                        if( !email.equals("") && !name.equals("") && !phone.equals("") && !spinner.equals("") && !question.equals("")){
                        //입력한 email, name, phone, question, 선택한 spinner의 값이 null 값이 아니라면
                            if(user !=null) { //만약 파이어베이스의 실시간 데이터베이스에서 shopproject-UserAccount-(내가 입력한 전화번호)에 데이터가 null 값이 아니라면
                                if (name.equals(user.getName()) && phone.equals(user.getPhone())
                                      && email.equals(user.getEmailId())) {//내가 입력한 이름과 실시간 데이터베이스의 이름이 일치하고, 또한 내가 입력한 핸드폰 번호와 실시간 데이터베이스의 번호가 일치하고, 또한 내가 입력한 이메일과 실시간 데이터베이스의 이메일이 일치한다면
                                    if (spinner.equals(user.getSpinner()) && question.equals(user.getQuestion())) {//내가 선택한 spinner(질문)과 실시간 데이터베이스의 질문이 일치하고, 또한 내가 입력한 질문의 답과 실시간 데이터베이스의 질문의 답변이 일치한다면
                                        mFirebaseAuth.sendPasswordResetEmail(user.getEmailId()).addOnCompleteListener(task -> { //파이어베이스 인증 객체를 이용하여 등록한 이메일로 비밀번호 변경 메세지를 보낸다.
                                            if (task.isSuccessful()){ //성공한다면
                                                Toast.makeText(context, "설정하신 이메일로 비밀번호 변경 메세지를 발송하였습니다.("+user.getEmailId()+")", Toast.LENGTH_LONG).show(); //Toast메세지로 설정하신 이메일로 비밀번호 변경 메세지를 발송하였다는 메세지를 띄운다.
                                                dismiss(); //창을 닫는다.
                                            }
                                        });
//                                        Toast.makeText(context, "비밀번호 변경가능합니다", Toast.LENGTH_SHORT).show();//Toast 메세지로 비밀번호 변경이 가능하다고 띄어준다.
//                                        dismiss(); //창을 닫는다
//                                        customDialogChangePwd = new CustomDialog_change_pwd(context, phone);//CustomDialog_change_pwd(Pwd를 바꾸는 Dialog)의 객체를 생성한다.
//                                        customDialogChangePwd.show();// CustomDialog_change_pwd 객체를 실행한다.
                                    } else {
                                        Toast.makeText(context, "질문의 답이 일치 하지 않습니다.", Toast.LENGTH_SHORT).show(); //Toast 메세지로 질문의 답이 일치 하지 않는다고 띄어준다.
                                    }
                                } else {
                                    Toast.makeText(context, "일치하는 회원정보가 없습니다.", Toast.LENGTH_SHORT).show(); //Toast 메세지로 일치하는 회원정보가 없다고 띄어준다.
                                }
                            }
                            else {
                                Toast.makeText(context, "일치하는 회원정보가 없습니다.", Toast.LENGTH_SHORT).show(); //Toast 메세지로 일치하는 회원정보가 없다고 띄어준다.
                            }
                        }
                        else {
                            Toast.makeText(context, "정보를 입력해주세요.",Toast.LENGTH_SHORT).show(); //Toast 메세지로 정보를 입력해주세요 라고 띄어준다.
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

        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}