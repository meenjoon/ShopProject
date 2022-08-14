package com.example.shopproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Camera_example extends AppCompatActivity {
    final private static String TAG = "MBJ";
    private  static  final  int REQUEST_IMAGE_CAPTURE = 672;
    private  String ImageFilePath;
    private Uri photoUri;

    private Button button_shooting, button_imagefind, button_back;

    private ImageView iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_example);



        //권한 체크
//        TedPermission.with(getApplicationContext())
//                .setPermissionListener(permissionListner)
//                .setRationaleMessage("카메라 권한이 필요합니다.")
//                .setDeniedMessage("거부하셨습니다.")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .check();

        //권한 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(Camera_example.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }





        button_shooting = findViewById(R.id.button_shooting);
        button_imagefind = findViewById(R.id.button_imagefind);
        button_back = findViewById(R.id.button_back);
        iv_photo = findViewById(R.id.iv_photo);









        button_shooting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 기본 카메라 앱을 띄우는 구문
                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }  catch (IOException e) {

                    }

                    if(photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(),photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); //인텐트로 화면 전환이 일어났을때 다음 액티비티로부터 값을 다시 가져와준다.
                    }

                }


            }
        });


    }

    private File createImageFile() throws  IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,".jpg",storageDir
        );
        ImageFilePath = image.getAbsolutePath();
        return  image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // tartActivityForResult 랑 한쌍

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && requestCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(ImageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(ImageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else {
                exifDegree = 0;
            }
            ((ImageView) findViewById(R.id.iv_photo)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }

    private int exifOrientationToDegress(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return  90;
        } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return  180;
        } else  if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return  0;
    }

    private  Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


//    PermissionListener permissionListner = new PermissionListener() {
//        @Override
//        public void onPermissionGranted() { //퍼미션 허용했을때의 액션
//            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT ).show();
//        }
//
//        @Override
//        public void onPermissionDenied(List<String> deniedPermissions) { //퍼미션 거절했을때의 액션
//            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT ).show();
//        }
//    };




}