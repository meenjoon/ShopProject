package com.example.shopproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView signup_id;
    private TextView signup_pwd;
    private TextView signup_pwd2;
    private TextView signup_name;
    private TextView signup_date;
    private Button signup_button;
    private TextView questtion;
    private ImageView back;
    private  Spinner spinner;
    private ImageView imageView2;
    private String phone;


    private EditText signup_phone;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 관련 라이브러리
    private DatabaseReference mDatabaseRef; // 파이어베이스 실시간 DB 라이브러리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.question, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
//        final String[] question = {"-질문을 선택해주세요.-", "당신의 별명은 무엇입니까?" , "당신의 아버지의 성함은?", "당신의 보물 1호는?" ,"당신의 고향은?","당신이 감명깊게 읽은 책은?"};
//        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, question);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnClickListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                        List<String> categories = new ArrayList<String>();
//        categories.add("-질문을 선택해주세요.-");
//        categories.add("당신의 별명은 무엇입니까?");
//        categories.add("당신의 아버지의 성함은?");
//        categories.add("당신의 보물 1호는?");
//        categories.add("당신의 고향은?");
//        categories.add("당신이 감명깊게 읽은 책은?");
//                Toast.makeText(getApplicationContext(), "Selected Question:" + question[position], Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        questtion = (EditText) findViewById(R.id.questtion);
        signup_id = (EditText) findViewById(R.id.signup_id);
        signup_pwd = (EditText) findViewById(R.id.signup_pwd);
        signup_pwd2 = (EditText) findViewById(R.id.signup_pwd2);
        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_date = (EditText) findViewById(R.id.signup_date);
        signup_phone = (EditText) findViewById(R.id.signup_phone);
        signup_button = (Button) findViewById(R.id.signup_button);
        back = (ImageView) findViewById(R.id.back);
        imageView2 = (ImageView) findViewById(R.id.imageView2);


        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 인스턴스 획득
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject"); // 파이어베이스 DB 인스턴스 획득 및 shopproject 상위 가지 형성
        // DB 구조 설계 중 shopproject 위치로 연결함.


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();

        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        signup_button.setOnClickListener(new View.OnClickListener() {
            private boolean StringUtils;

            @Override
            public void onClick(View view) {
                String email = signup_id.getText().toString().trim();
                String pwd = signup_pwd.getText().toString().trim();
                String pwdcheck = signup_pwd2.getText().toString().trim();
                String name = signup_name.getText().toString().trim();
                String date = signup_date.getText().toString().trim();
                String question = questtion.getText().toString().trim() ;
                String spinner1 = spinner.getSelectedItem().toString().trim();
                String phone = signup_phone.getText().toString().trim();





                if (email.equals("")  || pwd.equals("") || pwdcheck.equals("") || name.equals("")|| date.equals("") || phone.equals("") || spinner1.equals("- 질문을 선택해주세요 -") || question.equals("") )
                {
                    Toast.makeText(Signup.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {



                    if (pwd.equals(pwdcheck)) {
                        // Firebase Auth 진행
                        Log.d("44444로그인 : " , "어떻게 되고 있는거야" );
                        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {





                                String phone1 = "0,1";

//                                        char date1 = date.charAt(0);
//                                        char date2 = date.charAt(1);
//                                        char date3 = date.charAt(2);
//                                        char date4 = date.charAt(3);
//                                        char date5 = date.charAt(4);
//                                        char date6 = date.charAt(5);
//                                        char date7 = date.charAt(6);
//                                        char date8 = date.charAt(7);

                                if(pwd.length()<=5){
                                    Toast.makeText(Signup.this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }
                                else if(!email.contains("@") || !email.contains(".com")){

                                    Toast.makeText(Signup.this, "아이디에 @ 및 .com을 포함시키세요.", Toast.LENGTH_SHORT).show();
                                }
                                else if(phone.contains("-") || !(Integer.parseInt(String.valueOf(phone.charAt(1)))==1)){


//                                            if( !(phone.length() ==11 )) {
//                                                Toast.makeText(Signup.this, "휴대전화를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
//                                            }
//                                            else if(!(phone.substring(0,1) == phone1)){
//                                                Toast.makeText(Signup.this, "휴대전화를 올바르게 입력하세요.11", Toast.LENGTH_SHORT).show();
//                                            }
//                                            else
                                    Toast.makeText(Signup.this,"올바른 전화번호 형식으로 입력해주세요..", Toast.LENGTH_SHORT).show();


                                }

                                else if(date.length()<=7 || Integer.parseInt(String.valueOf(date.charAt(0))) >= 3 || Integer.parseInt(String.valueOf(date.charAt(4)))>1
                                        || Integer.parseInt(String.valueOf(date.charAt(5)))==0  || Integer.parseInt(String.valueOf(date.charAt(6)))>3
                                        ||(Integer.parseInt(String.valueOf(date.charAt(6)))==3  && Integer.parseInt(String.valueOf(date.charAt(7)))>1
                                        || (Integer.parseInt(String.valueOf(date.charAt(4)))==1 &&  Integer.parseInt(String.valueOf(date.charAt(5)))>2)
                                        ||Integer.parseInt(String.valueOf(date.charAt(7)))==0)
                                )  {
                                    Toast.makeText(Signup.this, "생년월일 8자 이상 및 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();

                                }
                                else if (date.length()<=7 || ( Integer.parseInt(String.valueOf(date.charAt(0)))==2  && Integer.parseInt(String.valueOf(date.charAt(1)))>0
                                        && Integer.parseInt(String.valueOf(date.charAt(2)))>2  && Integer.parseInt(String.valueOf(date.charAt(3)))>2)|| Integer.parseInt(String.valueOf(date.charAt(6)))>3
                                        || (Integer.parseInt(String.valueOf(date.charAt(6)))==3  && Integer.parseInt(String.valueOf(date.charAt(7)))>1)
                                        ||(Integer.parseInt(String.valueOf(date.charAt(4)))==1 &&  Integer.parseInt(String.valueOf(date.charAt(5)))>2
                                        ||Integer.parseInt(String.valueOf(date.charAt(7)))==0)
                                ){
                                    Toast.makeText(Signup.this, "생년월일 8자 이상 및 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                                }

                                else   { //회원 가입이 성공이 되었을때
                                    Log.d("33333로그인 : " , "어떻게 되고 있는거야" );

                                    if((task.isSuccessful())) {
                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                        UserAccount account = new UserAccount();

                                        Log.d("22222로그인 : " , "어떻게 되고 있는거야" );

                                        account.setIdToken(firebaseUser.getUid());
//                                                    account.setEmailId(firebaseUser.getEmail());
                                        account.setPassword(pwd);
                                        account.setName(name);
                                        account.setDate(date);
                                        account.setQuestion(question);
                                        account.setSpinner(spinner1);//spinner는 질문임
                                        account.setPhone(phone);
                                        account.setEmailId(email);
                                        account.setProduct("올린 제품 정보");

                                        // setValue : database에 insert (삽입) 행위

                                        mDatabaseRef.child("UserAccount").child(phone).setValue(account);
                                        mDatabaseRef.child("Product_Content").child(mFirebaseAuth.getUid()).setValue(account);

                                        Toast.makeText(Signup.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }




//                                    mDatabaseRef.child("UserAccount").child(phone).child("phone").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                                          UserAccount user = snapshot.getValue(UserAccount.class);
////                                           String phone = user.getPhone();
//                                            Log.d("55555로그인 : " , "어떻게 되고 있는거야" );
//                                            String value = snapshot.getValue(String.class);
//
//                                            if(value!=null){
//                                                Log.d("11111로그인 : " , "어떻게 되고 있는거야" );
//                                                Toast.makeText(Signup.this,"이미 존재하는 전화번호입니다.",Toast.LENGTH_SHORT).show();
//                                            }
//                                            else {
//                                                if((task.isSuccessful())) {
//                                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//                                                    UserAccount account = new UserAccount();
//
//                                                    Log.d("22222로그인 : " , "어떻게 되고 있는거야" );
//
//                                                    account.setIdToken(firebaseUser.getUid());
////                                                    account.setEmailId(firebaseUser.getEmail());
//                                                    account.setPassword(pwd);
//                                                    account.setName(name);
//                                                    account.setDate(date);
//                                                    account.setQuestion(question);
//                                                    account.setSpinner(spinner1);//spinner는 질문임
//                                                    account.setPhone(phone);
//                                                    account.setEmailId(email);
//                                                    account.setProduct("올린 제품 정보");
//
//                                                    // setValue : database에 insert (삽입) 행위
//                                                    mDatabaseRef.child("UserAccount").child(phone).setValue(account);
//                                                    mDatabaseRef.child("Product_Content").child(mFirebaseAuth.getUid()).setValue(account);
//
//                                                    Toast.makeText(Signup.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
//                                                    finish();
//                                                }
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });

                                }

                            }

                        });
                    } else {
                        Toast.makeText(Signup.this, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }


                }
            }

        });

//        imageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phone1 = signup_phone.getText().toString().trim();
//                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//
////                mDatabaseRef.child("UserAccount").addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////
////
////
////                        for(DataSnapshot aaa : snapshot.getChildren()){
////
////                            UserAccount user = snapshot.getValue(UserAccount.class);
////                            phone = user.getPhone();
////                            Log.e("ddd    " , phone);
//////                            Toast.makeText(Signup.this, phone ,Toast.LENGTH_SHORT).show();
////                        }
////
//////                        String phone = user.getPhone();
//////                        Toast.makeText(Signup.this, phone ,Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError error) {
////                        signup_id.setText("error: " + error.toException());
////                    }
////                });
//                mDatabaseRef.child("UserAccount").child(phone1).child("phone").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        UserAccount user = snapshot.getValue(UserAccount.class);
////                        String phone = user.getPhone();
//                        String value = snapshot.getValue(String.class);
//
//                        if(value!=null){
//                            Toast.makeText(Signup.this,"이미 존재하는 전화번호입니다.",Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            if((task.isSuccessful())) {
//                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//                                UserAccount account = new UserAccount();
//
//                                account.setIdToken(firebaseUser.getUid());
//                                account.setEmailId(firebaseUser.getEmail());
//                                account.setPassword(pwd);
//                                account.setName(name);
//                                account.setDate(date);
//                                account.setQuestion(question);
//                                account.setSpinner(spinner1);
//                                account.setPhone(phone);//spinner는 질문임
//
//                                // setValue : database에 insert (삽입) 행위
//                                mDatabaseRef.child("UserAccount").child(phone).setValue(account);
//
//                                Toast.makeText(Signup.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
