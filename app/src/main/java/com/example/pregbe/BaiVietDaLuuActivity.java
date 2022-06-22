package com.example.pregbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pregbe.Adapter.FavoriteViewHolder;
import com.example.pregbe.Model.Favorite;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BaiVietDaLuuActivity extends AppCompatActivity {

    RecyclerView rvFavorite;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference favoriteRef;

    String currentUserId;

    FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet_da_luu);

        rvFavorite = findViewById(R.id.rvFavorite);
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(BaiVietDaLuuActivity.this, LinearLayoutManager.VERTICAL, false));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        favoriteRef = firebaseDatabase.getReference("Favorite");

        getDataFavorite();
    }

    private void getDataFavorite() {
        Query query = favoriteRef.child(currentUserId);

        FirebaseRecyclerOptions<Favorite> options = new FirebaseRecyclerOptions.Builder<Favorite>().setQuery(query,Favorite.class).build();

        adapter = new FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position, @NonNull Favorite model) {
                String id = getRef(position).getKey();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = user.getUid();

                favoriteRef.child(currentUserId).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String Name = snapshot.child("name").getValue().toString();
                        String Des = snapshot.child("des").getValue().toString();
                        String Img = snapshot.child("img").getValue().toString();

                        holder.tieude.setText(Name);
                        holder.chuthich.setText(Des);
                        Picasso.get().load(Img).into(holder.image);

                        holder.favoriteCheck(id);
                        holder.fav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (holder.isInMyFavorite){
                                    favoriteRef.child(currentUserId).child(id).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    String text = "Remove"+" "+Name+" "+"from favorite list";
                                                    Spannable centeredText = new SpannableString(text);
                                                    centeredText.setSpan(
                                                            new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                                            0, text.length() - 1,
                                                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                                    );

                                                    Toast.makeText(BaiVietDaLuuActivity.this, centeredText, Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(getContext(), "Removed from favorite list", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
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
            public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent,false);
                FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);
                return viewHolder;
            }
        };
        rvFavorite.setAdapter(adapter);
        adapter.startListening();
    }

}