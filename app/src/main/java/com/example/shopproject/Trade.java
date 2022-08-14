package com.example.shopproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Trade extends AppCompatActivity {


    public static final int mainintent = 1000; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/

    private ArrayList<RecyclerProductData> arrayList, filterdList; //리사이클러뷰 데이터가 담겨 있는 곳을 ArrayList로 참조한다.
    private RecyclerProductAdapter recyclerProductAdapter; //내가 만든 RecyclerProductAdapter 의 변수를 생성한다.
    private RecyclerView recyclerView; //기본적으로 제공하는 RecyclerView 의 변수를 생성한다.
    private LinearLayoutManager linearLayoutManager; //기본적으로 제공하는 LinearLayoutManager의 변수를 생성한다.

//

    private int i=1; // 리사이클러뷰 안에 있는 아이템의 갯수를 카운터 해주기 위한 변수를 만들어준다.

//    @Override
//    protected  void onNewIntent(){
//
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_item); //리사이클러뷰가 있는 xml 파일하고 연결해준다.
        EditText searchET;

        searchET = (EditText) findViewById(R.id.searchFood);


        recyclerView = (RecyclerView) findViewById(R.id.recycler1); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한다.

        linearLayoutManager = new LinearLayoutManager(this); //만든 linearLayoutManager 변수를 통해 객체를 만든다.
        recyclerView.setLayoutManager(linearLayoutManager); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든 linearLayoutManager 객체와 연결해준다.

        arrayList = new ArrayList<>();  //arrayList 에 대한 객체를 생성한다.
        filterdList=new ArrayList<>();

        recyclerProductAdapter = new RecyclerProductAdapter(arrayList); //만든 recyclerProductAdapter 변수를 통해 객체를 만드는 이때 arrayList객체를 넣어 만들어 준다.
        recyclerView.setAdapter(recyclerProductAdapter);// 만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든
                                                        // linearLayoutManager 와 연결해준것과 더불어 recyclerProductAdapter와 연결해준다.

        Button add_button = (Button) findViewById(R.id.addbutton); //리사이클러뷰가 있는 xml에서 addbutton과 연결해준다.
        add_button.setOnClickListener(new View.OnClickListener() { //addbutton 을 클릭하였을때
            @Override
            public void onClick(View view) {
//                RecyclerProductData recyclerProductData = new RecyclerProductData(i,R.drawable.ic_launcher_background,"상품 이름","상품 정보","채팅방","상품 구매");
//                // RecyclerProductData를 통해 객체를 생성하는데 이때 생성자는(int product_count, int product_image, String product_name, String product_content, String product_chatting, String product_buy)이다.
//
//                arrayLis것t.add(recyclerProductData); // arrayList 에 recyclerProductData를 추가하갰다.
//                recyclerProductAdapter.notifyDataSetChanged(); //어뎁터를 갱신한다.
//                i++;
                Intent intent = new Intent(Trade.this, Sell_Form.class);
                startActivity(intent);

            }
        });

        Button mainback_button; // 메인메뉴로 가는 버튼 변수 생성
        mainback_button = (Button) findViewById(R.id.mainback); //메인메뉴로 가는 변수 생성한 것과 리사이클러뷰가 있는 xml 파일의 메인메뉴 버튼과 연결한다.

        mainback_button.setOnClickListener(new View.OnClickListener() { //Trade 액티비티에서 메인메뉴 버튼을 클릭 했을때
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class); // 메인메뉴 엑티비티로 가는 인탠트 형성

                startActivity(intent); // 메인 메뉴로의 화면 전환
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = searchET.getText().toString();
                serachFilter(searchText);
            }
        });


    }

    public void serachFilter(String searchText) {
        filterdList.clear();

        for (int i = 0; i< arrayList.size(); i++) {
            if (arrayList.get(i).getProduct_name().toLowerCase().contains(searchText.toLowerCase())) {
                filterdList.add(arrayList.get(i));
            }
        }
        recyclerProductAdapter.filterList(filterdList);

    }
}