package com.example.shopproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//내가 이름을 정의한 어댑터는  RecyclerView.Adapter<내가 이름을 정의한 어댑터.내가 정의한 뷰 홀더 이름>을 통해 만든다.



public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {


    private ArrayList<RecyclerProductData> arrayList; //데이터 ArrayList 생성

    public RecyclerProductAdapter(ArrayList<RecyclerProductData> arrayList) { //어댑터 안에 데이터가 들어있는 ArrayList를 읽겠다.(생성자)
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //뷰홀더 생성될때

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_data,parent,false); //리사이클러뷰 데이터 xml의 뷰 참조
        ViewHolder holder = new ViewHolder(view); //holder(데이터가 들어있는) 객체 생성


        return holder; //holder(리사이클러뷰의 데이터) 값을 반환 받는다.

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder(리사이클러뷰의 데이터)들의 각각의 부분에 대해서 내용을 채워준다.
        holder.product_count.setText(String.valueOf(arrayList.get(position).getProduct_count()));
        holder.product_image.setImageResource(arrayList.get(position).getProduct_image());   // arrayList.get(position).getProduct_image() 이런 부분 중요.
        holder.product_name.setText(arrayList.get(position).getProduct_name());             // arrayList의 위치를 참조받고 거기서 각각의 get함수의 대한 내용을 얻어온다.
        holder.product_content.setText(arrayList.get(position).getProduct_content());
        holder.product_chatting.setText(arrayList.get(position).getProduct_chatting());
        holder.product_buy.setText(arrayList.get(position).getProduct_buy());




//        holder.itemView.setTag(position);
         holder.product_content.setOnClickListener(new View.OnClickListener() { //만든 holder의 product_content(데이터부분을 가지고있는 xml) 부분을 클릭 했을때
             @Override
             public void onClick(View view) {
                 Context context = view.getContext();
                 Intent intent = new Intent(context,Product_Content.class); // 인텐트 생성하여 Product_Content 액티비티로 이동
                 context.startActivity(intent);
                Toast.makeText(context, arrayList.get(position).getProduct_chatting(), Toast.LENGTH_LONG).show();

             }
         });

        //

      holder.product_buy.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

                AlertDialog.Builder oDialog = new AlertDialog.Builder(view.getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog);



              oDialog.setMessage("정말로 구매하시겠습니까?")
                      .setTitle("구입하기")
                      .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                      {
                          @Override
                          public void onClick(DialogInterface dialog, int which)
                          {
                              Log.i("Dialog", "취소");
                              Toast.makeText(view.getContext(), "취소", Toast.LENGTH_SHORT).show();
                          }
                      })
                      .setNeutralButton("예", new DialogInterface.OnClickListener()
                      {
                          public void onClick(DialogInterface dialog, int which)
                          {
                              Intent intent = new Intent(view.getContext(),Buy.class);
                              view.getContext().startActivity(intent);

//                              Context context = view.getContext();
//                              Intent intent = new Intent(context,Buy.class); // 인텐트 생성하여 Product_Content 액티비티로 이동
//                              context.startActivity(intent);
                              //위에 있는것들도 이용 가능



                          }
                      })
                      .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.


                      .show();







          }
      });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { //뷰홀더의 부분을 길게 클릭했을때(단, 버튼 같은 특정 작용을 작용하는 것들을 제외하고서)
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition()); // holer의 getAdapterPosition()부분을 지운다.

                return true; // true 값을 반환한다.
            }
        });


    }


    @Override
    public int getItemCount() {
        return (null!=arrayList ? arrayList.size() : 0);
    } //아이템 개수를 세는 함수

    public void filterList(ArrayList<RecyclerProductData> filterdList) {
        arrayList = filterdList;
        notifyDataSetChanged();
    }

    public void remove(int position) {  //지우는 함수
        try{
            arrayList.remove(position); // arrayList의 해당하는 position 지우기
            notifyItemRemoved(position); // arrayList의 해당하는 position 지우기
        }catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //내가 이름을 정한 ViewHolder는 RecyclerView.ViewHolder를 상속받아 생성한다.

        //데이터 xml에 대한 각 항목들을 연결 시켜주기 위해 미리 변수들을 만들어 타입을 지정해 준다.
        protected  TextView product_count;
        protected ImageView product_image;
        protected TextView product_name;
        protected Button product_content;
        protected Button product_chatting;
        protected  Button product_buy;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //데이터 xml에 대한 각 항목들을 연결 시켜주기 위해 미리 변수들을 만들어 놓은것과 데이터 xml에 대한 각 항목들을 연결시켜준다.
            this.product_count = (TextView) itemView.findViewById(R.id.product_count);
            this.product_image =(ImageView) itemView.findViewById(R.id.product_image);
            this.product_name = (TextView)  itemView.findViewById(R.id.product_name);
            this.product_content = (Button) itemView.findViewById(R.id.product_content);
            this.product_chatting = (Button) itemView.findViewById(R.id.product_chatting);
            this.product_buy = (Button) itemView.findViewById(R.id.product_buy);

        }
    }



}