package com.example.pregbe.GioiThieu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pregbe.MainActivity;
import com.example.pregbe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChaoMung1 extends AppCompatActivity {
    Handler handler;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao_mung1);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUser != null){
                    Intent t=new Intent(ChaoMung1.this, MainActivity.class);
                    startActivity(t);
                    finish();
                }else{
                    Intent t=new Intent(ChaoMung1.this, ChaoMung2.class);
                    startActivity(t);
                    finish();
                }

            }
        },1000);

    }
}