package com.example.pregbe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pregbe.BaiVietDaLuuActivity;
import com.example.pregbe.GioiThieu.DangNhap;
import com.example.pregbe.GioiThieu.ReadWriteUserDetails;
import com.example.pregbe.R;
import com.example.pregbe.ThongTinBeActivity;
import com.example.pregbe.ThongTinMeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CanhanFragment extends Fragment {
    Button sighOut;
    FirebaseAuth firebaseAuth;
    LinearLayout baiVietFav, thongTinBe, thongTinMe;
    TextView tenme;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceProfile;
    ReadWriteUserDetails readWriteUserDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_canhan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        referenceProfile = firebaseDatabase.getReference("Users");

        firebaseAuth = FirebaseAuth.getInstance();

        sighOut= view.findViewById(R.id.signOut);
        sighOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), DangNhap.class);
                startActivity(intent);
            }
        });

        baiVietFav = view.findViewById(R.id.saveBaiViet);
        baiVietFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BaiVietDaLuuActivity.class);
                startActivity(intent);
            }
        });

        readWriteUserDetails = new ReadWriteUserDetails();

        tenme = view.findViewById(R.id.username);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();
        referenceProfile.child(currentUserId).child("Parent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                String tenMe = readWriteUserDetails.fullName;
                tenme.setText(tenMe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        thongTinBe = view.findViewById(R.id.thongtinBe);
        thongTinBe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThongTinBeActivity.class);
                startActivity(intent);
            }
        });

        thongTinMe = view.findViewById(R.id.thongtinMe);
        thongTinMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThongTinMeActivity.class);
                startActivity(intent);
            }
        });
    }
}