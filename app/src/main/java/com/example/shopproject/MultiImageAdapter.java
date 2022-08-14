package com.example.shopproject;

import static com.example.shopproject.Sell_Form.product_info;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MultiImageAdapter extends RecyclerView.Adapter<MultiImageAdapter.ViewHolder>{
    private ArrayList<Uri> mData = null ;
    private Context mContext = null ;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    // 생성자에서 데이터 리스트 객체, Context를 전달받음.
    MultiImageAdapter(ArrayList<Uri> list, Context context) {
        mData = list ;
        mContext = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        Button image_del;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조.
            image = itemView.findViewById(R.id.image);
            image_del = itemView.findViewById(R.id.image_del);
        }
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // LayoutInflater - XML에 정의된 Resource(자원) 들을 View의 형태로 반환.
    @Override
    public MultiImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;    // context에서 LayoutInflater 객체를 얻는다.
        View view = inflater.inflate(R.layout.multi_image_item, parent, false) ;	// 리사이클러뷰에 들어갈 아이템뷰의 레이아웃을 inflate.
        MultiImageAdapter.ViewHolder vh = new MultiImageAdapter.ViewHolder(view) ;

        return vh ;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MultiImageAdapter.ViewHolder holder, int position) {

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject");

        Uri image_uri = mData.get(position) ; //어댑터 객체 생성할때 생성자를 이용하여 mdata를 이용하여 사진 Uri를 가져온다.

        String product_info_setImageUrl = image_uri.toString();
        product_info.setImage_url(product_info_setImageUrl);

        Glide.with(mContext)
                .load(image_uri)
                .into(holder.image); //Glide()메소드를 이용하여 image_uri를 holder(뷰 객체에 대한 참조)의 image에 넣는다.

        holder.image_del.setOnClickListener(new View.OnClickListener() { //holder의 image_del(버튼)을 클릭하면
            @Override
            public void onClick(View v) {

                mData.remove(position); // 각 위치에 대한 mdata(정보)를 삭제한다.
                notifyItemRemoved(position);
//                notifyDataSetChanged();

                notifyItemRangeRemoved(position,mData.size()-position);

//                Uri image_uri22 = mData.get(position);
//                String product_info_setImageUrl22 = image_uri22.toString();
//                product_info.setImage_url(product_info_setImageUrl22);

            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}