package com.example.pregbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.Adapter.TuanThaiAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuanThai extends AppCompatActivity implements TuanThaiAdapter.Listener {
    Button tieptheo;
    RecyclerView rcvTuanThai;
    List<String> sotuan;
    TuanThaiAdapter tuanThaiAdapter;
    EditText tenThaiNhi;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_thai);
        sotuan = new ArrayList<>();
        for(int i = 1; i<=35; i++){
            sotuan.add(String.valueOf(i));
        }

        tieptheo = findViewById(R.id.tieptheo);
        rcvTuanThai = findViewById(R.id.rcvTuanThai);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        tenThaiNhi = findViewById(R.id.tenThaiNhi);
        tuanThaiAdapter = new TuanThaiAdapter(sotuan, this);
        rcvTuanThai.setAdapter(tuanThaiAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(TuanThai.this,
                LinearLayoutManager.VERTICAL,false);
        rcvTuanThai.setLayoutManager(layoutManager);
        //gach ngang
        rcvTuanThai.addItemDecoration(new DividerItemDecoration(TuanThai.this,
                LinearLayoutManager.VERTICAL));


        tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuanThai.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnClickListener(String sotuan) {
        Toast.makeText(TuanThai.this, "click me", Toast.LENGTH_SHORT).show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference referenceProfile = firebaseDatabase.getReference(
                "Users");
        Map<String, Object> baby = new HashMap<>();
        baby.put("tenBaby", tenThaiNhi.getText().toString());
        baby.put("soTuan", sotuan);
        referenceProfile.child(firebaseUser.getUid()).child("Baby").setValue(baby);
  }
}