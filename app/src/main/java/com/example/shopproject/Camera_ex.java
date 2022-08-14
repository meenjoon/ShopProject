package com.example.shopproject;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Camera_ex extends AppCompatActivity {

    final private static String TAG = "   MBj      ";

    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_PERMISSION = 11;
    public static final int REQUEST_TAKE_PHOTO = 10;

    private String mCurrentPhotoPath;

    private ImageView ivCapture;
    private Button btnCapture, btnSave, button_back, load_image;
    private int requestCode;
    private int resultCode;
    private Intent data;

    private ArrayList<Product_Image> arrayList, filterdList; //리사이클러뷰 데이터가 담겨 있는 곳을 ArrayList로 참조한다.
    private Camera_recyclerview Camera_recyclerview_Adapter; //내가 만든 RecyclerProductAdapter 의 변수를 생성한다.
    private RecyclerView recyclerView; //기본적으로 제공하는 RecyclerView 의 변수를 생성한다.
    private LinearLayoutManager linearLayoutManager; //기본적으로 제공하는 LinearLayoutManager의 변수를 생성한다.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_ex);

        checkPermission();

        btnCapture = findViewById(R.id.button_shooting);
        btnSave = findViewById(R.id.button_imagefind);
        button_back = findViewById(R.id.button_back);
        ivCapture = findViewById(R.id.iv_photo);
        load_image = findViewById(R.id.load_image);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한다.

        linearLayoutManager = new LinearLayoutManager(this); //만든 linearLayoutManager 변수를 통해 객체를 만든다.
        recyclerView.setLayoutManager(linearLayoutManager); //만든 recyclerView 변수를 리사이클러뷰가 있는 xml 파일의 리사이클러뷰와 연결한것과 위에서 만든 linearLayoutManager 객체와 연결해준다.

        arrayList = new ArrayList<>();  //arrayList 에 대한 객체를 생성한다.

        Camera_recyclerview_Adapter = new Camera_recyclerview(arrayList); //만든 recyclerProductAdapter 변수를 통해 객체를 만드는 이때 arrayList객체를 넣어 만들어 준다.
        recyclerView.setAdapter(Camera_recyclerview_Adapter);


        loadImgArr();
//        Toast.makeText(Camera_ex.this, (CharSequence) getFilesDir(), Toast.LENGTH_SHORT).show();
        File aa = getFilesDir();
        Log.w(TAG , String.valueOf(aa) + "adsfasdfsfasdfasfd");

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ContextCompat.checkSelfPermission(Camera_ex.this ,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Log.w(TAG, "카메라 권한 있음");
                }
                else {
                    Log.w(TAG, "카메라 권한 없음");
                }
                captureCamera();
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Camera_ex.this, Trade.class);
//                startActivity(intent);
                finish();
            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    BitmapDrawable drawable = (BitmapDrawable) ivCapture.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    //찍은 사진이 없으면
                    if (bitmap == null) {
                        Toast.makeText(Camera_ex.this, "저장할 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        //저장
                        saveImg();
                        mCurrentPhotoPath = ""; //initialize
                    }
                } catch (Exception e) {
                    Log.w(TAG, "SAVE ERROR!", e);
                }
            }

            ;
        });

        load_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);





            }
        });
    }

    private void captureCamera() { //사진 촬영
        Log.w(TAG, "11111");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Object aa = takePictureIntent.getPackage();
        if (takePictureIntent != null){
            Log.w(TAG, "인텐트 존재해~~");
        } else {
            Log.w(TAG, "인텐트 없어~~");
        }

//        takePictureIntent.getPackage();


        Log.w(TAG, "1111..55555");
        // 인텐트를 처리 할 카메라 액티비티가 있는지 확인
        ComponentName ff = takePictureIntent.resolveActivity(getPackageManager());
        Log.w(TAG, String.valueOf(ff));
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        Log.w(TAG, "2222222");
        // 촬영한 사진을 저장할 파일 생성
        File photoFile = null;

        try {
            //임시로 사용할 파일이므로 경로는 캐시폴더로
            Log.w(TAG, "333333333");
            File tempDir = getCacheDir();

            //임시촬영파일 세팅
            String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String imageFileName = "Capture_" + timeStamp + "_"; //ex) Capture_20201206_

            File tempImage = File.createTempFile(
                    imageFileName,  /* 파일이름 */
                    ".jpg",         /* 파일형식 */
                    tempDir      /* 경로 */
            );

            // ACTION_VIEW 인텐트를 사용할 경로 (임시파일의 경로)
            mCurrentPhotoPath = tempImage.getAbsolutePath();

            photoFile = tempImage;

        } catch (IOException e) {
            //에러 로그는 이렇게 관리하는 편이 좋다.
            Log.w(TAG, "파일 생성 에러!", e);
        }

        //파일이 정상적으로 생성되었다면 계속 진행
        if (photoFile != null) {
            //Uri 가져오기
            Log.w(TAG, "444444444444");
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.shopproject",
                    photoFile);
            //인텐트에 Uri담기
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            //인텐트 실행
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                startActivity(takePictureIntent);
        }
//        }
    }

    //이미지저장 메소드
    private void saveImg() {

        try {
            //저장할 파일 경로
            File storageDir = new File(getFilesDir() + "/capture");
            if (!storageDir.exists()) //폴더가 없으면 생성.
                storageDir.mkdirs();

            String filename = "캡쳐파일" + ".jpg";

            // 기존에 있다면 삭제
            File file = new File(storageDir, filename);
            boolean deleted = file.delete();
            Log.w(TAG, "Delete Dup Check : " + deleted);
            FileOutputStream output = null;

            try {
                output = new FileOutputStream(file);
                BitmapDrawable drawable = (BitmapDrawable) ivCapture.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, output); //해상도에 맞추어 Compress
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert output != null;
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.e(TAG, "Captured Saved");
            Toast.makeText(this, "Capture Saved ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.w(TAG, "Capture Saving Error!", e);
            Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();

        }
    }

    private void loadImgArr() {
        try {

            File storageDir = new File(getFilesDir() + "/capture");
            String filename = "캡쳐파일" + ".jpg";

            File file = new File(storageDir, filename);
//            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream("/data/user/0/com.example.shopproject/files/캡쳐파일.jpg"));
//            ivCapture.setImageBitmap(bitmap);
            Bitmap bit = BitmapFactory.decodeFile("/data/user/0/com.example.shopproject/files/capture/캡쳐파일.jpg");

            ivCapture.setImageBitmap(bitmap);

//            Product_Image imagedata = new Product_Image(bit);
//            arrayList.add(imagedata);
//
//            Camera_recyclerview_Adapter.notifyDataSetChanged();



        } catch (Exception e) {
            Log.w(TAG, "Capture loading Error!", e);
            Toast.makeText(this, "load failed", Toast.LENGTH_SHORT).show();
        }
    }










    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            //after capture
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {

                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));

                        if (bitmap != null) {
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

//                            //사진해상도가 너무 높으면 비트맵으로 로딩
//                            BitmapFactory.Options options = new BitmapFactory.Options();
//                            options.inSampleSize = 8; //8분의 1크기로 비트맵 객체 생성
//                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                            Bitmap rotatedBitmap = null;
                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }

                            //Rotate한 bitmap을 ImageView에 저장
                            ivCapture.setImageBitmap(rotatedBitmap);

                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            Log.w(TAG, "onActivityResult Error !", e);
        }

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    ivCapture.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

    }



    //카메라에 맞게 이미지 로테이션
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    //권한 확인
    public void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED
                || permissionRead != PackageManager.PERMISSION_GRANTED
                || permissionWrite != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // 권한이 취소되면 result 배열은 비어있다.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                    finish(); //권한이 없으면 앱 종료
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        checkPermission(); //권한체크
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//
//                    ivCapture.setImageBitmap(img);
//                } catch (Exception e) {
//
//                }
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//            }
//        }
//    }



}