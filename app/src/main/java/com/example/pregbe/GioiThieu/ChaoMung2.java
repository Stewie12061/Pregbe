package com.example.pregbe.GioiThieu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pregbe.R;

public class ChaoMung2 extends AppCompatActivity {
Button DangNhap,batdau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao_mung2);

        DangNhap = findViewById(R.id.DangNhap);
        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChaoMung2.this, DangNhap.class);
                startActivity(i);
            }
        });
        batdau = findViewById(R.id.batdau);
        batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChaoMung2.this,DangKy.class);
                startActivity(intent);
            }
        });

    }
}