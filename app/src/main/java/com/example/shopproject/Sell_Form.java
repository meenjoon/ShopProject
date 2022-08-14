package com.example.shopproject;

import static com.example.shopproject.Fragment1_home.arrayList33;
import static com.example.shopproject.Fragment1_home.recyclerProductAdapter;
import static com.example.shopproject.Main_Activity.loc;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



public class Sell_Form extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public  static Context aContext;

    private ImageView camera, picture, sell_form_back;


    private EditText product_title, product_content,price ;

    private Spinner product_category;

    private TextView complete;

    private ScrollView scroll_picture;
    private TextView loction;


    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스

    static int cc;

    private  static  final  String TAG = "MultiImageActivity";

    ArrayList<Uri> uriList = new ArrayList<>();
    ArrayList<Uri> uriList2 = new ArrayList<>();

    RecyclerView recyclerView; // 이미지를 보여줄 리사이클러뷰
    MultiImageAdapter adapter; // 리사이클러뷰에 적용시킬 어댑터


    private FirebaseStorage storage;

    Random random = new Random();

    static Product_Info product_info = new Product_Info();

    static Trade_RecyclerProductData info_data;


    static ArrayList image_data = new ArrayList();

    ArrayList ss;



    private CustomReceiver mReceiver = new CustomReceiver();

    String nn ;





    ProductInformation product_information = new ProductInformation();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_form);


        loction = findViewById(R.id.loction);
        nn="지역없음";

        if(loc!=null) { // loc에 구글 맵 현재 위치 데이터가 저장된다.


            nn = loc;
            loction.setText(nn);

        }
        else {

            loction.setText(nn);


        }

        image_data.clear();



        storage = FirebaseStorage.getInstance();




        recyclerView  = findViewById(R.id.image_recyclerview);
        recyclerView.setHasFixedSize(true);


        complete = findViewById(R.id.complete);
        camera = findViewById(R.id.camera);


        product_category = findViewById(R.id.product_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_category.setAdapter(adapter);
        product_category.setOnItemSelectedListener(this);

        product_title = findViewById(R.id.product_title);
        price = findViewById(R.id.price);
        product_content = findViewById(R.id.product_content);

        sell_form_back = findViewById(R.id.sell_form_back);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject");

        if(getIntent().getByteArrayExtra("image") != null) {
            byte[] imageBitmap = getIntent().getByteArrayExtra("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBitmap,0,imageBitmap.length);
            picture.setImageBitmap(bitmap);
        }




        sell_form_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        camera.setOnClickListener(new View.OnClickListener() { //카메라 이미지를 클릭했을때
            @Override
            public void onClick(View view) {

                //퍼미션(허용)을 checkSelfPermission()메소드를 이용하여 내가 원하는 퍼미션인 READ_EXTERNAL_STORAGE(앨범) 접근이 PackageManager을 통해 PERMISSION_GRANTED(허용)인 지 아닌지 검사합니다.
                if(ContextCompat.checkSelfPermission(Sell_Form.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //앨범 접근이 true 일때

                    Intent intent = new Intent(Intent.ACTION_PICK); //인텐트를 통해 앨범에 접근한다.
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2222);
                    //startActiviy가 아닌 startActivityForResult를 사용한 이유는 앨범에서 사진을 선택하고
                    //앨범이 닫히면서 선택한 사진 정보가 전달되는데 이것을 연동하는 부분이 onActivityResult에서 하기 때문이다.

                }

                else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Sell_Form.this); //AlertDialog 객체 생성
                    dlg.setTitle("권한이 필요합니다."); //다이얼로그 제목
                    dlg.setMessage("갤러리 접근 권한이 필요합니다."); //다이얼로그 내용
                    dlg.setPositiveButton("동의하기", new DialogInterface.OnClickListener() { //다이얼로그의 동의하기 버튼을 클릭했을때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Sell_Form.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2222);
                            //requestPermissions()메소드를 통해 READ_EXTERNAL_STORAGE(앨범) 퍼미션을 허용한다.
                        }
                    });
                    dlg.setNegativeButton("취소하기", new DialogInterface.OnClickListener() { //다이얼로그의 취소하기 버튼을 클릭했을때
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.create(); //다이얼로그 생성
                    dlg.show(); //다이얼로그 실행

                }


            }
        });

        aContext = this;

    }



    // 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        complete = findViewById(R.id.complete);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject");

        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        }
        else{   // 이미지를 하나라도 선택한 경우
            if(data.getClipData() == null){     // 이미지를 하나만 선택한 경우
                Log.e("single choice: ", String.valueOf(data.getData()));
                Uri imageUri = data.getData();
                uriList.add(imageUri);

            }
            else{      // 이미지를 여러장 선택한 경우

                ClipData clipData = data.getClipData();
                Log.e("clipData", String.valueOf(clipData.getItemCount()));

                if(clipData.getItemCount() > 10){   // 선택한 이미지가 11장 이상인 경우
                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                }
                else{   // 선택한 이미지가 1장 이상 10장 이하인 경우
                    Log.e(TAG, "multiple choice");


                    for (int i = 0; i < clipData.getItemCount(); i++){


                        String a = String.valueOf(i);

                        Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.

                        String strUri = imageUri.toString();


                        image_data.add(strUri);


                        StorageReference sotrageRef = storage.getReference();
                        StorageReference riversRef = sotrageRef.child(mFirebaseAuth.getUid()).child(a);
                        UploadTask uploadTask = riversRef.putFile(imageUri);


                        try {
                            uriList.add(imageUri);  //uri를 list에 담는다.
                            uriList2.add(imageUri);


                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }

                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                            byte[] reviewImage = stream.toByteArray();
                            String simage = byteArrayToBinaryString(reviewImage);

                            product_information.setProduct_image(simage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        recyclerView.removeAllViewsInLayout();
                        itemRefresh();

                        complete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String product_price1 = price.getText().toString().trim();
                                String product_title1 = product_title.getText().toString().trim();
                                String product_content1 = product_content.getText().toString().trim();
                                String product_category1 = product_category.getSelectedItem().toString().trim();

                                product_info.setProduct_title(product_title1);
                                product_info.setPrice(product_price1);
                                product_info.setSpinner(product_category1);
                                product_info.setProduct_content(product_content1);

                                arrayList33.add(new Trade_RecyclerProductData(Integer.toString(cc),product_info.getImage_url(),R.drawable.heart_black,R.drawable.heart_red ,"0",product_title1,product_price1,loction.getText().toString().trim(),product_content1,product_info.getSpinner(),product_title1,image_data,image_data.toString(),Integer.toString(0),loction.getText().toString().trim()));

                                cc++;

                                info_data = new Trade_RecyclerProductData(Integer.toString(cc),product_info.getImage_url(),R.drawable.heart_black,R.drawable.heart_red, "0",product_title1,product_price1,loction.getText().toString().trim(),product_content1,product_info.getImage_url(),product_title1,ss,image_data.toString(),Integer.toString(0),loction.getText().toString().trim());

//                                mDatabaseRef.child(info_data.getProduct_name()).setValue(info_data);

                                recyclerProductAdapter.notifyDataSetChanged();

                                finish();

                            }
                        });

                    }

                }
            }
        }
        aContext = this;
    }

    private void itemRefresh() {


        adapter = new MultiImageAdapter(uriList, getApplicationContext());

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));     // 리사이클러뷰 수평 스크롤 적용


    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }









}