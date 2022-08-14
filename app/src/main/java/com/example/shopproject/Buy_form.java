package com.example.shopproject;

import static com.example.shopproject.Fragment4_me.static_account;
import static com.example.shopproject.Sell_Form.info_data;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buy_form extends AppCompatActivity {

    private TextView buy_name;
    private TextView buy_category;
    private TextView buy_content;
    private TextView buy_price;
    private TextView buy_location;
    private TextView user_nickname;
    private Button chat_button;
    private ImageView buy_form_back;
    private ImageView heart_black;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 관련 라이브러리
    private DatabaseReference mDatabaseRef; // 파이어베이스 실시간 DB 라이브러리



    ArrayList<Uri> Buy_uriList = new ArrayList<>(); // 이미지 정보 담는 arraylist

    Buy_MultiImageAdapter adapter; //다중 이미지를 넣는 리사이클러뷰 어댑터 변수 생성

    RecyclerView recyclerView; // 이미지 보여줄 리사이클러뷰


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_form); //xml 연결

        mFirebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 인스턴스 획득
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject"); // 파이어베이스 DB 인스턴스 획득 및 shopproject 상위 가지 형성

        //각각의 xml 연결
        buy_location = findViewById(R.id.buy_location);
        buy_name = findViewById(R.id.buy_name);
        buy_category = findViewById(R.id.buy_category);
        buy_content = findViewById(R.id.buy_content);
        buy_price = findViewById(R.id.buy_price);
        heart_black = findViewById(R.id.heart_black);
        user_nickname = findViewById(R.id.user_nickname);


        chat_button = findViewById(R.id.chat_button);

        buy_form_back = findViewById(R.id.buy_form_back);

        recyclerView = findViewById(R.id.buy_image_recyclerview);

        Log.d("Buy_form 에서의 글 제목 ", info_data.getProduct_name());

        Log.d("Buy_form 에서의 카테고리 ", info_data.getProduct_category());

        Log.d("Buy_form 에서의 글 내용 ", info_data.getProduct_content());

        Log.d("Buy_form 에서의 가격  ", info_data.getProduct_price());

        Log.d("Buy_form 에서의 urls  ", info_data.getProduct_imageurls());

        buy_location.setText(info_data.getProduct_city()); //buy_location에 static arraylist인 info_data에 담겨져 있는데 get()메소드를 이용하여 Product_city를 가져와 넣는다.

        String[] array = info_data.getProduct_imageurls().split(",");
        //String array 형태를 생성하고 변수 이름은 array라고 설정해준다, 이 array에 info_data에 담겨져 있는 Product_imageurls(이미지 Url값)을 , 기준으로 잘라서 넣는다.

        for(int i =0; i<array.length; i++) { //i=0 부터 array의 길이 만큼 반복문을 돌린다.

            Log.d("Buy_form 에서의 array:", array[i]);

            String aa = array[i].substring(array[i].length()-1, array[i].length()); // array[i]의 마지막 전 글자(array[i].length()-1)에서 마지막 글자(array[i].length())까지 자른다.

            Log.d("11111111111111", aa);


            if(aa.equals("]")) { //aa의 글자가 ] 라면

                Uri imageUri = Uri.parse(array[i].substring(1,array[i].length()-1)); //Uri 형식으로 변수이름은 imageUri으로 array[i].substring(1,array[i].length()-1)의 데이터를 저장한다.
                Log.d("777777777", imageUri.toString());
                Buy_uriList.add(imageUri); //Buy_uriList(이미지를 담는 arraylist)에 imageUri 데이터를 add를 통해 추가한다.

            }
            else {
                Uri imageUri = Uri.parse(array[i].substring(1)); //Uri 형식으로 변수이름은 imageUri으로 array[i].substring(1)의 데이터를 저장한다.
                Log.d("88888888888", imageUri.toString());
                Buy_uriList.add(imageUri); //Buy_uriList(이미지를 담는 arraylist)에 imageUri 데이터를 add를 통해 추가한다.

            }


        }


        Log.d("Buy_uriList의 값은////" , Buy_uriList.toString());

        adapter = new Buy_MultiImageAdapter(Buy_uriList, getApplicationContext()); //Buy_MultiImageAdapter의 객체를 생성하고 생성자 변수로 이미지 정보가 들어있는 Buy_uriList와 Context를 넣어준다.

        recyclerView.setAdapter(adapter); // recyclerView xml의 자리에 Adapter를 연결하여준다.

        adapter.notifyDataSetChanged(); //recyclerview 갱신

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)); // 리사이클러뷰 수평 스크롤 적용



        buy_name.setText(info_data.getProduct_name()); //buy_name에 static arraylist인 info_data에 담겨져 있는데 get()메소드를 이용하여 Product_name을 가져와 넣는다.
        buy_category.setText(info_data.getProduct_category()); //buy_category static arraylist인 info_data에 담겨져 있는데 get()메소드를 이용하여 Product_category를 가져와 넣는다.
        buy_content.setText(info_data.getProduct_content()); //buy_content static arraylist인 info_data에 담겨져 있는데 get()메소드를 이용하여 Product_content를 가져와 넣는다.
        buy_price.setText(info_data.getProduct_price()); //buy_price static arraylist인 info_data에 담겨져 있는데 get()메소드를 이용하여 Product_price를 가져와 넣는다.
        buy_category.setPaintFlags(buy_category.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //buy_category에 밑줄 긋기

        buy_form_back.setOnClickListener(new View.OnClickListener() { //뒤로가기 버튼을 누른다면
            @Override
            public void onClick(View v) {

                finish(); //뒤로 가기 기능

            }
        });



        mDatabaseRef.child("Product_Content").child(mFirebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            //shopprojcet-Product_content-mFirebaseAuth.getUid() 의 설계 구조를 가진 DB에 들어간다.

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                static_account = snapshot.getValue(UserAccount.class); //미리 만든 회원정보 UserAccount 객체에 snapshot의 값들을 넣는다.

                try {

                    if (static_account.getName().equals("")) { // account의 name이 null 값이라면
                        user_nickname.setText(""); //나의 정보 이름에 null 값 넣기
                    } else { // account의 name이 null값이 아니라면
                        String name = static_account.getName(); // String 변수 name에 account객체의 name 데이터 넣기

                        user_nickname.setText(name + " 님"); //나의 정보 이름에 String 변수 name 값 넣기
                    }
                }catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        heart_black.setOnClickListener(new View.OnClickListener() { //검정색 하트를 클릭했을때
            @Override
            public void onClick(View v) {

                int heart_count = Integer.parseInt((String) info_data.getProduct_heart_count() ); //info_data 에서 heart_count의 데이터를 불러온다.

                if(heart_count >=0 ) { //만약 heart_count가 0보다 같거나 클 때
                    if(heart_count % 2 == 0 ){ //heart_count를 2로 나눠서 나머지가 0일때 즉, 짝수일때
                        heart_black.setImageResource(R.drawable.heart_red); // 빨간색 하트로 바꾼다.
                        info_data.setProduct_heart_count(Integer.toString(Integer.parseInt((String) info_data.getProduct_heart_count() + 1))); //info_data의 heart_count의 수를 +1 한다.
                    }
                    else { //홀수 일때
                        heart_black.setImageResource(R.drawable.heart_black); //검정색 하트로 바꾼다.
                        info_data.setProduct_heart_count( Integer.toString(Integer.parseInt((String) info_data.getProduct_heart_count())-1)); //info_data의 heart_count의 수를 -1 한다.
                    }

                }

            }
        });
    }
}