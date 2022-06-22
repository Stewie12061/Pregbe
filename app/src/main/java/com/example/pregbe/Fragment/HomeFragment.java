package com.example.pregbe.Fragment;

import android.content.Intent;
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

import com.example.pregbe.Adapter.DatLichHomeViewHolder;
import com.example.pregbe.Adapter.DatLichViewHolder;
import com.example.pregbe.Adapter.SoTuanViewHolder;
import com.example.pregbe.GioiThieu.ReadWriteUserDetails;
import com.example.pregbe.ItemClickListener;
import com.example.pregbe.Model.DatLich;
import com.example.pregbe.Model.Tuan;
import com.example.pregbe.R;
import com.example.pregbe.Update_lich_Activity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceProfile, thongTinRef, datLichRef;

    TextView tenme, TenBe, SoKy, ChieuDai, MoTa, tuanThai;
    FirebaseRecyclerAdapter<Tuan, SoTuanViewHolder> adapter;
    String id2;
    ImageView HinhAnh;

    RecyclerView rvTuanThai, rvDatLich;

    ReadWriteUserDetails readWriteUserDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        referenceProfile = firebaseDatabase.getReference("Users");
        thongTinRef = firebaseDatabase.getReference("ThongTin");
        datLichRef = firebaseDatabase.getReference("Dat Lich");

        tenme = view.findViewById(R.id.username);
        TenBe = view.findViewById(R.id.TenBe);

        rvTuanThai = view.findViewById(R.id.rvTuanThai);
        rvDatLich = view.findViewById(R.id.rvDatLichHome);

        HinhAnh = view.findViewById(R.id.hinhAnhBe);
        ChieuDai = view.findViewById(R.id.chieuDaiBe);
        SoKy = view.findViewById(R.id.soKyBe);
        MoTa = view.findViewById(R.id.moTaBe);

        readWriteUserDetails = new ReadWriteUserDetails();

        tuanThai = view.findViewById(R.id.tuanThai);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();

        referenceProfile.child(currentUserId).child("Parent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readWriteUserDetails =snapshot.getValue(ReadWriteUserDetails.class);
                String tenMe = readWriteUserDetails.fullName;
                tenme.setText(tenMe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceProfile.child(currentUserId).child("Baby").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                readWriteUserDetails =snapshot.getValue(ReadWriteUserDetails.class);
//                String tenBe =readWriteUserDetails.tenBe;
//                String sotuan = readWriteUserDetails.soTuan;
//                tuanThai.setText(sotuan);
//                TenBe.setText(tenBe);
                tuanThai.setText(snapshot.child("soTuan").getValue().toString());
                TenBe.setText(snapshot.child("tenBaby").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        rvTuanThai.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rvDatLich.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getTuanThai();
        getDatLich();
    }

    private void getDatLich() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = user.getUid();

        Query query = datLichRef.child(currentUserId);
        FirebaseRecyclerOptions<DatLich> options = new FirebaseRecyclerOptions.Builder<DatLich>().setQuery(query,DatLich.class).build();
        FirebaseRecyclerAdapter<DatLich, DatLichHomeViewHolder> adapter = new FirebaseRecyclerAdapter<DatLich, DatLichHomeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DatLichHomeViewHolder holder, int position, @NonNull DatLich model) {
                String idDatLich = getRef(position).getKey();

                datLichRef.child(currentUserId).child(idDatLich).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tieude = snapshot.child("tieuDe").getValue().toString();
                        String ngaydat = snapshot.child("ngayDat").getValue().toString();
                        String mota = snapshot.child("moTa").getValue().toString();
                        String tgDat = snapshot.child("tgDat").getValue().toString();

                        holder.tieuDe.setText(tieude);
                        holder.ngayDat.setText(ngaydat);
                        holder.moTa.setText(mota);
                        holder.thoiGian.setText(tgDat);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public DatLichHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dat_lich_home,parent,false);
                DatLichHomeViewHolder viewHolder = new DatLichHomeViewHolder(v);
                return viewHolder;
            }
        };
        rvDatLich.setAdapter(adapter);
        adapter.startListening();
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