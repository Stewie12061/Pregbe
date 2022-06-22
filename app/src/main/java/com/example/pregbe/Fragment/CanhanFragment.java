package com.example.pregbe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pregbe.BaiVietDaLuuActivity;
import com.example.pregbe.GioiThieu.DangNhap;
import com.example.pregbe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CanhanFragment extends Fragment {
    Button sighOut;
    FirebaseAuth firebaseAuth;
    LinearLayout baiVietFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_canhan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }
}