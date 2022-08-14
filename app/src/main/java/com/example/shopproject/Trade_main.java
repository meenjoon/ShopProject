//package com.example.shopproject;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//
//public class Trade_main extends AppCompatActivity {
//
//
//    public static final int mainintent = 1000; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
//
//    private ArrayList<Trade_RecyclerProductData> arrayList, filterdList; //리사이클러뷰 데이터가 담겨 있는 곳을 ArrayList로 참조한다.
//    private Trade_RecyclerProductAdapter recyclerProductAdapter; //내가 만든 RecyclerProductAdapter 의 변수를 생성한다.
//    private RecyclerView recyclerView; //기본적으로 제공하는 RecyclerView 의 변수를 생성한다.
//    private LinearLayoutManager linearLayoutManager; //기본적으로 제공하는 LinearLayoutManager의 변수를 생성한다.
//
////
//
//    private int i=1; // 리사이클러뷰 안에 있는 아이템의 갯수를 카운터 해주기 위한 변수를 만들어준다.
//
////    @Override
////    protected  void onNewIntent(){
////
////    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_recyclerview_item); //리사이클러뷰가 있는 xml 파일하고 연결해준다.
//        EditText searchET;
//        searchET = (EditText) findViewById(R.id.searchFood);
//
//
//
//        recyclerView = (RecyclerView) findViewById(R.id.recycler1); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한다.
//
//        linearLayoutManager = new LinearLayoutManager(this); //만든 linearLayoutManager 변수를 통해 객체를 만든다.
//        recyclerView.setLayoutManager(linearLayoutManager); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든 linearLayoutManager 객체와 연결해준다.
//
//        arrayList = new ArrayList<>();  //arrayList 에 대한 객체를 생성한다.
//        filterdList=new ArrayList<>();
//
//        recyclerProductAdapter = new Trade_RecyclerProductAdapter(arrayList); //만든 recyclerProductAdapter 변수를 통해 객체를 만드는 이때 arrayList객체를 넣어 만들어 준다.
//        recyclerView.setAdapter(recyclerProductAdapter);// 만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든
//                                                        // linearLayoutManager 와 연결해준것과 더불어 recyclerProductAdapter와 연결해준다.
//
//        ImageView trade_image = (ImageView) findViewById(R.id.trade_image); //리사이클러뷰가 있는 xml에서 addbutton과 연결해준다.
//        trade_image.setOnClickListener(new View.OnClickListener() { //trade_image 을 클릭하였을때
//            @Override
//            public void onClick(View view) {
////                RecyclerProductData recyclerProductData = new RecyclerProductData(i,R.drawable.ic_launcher_background,"상품 이름","상품 정보","채팅방","상품 구매");
////                // RecyclerProductData를 통해 객체를 생성하는데 이때 생성자는(int product_count, int product_image, String product_name, String product_content, String product_chatting, String product_buy)이다.
////
////                arrayList.add(recyclerProductData); // arrayList 에 recyclerProductData를 추가하갰다.
////                recyclerProductAdapter.notifyDataSetChanged(); //어뎁터를 갱신한다.
////                i++;
//                Intent intent = new Intent(Trade_main.this, Sell_Form.class);
//                startActivity(intent);
//
//            }
//        });
//
//        ImageView myproduct_image = (ImageView) findViewById(R.id.myproduct_image);
//        myproduct_image.setOnClickListener(new View.OnClickListener() { //Trade 액티비티에서 메인메뉴 버튼을 클릭 했을때
//            @Override
//            public void onClick(View view) {
//                //내가 올린 상품의 정보를 올려주는 액티비티 생성
//            }
//        });
//
//        searchET.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String searchText = searchET.getText().toString();
//                serachFilter(searchText);
//            }
//        });
//
//
//    }
//
//    public void serachFilter(String searchText) {
//        filterdList.clear();
//
//        for (int i = 0; i< arrayList.size(); i++) {
//            if (arrayList.get(i).getProduct_name().toLowerCase().contains(searchText.toLowerCase())) {
//                filterdList.add(arrayList.get(i));
//            }
//        }
//        recyclerProductAdapter.filterList(filterdList);
//
//    }
//}