package com.example.shopproject;

import static com.example.shopproject.Main_Activity.loc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment4_me extends Fragment {

    TextView my_name;
    TextView my_loacation;

    ImageView arrow_right;

    Button logout;
    Button membership_withdrawal;

    static UserAccount static_account;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 관련 라이브러리
    private DatabaseReference mDatabaseRef; // 파이어베이스 실시간 DB 라이브러리

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment4_me() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment4_me, container, false);


        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 인스턴스 획득
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject"); // 파이어베이스 DB 인스턴스 획득 및 shopproject 상위 가지 형성

        my_name = view.findViewById(R.id.my_name); // TextView
        my_loacation = view.findViewById(R.id.my_location);

        arrow_right = view.findViewById(R.id.arrow_right); // ImageView

        logout = view.findViewById(R.id.logout);
        membership_withdrawal = view.findViewById(R.id.membership_withdrawal);


        if(loc!=null){ //현재 위치 지역 데이터 loc가 null 값이 아니라면

            String my_location;
            my_location= loc; //현재 위치 지역 데이터를 String 변수 my_location에 넣는다.
            my_loacation.setText(my_location);
        }
        else{ //현재 위치 지역 데이터 loc가 null 값이라면

            my_loacation.setText("지역 설정 안함");

        }


        mDatabaseRef.child("Product_Content").child(mFirebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            //shopprojcet-Product_content-mFirebaseAuth.getUid() 의 설계 구조를 가진 DB에 들어간다.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                static_account = snapshot.getValue(UserAccount.class); //미리 만든 회원정보 UserAccount 객체에 snapshot의 값들을 넣는다.

                try {
                    if (static_account.getName().equals("")) { // account의 name이 null 값이라면
                        my_name.setText(""); //나의 정보 이름에 null 값 넣기
                    } else { // account의 name이 null값이 아니라면
                        String name = static_account.getName(); // String 변수 name에 account객체의 name 데이터 넣기

                        my_name.setText(name + " 님"); //나의 정보 이름에 String 변수 name 값 넣기
                    }
                }catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //AlertDialog.Builder 를 이용한 builder 객체 생성
                builder.setTitle("안내");
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() { //예를 클릭 했을때
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"로그아웃 되었습니다", Toast.LENGTH_SHORT).show(); // 토스트 메세지
                        mFirebaseAuth.signOut(); //파이어 베이스 로그아웃
                        Intent intent = new Intent(getActivity(), Log_phone.class); //인텐트로 로그인 액티비티 이동
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // 아니오를 클릭 했을때
                        Toast.makeText(getContext(),"취소", Toast.LENGTH_SHORT).show(); // 토스트 메세지
                    }
                });

                AlertDialog alertDialog = builder.create(); // builder.create()를 이용한 alertDialog 객체 생성
                alertDialog.show(); // alertDialog 객체의 show 메소드 시작
            }
        });

        membership_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //AlertDialog.Builder 를 이용한 builder 객체 생성
                builder.setTitle("안내");
                builder.setMessage("회원탈퇴 하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() { //예를 클릭 했을때
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"회원탈퇴 되었습니다", Toast.LENGTH_SHORT).show(); // 토스트 메세지
                        mFirebaseAuth.getCurrentUser().delete(); //파이어 베이스 인증에 등록된 회원탈퇴
                        mDatabaseRef.child("Product_Content").child(mFirebaseAuth.getUid()).setValue(null); //파이어 베이스 realtimebase 데이터 삭제

                        //로그인을 할 때 로그인 저장 정보를 Sharedpreferneces에 저장하였는데 이것을 삭제하는 기능이다.
                        SharedPreferences pref = getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);
                        //getSharedPreferences("Preferences이름",MODE_PRIVATE) 메소드를 이용하여 SharedPreferences 인스턴스를 획득한다.
                        SharedPreferences.Editor editor = pref.edit(); //SharedPreferences Editor 객체 생성
                        editor.clear(); //Preferneces 전체 삭제
                        editor.commit(); //Editor commit
                        // 참고 : https://sunful.tistory.com/8
                        Intent intent = new Intent(getActivity(), Log_phone.class); //인텐트로 로그인 액티비티 이동
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // 아니오를 클릭 했을때
                        Toast.makeText(getContext(),"취소", Toast.LENGTH_SHORT).show(); // 토스트 메세지
                    }
                });

                AlertDialog alertDialog = builder.create(); // builder.create()를 이용한 alertDialog 객체 생성
                alertDialog.show(); // alertDialog 객체의 show 메소드 시작

            }
        });




        return view;
    }
}