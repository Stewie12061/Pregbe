package com.example.pregbe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.Adapter.BaiVietCateViewHolder;
import com.example.pregbe.BaiViet2;
import com.example.pregbe.ItemClickListener;
import com.example.pregbe.Model.Baiviet;
import com.example.pregbe.R;
import com.example.pregbe.TuanThai;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ThongtinFragment extends Fragment {
    private RecyclerView RVtuanthai;
    private RecyclerView.LayoutManager manager;
    private TextView lable;
    FirebaseRecyclerAdapter<Baiviet, BaiVietCateViewHolder> adapter;
    public ThongtinFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference baivietcateRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongtin, container, false);
        lable = getActivity().findViewById(R.id.label);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
        baivietcateRef = firebaseDatabase.getReference("BaiVietCate");

        RVtuanthai = view.findViewById(R.id.RVtuanthai);
        manager = new GridLayoutManager(getContext(),2);
        RVtuanthai.setLayoutManager(manager);

        getBaiVietCate();

        return view;
    }

    private void getBaiVietCate() {
        FirebaseRecyclerOptions<Baiviet> options = new FirebaseRecyclerOptions.Builder<Baiviet>().setQuery(baivietcateRef,Baiviet.class).build();

         adapter = new FirebaseRecyclerAdapter<Baiviet, BaiVietCateViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BaiVietCateViewHolder holder, int position, @NonNull Baiviet model) {
                String id =getRef(position).getKey();

                baivietcateRef.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        String des = snapshot.child("des").getValue().toString();
                        String img = snapshot.child("img").getValue().toString();

                        holder.name.setText(name);
                        holder.description.setText(des);
                        Picasso.get().load(img).into(holder.anhminhhoa);

                        holder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Intent intent = new Intent(getContext(), BaiViet2.class);
                                intent.putExtra("idCate",adapter.getRef(position).getKey());
                                intent.putExtra("name",name);
                                startActivity(intent);
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
            public BaiVietCateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongtin, parent, false);
                BaiVietCateViewHolder viewHolder = new BaiVietCateViewHolder(v);
                return viewHolder;
            }
        };
        RVtuanthai.setAdapter(adapter);
        adapter.startListening();
    }


}