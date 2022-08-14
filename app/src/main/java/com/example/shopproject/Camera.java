package com.example.shopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Camera extends AppCompatActivity {
    final private static String TAG = "GILBOMI";
    final static int TAKE_PICTURE = 1;
        //경로 변수와 요청변수 생성
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    private ImageView iv_photo;
    private Button button_shooting, button_imagefind, button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_shooting);

        button_shooting = findViewById(R.id.button_shooting);
        button_imagefind = findViewById(R.id.button_imagefind);
        button_back = findViewById(R.id.button_back);
        iv_photo = findViewById(R.id.iv_photo);


        button_shooting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "권한 설정 완료");
                    } else {
                        Log.d(TAG, "권한 설정 요청");
                        ActivityCompat.requestPermissions(Camera.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }

                switch (view.getId()) {
                    case R.id.button_shooting:
                        //카메라 기능을 Intent
//                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(i, 0);
                        dispatchTakePictureIntent();
                        break;

                }
            }

        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 카메라 촬영을 하면 이미지뷰에 사진 삽입
//         if(requestCode == 0 && resultCode == RESULT_OK) {
//             // Bundle로 데이터를 입력
//              Bundle extras = data.getExtras();
//              // Bitmap으로 컨버전
//              Bitmap imageBitmap = (Bitmap) extras.get("data");
//              // 이미지뷰에 Bitmap으로 이미지를 입력
//              iv_photo.setImageBitmap(imageBitmap);
//
//             Intent intent = new Intent(Camera.this, Sell_Form.class);
//             intent.putExtra("image",imageBitmap);
//         }
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if(requestCode==RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if(bitmap != null) {
                            iv_photo.setImageBitmap(bitmap);
                        }
                    }
                    break;
            }


        }
        catch (Exception error){
            error.printStackTrace();
        }
    }

    private  File createImageFile() throws IOException {
        String timeStampe = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStampe + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
    mCurrentPhotoPath = image.getAbsolutePath();
    return image;
    }

    private  void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try { photoFile = createImageFile(); }
            catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.shopproject.fileprovider",photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
            }
        }
    }

}