package com.example.pregbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DangKyThanhCongActivity2 extends AppCompatActivity {
    Button xacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_thanh_cong2);
        xacnhan = findViewById(R.id.xacnhan);
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKyThanhCongActivity2.this, TuanThai.class);
                startActivity(intent);
            }
        });
    }

}