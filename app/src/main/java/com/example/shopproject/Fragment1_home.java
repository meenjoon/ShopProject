package com.example.shopproject;

import static com.example.shopproject.Sell_Form.image_data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class Fragment1_home extends Fragment {

    Context context;

    private EditText search_product;

    private TextView city;

    private TextView trade_text;
    private TextView myproduct_text;

    private ImageView check;
    private ImageView category;
    private ImageView alarm;
    private ImageView trade_image;
    private ImageView myproduct_image;
    private Spinner location_spinner;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    private ChildEventListener mChild;

    private ArrayList<Trade_RecyclerProductData> arrayList00;




    static ArrayList<Trade_RecyclerProductData> arrayList33 = new ArrayList<>();
    static ArrayList<Trade_RecyclerProductData> filterdList = new ArrayList<>(); //리사이클러뷰 데이터가 담겨 있는 곳을 ArrayList로 참조한다.
    static Trade_RecyclerProductAdapter recyclerProductAdapter; //내가 만든 RecyclerProductAdapter 의 변수를 생성한다.
    private RecyclerView recyclerView; //기본적으로 제공하는 RecyclerView 의 변수를 생성한다.
    private LinearLayoutManager linearLayoutManager; //기본적으로 제공하는 LinearLayoutManager의 변수를 생성한다.



    private int i=1; // 리사이클러뷰 안에 있는 아이템의 갯수를 카운터 해주기 위한 변수를 만들어준다.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1_home, container, false);


        search_product = view.findViewById(R.id.search_product);
        category = view.findViewById(R.id.category);
        trade_text = view.findViewById(R.id.trade_text);
        trade_image = view.findViewById(R.id.trade_image);
        myproduct_text = view.findViewById(R.id.myproduct_text);
        myproduct_image = view.findViewById(R.id.myproduct_image);
        alarm = view.findViewById(R.id.alarm);
        location_spinner = view.findViewById(R.id.location_spinner);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler1); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한다.
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context); //만든 linearLayoutManager 변수를 통해 객체를 만든다.
        recyclerView.setLayoutManager(linearLayoutManager); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든 linearLayoutManager 객체와 연결해준다.




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.location_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location_spinner.setAdapter(adapter);






        recyclerProductAdapter = new Trade_RecyclerProductAdapter(getActivity() ,arrayList33,image_data); //만든 recyclerProductAdapter 변수를 통해 객체를 만드는 이때 arrayList객체를 넣어 만들어 준다.

//        image_data.clear();



        Log.d("프래그먼트1 홈", String.valueOf(image_data.size()));
//        recyclerProductAdapter = new Trade_RecyclerProductAdapter(arrayList33); //만든 recyclerProductAdapter 변수를 통해 객체를 만드는 이때 arrayList객체를 넣어 만들어 준다.
        recyclerView.setAdapter(recyclerProductAdapter);// 만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든

        recyclerProductAdapter.notifyDataSetChanged();
        image_data.clear();
        arrayList33.clear();




        // linearLayoutManager 와 연결해준것과 더불어 recyclerProductAdapter와 연결해준다.

        trade_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Sell_Form.class);
                startActivity(intent);
            }
        });

        search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = search_product.getText().toString();
                searchFilter(searchText);

            }
        });
        Log.d("스피너 값 : ", location_spinner.getSelectedItem().toString());


        location_spinner.setSelection(0,false);

        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                filterdList.clear();

                String location = location_spinner.getItemAtPosition(position).toString();
                locationFilter(location);



//                for (int i = 0; i< arrayList33.size(); i++) {
////            if (arrayList33.get(i).getProduct_name().toLowerCase().contains(searchText.toLowerCase())) {
////                filterdList.add(arrayList33.get(i));
////            }
//
//                    if(arrayList33.get(i).getProduct_location().toLowerCase().equals(location_spinner.getItemAtPosition(position).toString())){
//                        filterdList.add(arrayList33.get(i));
//                        Log.d("성공 :"," ㅇㅇ");
//                        Log.d("aa",arrayList33.get(i).getProduct_imageurls());
//                        Log.d("bb",arrayList33.get(i).getProduct_image_urls().toString());
//                        Log.d("cc",arrayList33.get(i).getProduct_image_url());
//
//                        Log.d("dd",arrayList33.get(i).getProduct_name());
//                    }
//                    else {
//                        Log.d("실패 :"," ㄴㄴ");
//                    }
//
//                    Log.d("어레이 값은? : ",arrayList33.get(i).getProduct_location().toString() );
//                    Log.d("어레이 값은(스피너) : ",  location_spinner.getSelectedItem().toString());
//                    Log.d("어레이 값은(스피너)333 : " ,location_spinner.getItemAtPosition(position).toString());
////                    if(arrayList33.get(i).getProduct_location().equals(location_spinner.getItemAtPosition(position))) { //스피너의 현재값과 같은 값만
//////                        filterdList.add(arrayList33.get(i));
////                    }
//
//                }
//
//
//
//
//                recyclerProductAdapter.filterList(filterdList);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

//                String location = location_spinner.getSelectedItem().toString();
//                locationFilter(location);

//                filterdList.clear();
//                for (int i = 0; i< arrayList33.size(); i++) {
////            if (arrayList33.get(i).getProduct_name().toLowerCase().contains(searchText.toLowerCase())) {
////                filterdList.add(arrayList33.get(i));
////            }
//
//                    if(arrayList33.get(i).getProduct_location().toLowerCase().equals(location_spinner.getSelectedItem().toString())){
//                        filterdList.add(arrayList33.get(i));
//                        Log.d("성공 :"," ㅇㅇ");
//                        Log.d("aa",arrayList33.get(i).getProduct_imageurls());
//                        Log.d("bb",arrayList33.get(i).getProduct_image_urls().toString());
//                        Log.d("cc",arrayList33.get(i).getProduct_image_url());
//
//                        Log.d("dd",arrayList33.get(i).getProduct_name());
//                    }
//                    else {
//                        Log.d("실패 :"," ㄴㄴ");
//                    }
//
//                    Log.d("어레이 값은? : ",arrayList33.get(i).getProduct_location().toString() );
//                    Log.d("어레이 값은(스피너) : ",  location_spinner.getSelectedItem().toString());
//                    Log.d("어레이 값은(스피너)333 : " ,location_spinner.getSelectedItem().toString());
////                    if(arrayList33.get(i).getProduct_location().equals(location_spinner.getItemAtPosition(position))) { //스피너의 현재값과 같은 값만
//////                        filterdList.add(arrayList33.get(i));
////                    }
//
//                }




                recyclerProductAdapter.filterList(filterdList);




            }
        });

        return view;

    }
    public void searchFilter(String searchText) {
        filterdList.clear();

        for (int i = 0; i< arrayList33.size(); i++) {
            if (arrayList33.get(i).getProduct_name().toLowerCase().contains(searchText.toLowerCase())) {
                filterdList.add(arrayList33.get(i));

            }

//            if(arrayList33.get(i).getProduct_location().equals(location_spinner)) {
//                filterdList.add(arrayList33.get(i));
//            }

        }
        recyclerProductAdapter.filterList(filterdList);

    }


    public void locationFilter(String location) {
        filterdList.clear();

        for (int i = 0; i< arrayList33.size(); i++) {
            if (arrayList33.get(i).getProduct_location().equals(location)) {
                filterdList.add(arrayList33.get(i));

                Log.d("111 @" +i, filterdList.get(i).getProduct_imageurls());
                Log.d("222 @" +i, filterdList.get(i).getProduct_image_urls().toString());
                Log.d("333 @" +i ,filterdList.get(i).getProduct_image_url().toString());

            }
        }


        try {
            recyclerProductAdapter.filterList(filterdList);
        }catch (Exception e){

        }

//        recyclerProductAdapter(filterdList);



    }

    private void recyclerProductAdapter(ArrayList<Trade_RecyclerProductData> filterdList) {
    }

    public void searchFilter_loaction(String searchText) {
//        for (int i=0; )

        filterdList.clear();



    }



    private  void inintDatabase() {
         mChild = new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot snapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         };
         mDatabaseRef.addChildEventListener(mChild);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    //    mDatabaseRef.removeEventListener(mChild);
    }


}