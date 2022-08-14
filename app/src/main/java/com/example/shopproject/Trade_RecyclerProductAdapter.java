package com.example.shopproject;

import static com.example.shopproject.Sell_Form.info_data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
//내가 이름을 정의한 어댑터는  RecyclerView.Adapter<내가 이름을 정의한 어댑터.내가 정의한 뷰 홀더 이름>을 통해 만든다.




public class Trade_RecyclerProductAdapter extends RecyclerView.Adapter<Trade_RecyclerProductAdapter.ViewHolder> {


    private ArrayList<Trade_RecyclerProductData> arrayList; //데이터 ArrayList 생성
    private Context aContext; //Context 값을 aContext 이름 설정
    private ArrayList image_arrayList;
    static int max;

    public Trade_RecyclerProductAdapter(Context context, ArrayList<Trade_RecyclerProductData> arrayList, ArrayList image_arrayList) { //Trade_RecyclerProductAdapter의 생성자[Context, ArrayList<Trade_RecyclerProductData> arrayList, ArrayList image_arrayList] 의 변수를 넣어줘야한다.
        this.aContext = context;
        this.arrayList = arrayList;
        this.image_arrayList = image_arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //뷰홀더 생성될때

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //XML에 미리 정의해둔 틀을 실제 메모리에 올려주는 역할인 LayoutInflater를 사용한다.
        View view = inflater.inflate(R.layout.trade_recyclerview_data, parent, false); // trade_recyclerview_data 라는 xml과 연결해준다.

        Trade_RecyclerProductAdapter.ViewHolder holder = new Trade_RecyclerProductAdapter.ViewHolder(view); // new Trade_RecyclerProductAdapter.ViewHolder(view)를 통해 각각의 목록에 접근가능한 holder를 생성한다.

        return holder; //holder(리사이클러뷰의 데이터) 값을 반환 받는다.

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder(리사이클러뷰의 데이터)들의 각각의 부분에 대해서 내용을 채워준다.

        // < arrayList의 위치를 참조받고 거기서 각각의 get함수의 대한 내용을 얻어온다. >



        holder.product_count.setText(Integer.toString(Integer.parseInt(arrayList.get(position).getProduct_count()) + 1)); //holder.product_count(물품 등록 수)에 생성자를 통한 arrayList.get(position).getProduct_count())을 이용하여 값을 넣는다.
        holder.product_image.setVisibility(View.VISIBLE); //holder.product_image의 이미지를 보이게 설정
        Glide.with(holder.itemView).load(arrayList.get(position).getProduct_image()).placeholder(R.drawable.camera)
                    .error(R.drawable.heart_red)
                    .fallback(R.drawable.heart_black)
                    .into(holder.product_image);
        //Glide 함수를 통해 holder.itemView의 into(holder.product_image)에
        //load(이미지 값(url[arrayList.get(position).getProduct_image()]))을 넣는다
        //또한 placeholder(로딩 중일때는 R.drawble.camera 값)을 넣고 오류(error 일때는 R.drawable.heart_red의 값을 넣고)
        //또한 fallback(null 값일 때는 R.drawable.heart_black)을 넣는다.



        holder.product_name.setText(arrayList.get(position).getProduct_name()); //holder.product_name(물품 제목)에 생성자를 통한 arrayList.get(position).getProduct_name())을 이용하여 값을 넣는다.
        holder.product_heart_black_image.setImageResource(arrayList.get(position).getProduct_heart_black_image());
        //holder.product_heart_black_image(물품의 좋아요 사진(검은색하트)에 생성자를 통한 arrayList.get(position).getProduct_heart_black_image())을 setImageResource()를 이용하여 값을 넣는다.
        holder.heart_red.setImageResource(arrayList.get(position).getProduct_heart_red_image());
        //holder.product_heart_black_image(물품의 좋아요 사진(빨간색)에 생성자를 통한 arrayList.get(position).getProduct_heart_red_image())을 setImageResource()를 이용하여 값을 넣는다.(이 값은 실제로 공간이 있는 값이 아니라 빨간 하트를 가져오기 위한 참조 값이다. xml 파일에서 android:visibility="gone" 설정.)

        Drawable blackheart = holder.product_heart_black_image.getDrawable(); //holder.product_heart_black_image에 들어간  검정색 하트 Url 형식을 Drawable 형식으로 만들기
        Drawable redheart = holder.heart_red.getDrawable(); //holder.product_heart_black_image에 들어간  검정색 하트 Url 형식을 Drawable 형식으로 만들기
        //참고 : https://www.masterqna.com/android/24619/imageview%EC%97%90-src%EB%A1%9C-%EB%84%A3%EC%9D%80-%EC%9D%B4%EB%AF%B8%EC%A7%80%EC%99%80-%EB%8B%A4%EB%A5%B8-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%B9%84%EA%B5%90-%EC%A7%88%EB%AC%B8%EC%9E%85%EB%8B%88%EB%8B%A4

        holder.product_heart_count.setText(arrayList.get(position).getProduct_heart_count());//holder.product_heart_count(물품 좋아요 수)에 생성자를 통한 arrayList.get(position).getProduct_heart_count())을 이용하여 값을 넣는다.


        holder.product_location.setText(arrayList.get(position).getProduct_location()); //holder.product_location(물품 사용자 위치)에 생성자를 통한 arrayList.get(position).getProduct_location())을 이용하여 값을 넣는다.
        //holder.product_city.setText(arrayList.get(position).getProduct_city());

        holder.product_price.setText(arrayList.get(position).getProduct_price()+"원"); //holder.product_price(물품 가격)에 생성자를 통한 arrayList.get(position).getProduct_price())을 이용하여 값을 넣는다.

        holder.product_category.setText(arrayList.get(position).getProduct_category().toString()); //holder.product_category(물품 카테고리)에 생성자를 통한 arrayList.get(position).getProduct_category())을 이용하여 값을 넣는다.
        holder.product_content.setText(arrayList.get(position).getProduct_content().toString()); //holder.product_category(물품 카테고리)에 생성자를 통한 arrayList.get(position).getProduct_category())을 이용하여 값을 넣는다.
        holder.product_image_urls.setText(arrayList.get(position).getProduct_imageurls().toString()); //holder.product_image_urls(물품 카테고리)에 생성자를 통한 arrayList.get(position).getProduct_imageurls())을 이용하여 값을 넣는다.


        max = Integer.parseInt(arrayList.get(0).getProduct_count()); //max(최대값)은 arrayList.get(0).getProduct_count()을 고정값이다.

        for(int i=1; i< getItemCount(); i++){ // 쓰레드 사용을 위한 max 변수 값 구하기[i=1에서 시작, i<아이템 갯수 만큼 for문 사용]

            if(arrayList.get(i).getProduct_count().isEmpty()){ //arrayList.get(i).getProduct_count()이 비어있다면
            }
            else { //arrayList.get(i).getProduct_count()이 비어있지 않다면
                if (max < Integer.parseInt(arrayList.get(i).getProduct_count())) { //max의 값(arrayList.get(0).getProduct_count())이 (arrayList.get(i).getProduct_count()) 보다 작다면
                    max = Integer.parseInt(arrayList.get(i).getProduct_count()); // max의 값을 arrayList.get(i).getProduct_count()으로 설정
                 }
                else if( max > Integer.parseInt(arrayList.get(i).getProduct_count())) { //max의 값(arrayList.get(0).getProduct_count())이 (arrayList.get(i).getProduct_count()) 보다 크다면
                    // max의 값 변한 없음
                }
            }
        }

        { // 사람들이 현재 가장 많이 보는 상품을 작업쓰레드를 이용하여 토스트 메세지로 10초마다 띄어준다.
            String strmax = Integer.toString(max);
//            if (strmax.isEmpty()) {
//            } else {
//                BackThread most_popular_product = new BackThread(); //내가 만든 BackThread(작업스레드)객체 생성
//                most_popular_product.start();
//            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() { //holder의 itemview를 클릭했을때 Buy_form.class(물품 정보 상세 페이지)로의 이동을 하는 기능
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aContext, Buy_form.class); //holder의 itemview를 클릭 시 Buy_form.class 이동을 위해 인텐트 이용을 위해 Fragment1에서 어댑터 객체 생성자로 부터 Context를 변수로 얻어 온다.
                aContext.startActivity(intent); // 인텐트 시작

                info_data.setProduct_price(holder.product_price.getText().toString()); //static(전역 변수)인 ArrayList<Trade_RecyclerProductData> info_data를 참조하여 holder.product_price.getText()로 부터 값을 불러와 setProduct_price 하여 값 저장한다.
                info_data.setProduct_name(holder.product_name.getText().toString()); //static(전역 변수)인 ArrayList<Trade_RecyclerProductData> info_data를 참조하여 holder.product_name.getText()로 부터 값을 불러와 setProduct_name 하여 값 저장한다.
                info_data.setProduct_category(holder.product_category.getText().toString()); //static(전역 변수)인 ArrayList<Trade_RecyclerProductData> info_data를 참조하여 holder.product_category.getText()로 부터 값을 불러와 setProduct_category 하여 값 저장한다.
                info_data.setProduct_content(holder.product_content.getText().toString()); //static(전역 변수)인 ArrayList<Trade_RecyclerProductData> info_data를 참조하여 holder.product_content.getText()로 부터 값을 불러와 setProduct_content 하여 값 저장한다.
                info_data.setProduct_imageurls( holder.product_image_urls.getText().toString()); //static(전역 변수)인 ArrayList<Trade_RecyclerProductData> info_data를 참조하여 holder.product_image_urls.getText()로 부터 값을 불러와 setProduct_imageurls 하여 값 저장한다.

                Log.d("price   ", info_data.getProduct_price());
                Log.d("name   ", info_data.getProduct_name());
                Log.d("category    ", info_data.getProduct_category());
                Log.d("content    ", info_data.getProduct_content());
                Log.d("imageurls     ", info_data.getProduct_imageurls());
            }
        });


        holder.product_heart_black_image.setOnClickListener(new View.OnClickListener() {//holder의 product_heart_black_image(검정색 하트) 클릭했을때 빨간 하트로 변하고 좋아요 수가 1 증가 / 빨간색 하트 눌렀을 때 검정 하트로 변하고 좋아요 수 1 감소
            @Override
            public void onClick(View v) {

                Log.d("검은색 하트 비트맵 값(고정값) : ", drawableToBitmap(blackheart).toString());
                Log.d("빨간색 하트 비트맵 값(고정값) : ", drawableToBitmap(redheart).toString());

                int heart_count = Integer.parseInt((String) holder.product_heart_count.getText()); //heart_count(좋아요 수)변수에 holder.product_heart_count.getText()의 값을 넣는다.

                if(heart_count>=0) { //heart_count(좋아요 수)가 0보다 클 때
                    if (drawableToBitmap(holder.product_heart_black_image.getDrawable()).equals(drawableToBitmap(blackheart)) ) {
                        //현재 나의 상품 좋아요 하트 색인 (drawableToBitmap()메소드를 통해 holder.product_heart_black_image를 getDrawable()으로 Drawable형식을 Bitemap 형식으로 바꿈)
                        //이것이 변하지 않는 검정색 하트 색인 blackheart(drawableToBitmap()메소드를 통해 처음에 holder.product_heart_black_image를 getDrawable()으로 Drawable형식을 Bitmap 형식으로 저장하고 변하지 않은 변수값임)
                        // 즉, 현재 나의 상품 좋아요 하트 색 = 검정색 하트라면 / 현재 나의 상품 좋아요 하트 색이 검정색 이라면

                        holder.product_heart_black_image.setImageResource(R.drawable.heart_red); //holder.product_heart_black_image에 setImageResource()메소드를 통해 drawable 디렉토리에 저장되어 있는 빨간색 하트로 바꾼다.

                        holder.product_heart_count.setText(String.valueOf(heart_count + 1)); //holder.product_heart_count의 수를 1 증가 시킨다.
                        //   android.content.res.Resources$NotFoundException: String resource ID #0x1 에러 해결
                        // String 자리에 int 를 넣어 줬음

                        Log.d("하트 색이 동일 한가 ? " , "하트 색 동일하다.(현재 하트 색= 검정색 하트색");

                        Log.d("현재 색(검정색)과 검정색 하트 색과 동일할 때 하트의 비트맵 값: ", drawableToBitmap(holder.product_heart_black_image.getDrawable()).toString());

                    }

                    else { // 현재 나의 상품 좋아요 하트 색 != 검정색 이라면 즉, 현재 나의 상품 좋아요 색이 빨간색 하트 이라면
                        holder.product_heart_black_image.setImageResource(R.drawable.heart_black); //holder.product_heart_black_image에 setImageResource()메소드를 통해 drawable 디렉토리에 저장되어 있는 검정색 하트로 바꾼다.
                        holder.product_heart_count.setText(String.valueOf(heart_count - 1));//holder.product_heart_count의 수를 1 감소 시킨다.
                        Log.d("하트 색이 동일 한가 ? " , "하트 색 동일하지 않다.(현재 하트 색 = 빨간색 하트색");
                        Log.d("현재 색(빨간색)과 검정색 하트 색과 동일할 때 하트의 비트맵 값:", drawableToBitmap(holder.product_heart_black_image.getDrawable()).toString());
                    }
                }

            }
        });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { //뷰홀더의 부분을 길게 클릭했을때(단, 버튼 같은 특정 작용을 작용하는 것들을 제외하고서)
            @Override
            public boolean onLongClick(View view) {

               arrayList.remove(holder.getAdapterPosition()); //
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),arrayList.size());

//                remove(holder.getAdapterPosition()); // holer의 getAdapterPosition()부분을 지운다.

                return true; // true 값을 반환한다.
            }
        });
    }


    public static Bitmap drawableToBitmap (Drawable drawable) { //drawable 데이터 형태를 Bitmap 형태로 변환)
        if (drawable instanceof BitmapDrawable)  {  //instanceof : 객체 타입을 확인하는 연산자,형변환 가능여부를 확인하며 true/false로 결과 반환
            return ((BitmapDrawable)drawable).getBitmap(); // BitmapDrawable 형식에서는 getBitmap() 메소드 사용가능하다.
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888); //임의의 bitmap 원하는 내용으로 그리기
        Canvas canvas = new Canvas(bitmap); //일종의 도화지 생성
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight()); //위치 및 크기 설정
        drawable.draw(canvas); // drawable 그리기

        return bitmap;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    public int getItemCount() {
        return (null!=arrayList ? arrayList.size() : 0);
    } //아이템 개수를 세는 함수

    public void filterList(ArrayList<Trade_RecyclerProductData> filterdList) {
        arrayList = filterdList;
        notifyDataSetChanged();
    }

    public void remove(int position) {  //항목 지우는 메소드 구현
        try{
            arrayList.remove(position); // arrayList의 해당하는 position 지우기

        }catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //내가 이름을 정한 ViewHolder는 RecyclerView.ViewHolder를 상속받아 생성한다.

        //데이터 xml에 대한 각 항목들을 연결 시켜주기 위해 미리 변수들을 만들어 타입을 지정해 준다.
        protected  TextView product_count;
        protected ImageView product_image;

        protected  ImageView product_heart_black_image;
        protected  TextView product_heart_count;

        protected TextView product_name;
        protected TextView product_price;
        protected TextView product_location;




        protected TextView product_category;
        protected TextView product_image_urls;
        protected TextView product_content;
        protected TextView click_count;

        protected ImageView heart_red;

        public ImageView getHeart_red() {
            return heart_red;
        }

        public void setHeart_red(ImageView heart_red) {
            this.heart_red = heart_red;
        }

        public TextView getProduct_count() {
            return product_count;
        }

        public void setProduct_count(TextView product_count) {
            this.product_count = product_count;
        }

        public ImageView getProduct_image() {
            return product_image;
        }

        public void setProduct_image(ImageView product_image) {
            this.product_image = product_image;
        }

        public ImageView getProduct_heart_black_image() {
            return product_heart_black_image;
        }

        public void setProduct_heart_black_image(ImageView product_heart_black_image) {
            this.product_heart_black_image = product_heart_black_image;
        }

        public TextView getProduct_heart_count() {
            return product_heart_count;
        }

        public void setProduct_heart_count(TextView product_heart_count) {
            this.product_heart_count = product_heart_count;
        }

        public TextView getProduct_name() {
            return product_name;
        }

        public void setProduct_name(TextView product_name) {
            this.product_name = product_name;
        }

        public TextView getProduct_price() {
            return product_price;
        }

        public void setProduct_price(TextView product_price) {
            this.product_price = product_price;
        }

        public TextView getProduct_location() {
            return product_location;
        }

        public void setProduct_location(TextView product_location) {
            this.product_location = product_location;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //데이터 xml에 대한 각 항목들을 연결 시켜주기 위해 미리 변수들을 만들어 놓은것과 데이터 xml에 대한 각 항목들을 연결시켜준다.
            this.product_count = (TextView) itemView.findViewById(R.id.product_count);
            this.product_image =(ImageView) itemView.findViewById(R.id.product_image);

            this.product_heart_black_image =(ImageView)  itemView.findViewById(R.id.heart_black_image);
            this.heart_red = (ImageView) itemView.findViewById(R.id.heart_red);
            this.product_heart_count = (TextView)  itemView.findViewById(R.id.heart_count);

            this.product_name = (TextView)  itemView.findViewById(R.id.product_name);
            this.product_price = (TextView)  itemView.findViewById(R.id.product_price);
            this.product_location = (TextView)  itemView.findViewById(R.id.product_location);

            this.product_category = (TextView) itemView.findViewById(R.id.adapter_category);
            this.product_content = (TextView) itemView.findViewById(R.id.adapter_content);
            this.product_image_urls = (TextView) itemView.findViewById(R.id.adapter_image_urls);

        }
    }

//    class BackRunnable implements Runnable { //작업 쓰레드를 Runnable를 상속 받아 구현
//
//        @Override
//        public void run() {
//            while (true){
//                mHandler.sendEmptyMessage(0);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }

    class BackThread extends Thread {  //작업 쓰레드를 Thread를 상속 받아 구현

        @Override
        public void run() {
            while (true){
                mHandler.sendEmptyMessage(0); //mHandler 에게 메세지로 0의 값을 전달한다.
                try {
                    Thread.sleep(10000);  // 30초 간격 마다
                } catch (InterruptedException e) { //예외 처리
                    e.printStackTrace();
                }

            }
        }

    }


    Handler mHandler = new Handler() { // mHandler라는 핸들러 객체를 생성한다.
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0) { //만약에 받는 메시지가 0 이라면

                int max1 = max; //


                try {

                    Toast.makeText(aContext, "현재 사람들이 가장 많이 본 상품은 " + arrayList.get(max1).getProduct_name().toString(), Toast.LENGTH_SHORT).show();
                    //Toast 메세지를 통해 arrayList.get(max1).getProduct_name()의 값을 5초마다 띄운다.

                    Log.d("thread : ", arrayList.get(max).getProduct_name()); //값을 가져오는 arrayList.get(max).getProduct_name() 확인용
                    Log.d("thread : ", String.valueOf(max)); // 값을 가져오는 max 확인용
                }
                catch (Exception e){

                }

            }
        }
    };



}