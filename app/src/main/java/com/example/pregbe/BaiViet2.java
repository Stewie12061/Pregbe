package com.example.pregbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.Adapter.DanhSachViewHolder;
import com.example.pregbe.Adapter.SoTuanViewHolder;
import com.example.pregbe.Model.Danhsach;
import com.example.pregbe.Model.Tuan;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BaiViet2 extends AppCompatActivity {
    public TextView tuade;

    RecyclerView RVtuan, RVdanhsach;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference baiVietDetailRef;
    FirebaseRecyclerAdapter<Tuan, SoTuanViewHolder> adapterTuan;
    FirebaseRecyclerAdapter<Danhsach, DanhSachViewHolder> adapterDanhSach;
    String id;
    String id3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet2);
        //nhan dlieu tu Fragment
        tuade = findViewById(R.id.tuade);
        Intent intent = getIntent();
        String s = intent.getStringExtra("name");
        tuade.setText(s);

        //recycler view Tuan
        RVtuan = findViewById(R.id.RVTuan);
        RVdanhsach = findViewById(R.id.rvDanhSach);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
        baiVietDetailRef = firebaseDatabase.getReference("BaiVietDetail");

        RVtuan.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));

        RVdanhsach.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));

        id = getIntent().getStringExtra("id");
        id3 = id;

        getTuan();
        getDanhSach();

    }

    private void getTuan() {
        Query query = baiVietDetailRef;
        FirebaseRecyclerOptions<Tuan> options = new FirebaseRecyclerOptions.Builder<Tuan>().setQuery(query,Tuan.class).build();

        adapterTuan = new FirebaseRecyclerAdapter<Tuan, SoTuanViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SoTuanViewHolder holder, int position, @NonNull Tuan model) {
                String id2 =getRef(position).getKey();
                baiVietDetailRef.child(id).child("week").child(id2).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String soTuan = snapshot.getKey();

                        holder.soTuan.setText(soTuan);
                        holder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                 id3 = adapterTuan.getRef(position).getKey();
                                Toast.makeText(getApplicationContext(),id3,Toast.LENGTH_SHORT).show();
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
        RVtuan.setAdapter(adapterTuan);
        adapterTuan.startListening();
    }

    private void getDanhSach() {
        Query query = baiVietDetailRef.child(id).child("list");
        FirebaseRecyclerOptions<Danhsach> options = new FirebaseRecyclerOptions.Builder<Danhsach>().setQuery(query,Danhsach.class).build();

        adapterDanhSach = new FirebaseRecyclerAdapter<Danhsach, DanhSachViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DanhSachViewHolder holder, int position, @NonNull Danhsach model) {
                String idList =getRef(position).getKey();
                baiVietDetailRef.child(id).child("list").child(idList).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String des = snapshot.child("des").getValue().toString();
                        String img = snapshot.child("img").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();

                        holder.chuthich.setText(des);
                        holder.tieude.setText(name);
                        Picasso.get().load(img).into(holder.img);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public DanhSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.danhsach_item,parent,false);
                DanhSachViewHolder viewHolder = new DanhSachViewHolder(v);
                return viewHolder;
            }
        };
        RVdanhsach.setAdapter(adapterDanhSach);
        adapterDanhSach.startListening();
    }
}
