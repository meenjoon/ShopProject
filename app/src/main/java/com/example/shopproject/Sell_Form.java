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
    private DatabaseReference mDatabaseRef; // ????????? ????????? ?????????

    static int cc;

    private  static  final  String TAG = "MultiImageActivity";

    ArrayList<Uri> uriList = new ArrayList<>();
    ArrayList<Uri> uriList2 = new ArrayList<>();

    RecyclerView recyclerView; // ???????????? ????????? ??????????????????
    MultiImageAdapter adapter; // ????????????????????? ???????????? ?????????


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
        nn="????????????";

        if(loc!=null) { // loc??? ?????? ??? ?????? ?????? ???????????? ????????????.


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


        camera.setOnClickListener(new View.OnClickListener() { //????????? ???????????? ???????????????
            @Override
            public void onClick(View view) {

                //?????????(??????)??? checkSelfPermission()???????????? ???????????? ?????? ????????? ???????????? READ_EXTERNAL_STORAGE(??????) ????????? PackageManager??? ?????? PERMISSION_GRANTED(??????)??? ??? ????????? ???????????????.
                if(ContextCompat.checkSelfPermission(Sell_Form.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //?????? ????????? true ??????

                    Intent intent = new Intent(Intent.ACTION_PICK); //???????????? ?????? ????????? ????????????.
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2222);
                    //startActiviy??? ?????? startActivityForResult??? ????????? ????????? ???????????? ????????? ????????????
                    //????????? ???????????? ????????? ?????? ????????? ??????????????? ????????? ???????????? ????????? onActivityResult?????? ?????? ????????????.

                }

                else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Sell_Form.this); //AlertDialog ?????? ??????
                    dlg.setTitle("????????? ???????????????."); //??????????????? ??????
                    dlg.setMessage("????????? ?????? ????????? ???????????????."); //??????????????? ??????
                    dlg.setPositiveButton("????????????", new DialogInterface.OnClickListener() { //?????????????????? ???????????? ????????? ???????????????
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Sell_Form.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2222);
                            //requestPermissions()???????????? ?????? READ_EXTERNAL_STORAGE(??????) ???????????? ????????????.
                        }
                    });
                    dlg.setNegativeButton("????????????", new DialogInterface.OnClickListener() { //?????????????????? ???????????? ????????? ???????????????
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.create(); //??????????????? ??????
                    dlg.show(); //??????????????? ??????

                }


            }
        });

        aContext = this;

    }



    // ???????????? ??????????????? ????????? ??? ???????????? ?????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        complete = findViewById(R.id.complete);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shopproject");

        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){   // ?????? ???????????? ???????????? ?????? ??????
            Toast.makeText(getApplicationContext(), "???????????? ???????????? ???????????????.", Toast.LENGTH_LONG).show();
        }
        else{   // ???????????? ???????????? ????????? ??????
            if(data.getClipData() == null){     // ???????????? ????????? ????????? ??????
                Log.e("single choice: ", String.valueOf(data.getData()));
                Uri imageUri = data.getData();
                uriList.add(imageUri);

            }
            else{      // ???????????? ????????? ????????? ??????

                ClipData clipData = data.getClipData();
                Log.e("clipData", String.valueOf(clipData.getItemCount()));

                if(clipData.getItemCount() > 10){   // ????????? ???????????? 11??? ????????? ??????
                    Toast.makeText(getApplicationContext(), "????????? 10????????? ?????? ???????????????.", Toast.LENGTH_LONG).show();
                }
                else{   // ????????? ???????????? 1??? ?????? 10??? ????????? ??????
                    Log.e(TAG, "multiple choice");


                    for (int i = 0; i < clipData.getItemCount(); i++){


                        String a = String.valueOf(i);

                        Uri imageUri = clipData.getItemAt(i).getUri();  // ????????? ??????????????? uri??? ????????????.

                        String strUri = imageUri.toString();


                        image_data.add(strUri);


                        StorageReference sotrageRef = storage.getReference();
                        StorageReference riversRef = sotrageRef.child(mFirebaseAuth.getUid()).child(a);
                        UploadTask uploadTask = riversRef.putFile(imageUri);


                        try {
                            uriList.add(imageUri);  //uri??? list??? ?????????.
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));     // ?????????????????? ?????? ????????? ??????


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