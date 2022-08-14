package com.example.shopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Log_before extends AppCompatActivity {
    private TextView already;
    private TextView log_button;
    private Button log_before_button;
    private ImageView aaa;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();


    private FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_before);
        already = (TextView) findViewById(R.id.already);
        log_button = (TextView) findViewById(R.id.log_button);
        log_before_button = (Button) findViewById(R.id.log_before_button);
        aaa = (ImageView) findViewById(R.id.imageView);

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_before.this, Log_phone.class);
                startActivity(intent);
            }
        });
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_before.this, Log_phone.class);
                startActivity(intent);
            }
        });

        log_before_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_before.this, Signup.class);
                startActivity(intent);
            }
        });

//        aaa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                databaseReference.child("shopproject").child("UserAccount").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        UserAccount user = snapshot.getValue(UserAccount.class);
//
//                        String phone = user.getPhone();
//                        Toast.makeText(Log_before.this,phone,Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });

    }
}