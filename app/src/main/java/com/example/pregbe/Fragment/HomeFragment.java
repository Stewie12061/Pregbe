package com.example.pregbe.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.Adapter.SoTuanViewHolder;
import com.example.pregbe.GioiThieu.ReadWriteUserDetails;
import com.example.pregbe.ItemClickListener;
import com.example.pregbe.Model.TenBe;
import com.example.pregbe.Model.Tuan;
import com.example.pregbe.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceProfile, thongTinRef;

    TextView tenme, TenBe, SoKy, ChieuDai, MoTa, tuanThai;
    FirebaseRecyclerAdapter<Tuan, SoTuanViewHolder> adapter;
    String id2;
    ImageView HinhAnh;

    RecyclerView rvTuanThai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        referenceProfile = firebaseDatabase.getReference("Users");
        thongTinRef = firebaseDatabase.getReference("ThongTin");

        tenme = view.findViewById(R.id.username);
        TenBe = view.findViewById(R.id.TenBe);

        rvTuanThai = view.findViewById(R.id.rvTuanThai);

        HinhAnh = view.findViewById(R.id.hinhAnhBe);
        ChieuDai = view.findViewById(R.id.chieuDaiBe);
        SoKy = view.findViewById(R.id.soKyBe);
        MoTa = view.findViewById(R.id.moTaBe);

        tuanThai = view.findViewById(R.id.tuanThai);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();

        referenceProfile.child(currentUserId).child("Parent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails =snapshot.getValue(ReadWriteUserDetails.class);
                String tenMe =readWriteUserDetails.fullName;
                tenme.setText(tenMe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceProfile.child(currentUserId).child("Baby").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails =snapshot.getValue(ReadWriteUserDetails.class);
                String tenBe =readWriteUserDetails.tenBe;
                int tuanthai = readWriteUserDetails.soTuan;
                String tuanthaiString = Integer.toString(tuanthai);
                tuanThai.setText(tuanthaiString);
                TenBe.setText(tenBe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        rvTuanThai.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getTuanThai();
        getThongTin();

        return view;
    }

    private void getThongTin() {

    }

    private void getTuanThai() {
        FirebaseRecyclerOptions<Tuan>options = new FirebaseRecyclerOptions.Builder<Tuan>().setQuery(thongTinRef,Tuan.class).build();

         adapter = new FirebaseRecyclerAdapter<Tuan, SoTuanViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SoTuanViewHolder holder, int position, @NonNull Tuan model) {
                String id =getRef(position).getKey();

                thongTinRef.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String soTuan = snapshot.getKey();

                        holder.soTuan.setText(soTuan);

                        holder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                id2 = adapter.getRef(position).getKey();

                                thongTinRef.child(id2).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String chieudai = snapshot.child("ChieuDai").getValue().toString();
                                        String hinhanh = snapshot.child("HinhAnh").getValue().toString();
                                        String mota = snapshot.child("MoTa").getValue().toString();
                                        String soky = snapshot.child("Soky").getValue().toString();

                                        ChieuDai.setText(chieudai);
                                        MoTa.setText(mota);
                                        SoKy.setText(soky);
                                        Picasso.get().load(hinhanh).into(HinhAnh);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public SoTuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false);
                SoTuanViewHolder viewHolder = new SoTuanViewHolder(v);
                return viewHolder;
            }
        };

         rvTuanThai.setAdapter(adapter);
        adapter.startListening();
    }
}