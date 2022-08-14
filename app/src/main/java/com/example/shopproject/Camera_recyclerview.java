package com.example.shopproject;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Camera_recyclerview extends  RecyclerView.Adapter<Camera_recyclerview.ViewHolder> {

    final private static String TAG = "   MBj      ";



    private ArrayList<Product_Image> arrayList;

    public Camera_recyclerview(ArrayList<Product_Image> arrayList) { //어댑터 안에 데이터가 들어있는 ArrayList를 읽겠다.(생성자)
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //뷰홀더 생성될때

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_data,parent,false); //리사이클러뷰 데이터 xml의 뷰 참조
      ViewHolder holder = new ViewHolder(view); //holder(데이터가 들어있는) 객체 생성





        return holder; //holder(리사이클러뷰의 데이터) 값을 반환 받는다.


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {



//        holder.iv_item_image.setOnClickListener(new View.OnClickListener() { //만든 holder의 product_content(데이터부분을 가지고있는 xml) 부분을 클릭 했을때
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return (null!=arrayList ? arrayList.size() : 0);
    } //아이템 개수를 세는 함수


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_item_image;



        public ViewHolder(@NonNull View itemView) {
         super(itemView);
         //데이터 xml에 대한 각 항목들을 연결 시켜주기 위해 미리 변수들을 만들어 놓은것과 데이터 xml에 대한 각 항목들을 연결시켜준다.
         this.iv_item_image = (ImageView) itemView.findViewById(R.id.iv_item_movie);


        }
    }




}