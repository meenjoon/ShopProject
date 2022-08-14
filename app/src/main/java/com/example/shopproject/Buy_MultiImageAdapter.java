package com.example.shopproject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Buy_MultiImageAdapter extends RecyclerView.Adapter<Buy_MultiImageAdapter.ViewHolder>{
    private ArrayList<Uri> mData = null ;
    private Context mContext = null ;

    // 생성자에서 Uri 데이터 리스트 객체, Context를 전달받음.
    Buy_MultiImageAdapter(ArrayList<Uri> list, Context context) {
        mData = list ;
        mContext = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조.
            image = itemView.findViewById(R.id.buy_image_data);

        }
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // LayoutInflater - XML에 정의된 Resource(자원) 들을 View의 형태로 반환.
    @Override
    public Buy_MultiImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ; // context에서 LayoutInflater 객체를 얻는다.
        View view = inflater.inflate(R.layout.buy_image_data, parent, false) ; // 리사이클러뷰에 들어갈 아이템뷰의 레이아웃을 inflate.
        Buy_MultiImageAdapter.ViewHolder vh = new Buy_MultiImageAdapter.ViewHolder(view) ; //뷰홀더 객체 vh 생성

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(Buy_MultiImageAdapter.ViewHolder holder, int position) {



//        Button image_del;
//        image_del = findViewById(R.id.image_del);

        Uri image_uri = mData.get(position) ; //Uri 형식변수 이름 image_uri를 생성자로부터 얻어온 arraylist인 mData의 position을 얻는다.

        String imageuritostr = image_uri.toString(); //image_uri를 string형태로 새로운 변수에 넣어준다.
        Log.d("$$$$$$$$$$$$$$$$$", imageuritostr);

//        product_info.setImage_url(imageuritostr);

        Glide.with(mContext)  //Glide메소드를 이용하여 홀더 부분에 image_uri를 넣는다(이미지를 넣는다.)
                .load(image_uri)
                .into(holder.image);

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }


}