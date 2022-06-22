package com.example.pregbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pregbe.Model.Favorite;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BaiVietDetailActivity extends AppCompatActivity {

    TextView goback;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference listDetailRef, favoriteRef,searchRef;

    public Boolean isInMyFavorite = false;
    TextView fav;

    ImageView imgListDetail;
    TextView nameListDetail, desListDetail;

    String idDetail, idTuan, idCate;
    String idFromFav;

    Favorite favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet_detail);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        listDetailRef = firebaseDatabase.getReference("ListDetail");
        favoriteRef = firebaseDatabase.getReference("Favorite");
        searchRef = firebaseDatabase.getReference("Search");

        favorite = new Favorite();

        goback = findViewById(R.id.backprevious);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        imgListDetail = findViewById(R.id.imgListDetail);
        nameListDetail = findViewById(R.id.nameListDetail);
        desListDetail = findViewById(R.id.desListDetail);

        idDetail = getIntent().getStringExtra("idDetail");
        idTuan = getIntent().getStringExtra("idTuan");
        idCate = getIntent().getStringExtra("idCate");
        idFromFav = getIntent().getStringExtra("idFromFav");
        if (idDetail != null){
            getListDetail(idDetail,idTuan,idCate);
        }
        else{
            getListDetailFromFav(idFromFav);
        }

    }

    private void getListDetailFromFav(String idFromFav) {
        searchRef.child(idFromFav).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =snapshot.child("name").getValue().toString();
                String des = snapshot.child("des").getValue().toString();
                String img = snapshot.child("img").getValue().toString();

                nameListDetail.setText(name);
                desListDetail.setText(des);
                Picasso.get().load(img).into(imgListDetail);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = user.getUid();
                favoriteCheck();

                favorite.setName(name);
                favorite.setDes(des);
                favorite.setImg(img);

                fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isInMyFavorite){
                            favoriteRef.child(currentUserId).child(idFromFav).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            String text = "Remove"+" "+name+" "+"from favorite list";
                                            Spannable centeredText = new SpannableString(text);
                                            centeredText.setSpan(
                                                    new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                                    0, text.length() - 1,
                                                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                            );

                                            Toast.makeText(BaiVietDetailActivity.this, centeredText, Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(ItemDetailActivity.this, "Removed from favorite list", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            favoriteRef.child(currentUserId).child(idFromFav).setValue(favorite)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            String text = "Add"+" "+name+" "+"to favorite list";
                                            Spannable centeredText = new SpannableString(text);
                                            centeredText.setSpan(
                                                    new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                                    0, text.length() - 1,
                                                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                            );

                                            Toast.makeText(BaiVietDetailActivity.this, centeredText, Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(ItemDetailActivity.this, "Added to favorite list", Toast.LENGTH_SHORT).show();
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

    private void getListDetail(String idDetail, String idTuan, String idCate){
        listDetailRef.child(idCate).child("week").child(idTuan).child(idDetail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =snapshot.child("name").getValue().toString();
                String des = snapshot.child("des").getValue().toString();
                String img = snapshot.child("img").getValue().toString();

                nameListDetail.setText(name);
                desListDetail.setText(des);
                Picasso.get().load(img).into(imgListDetail);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = user.getUid();
                favoriteCheck();

                favorite.setName(name);
                favorite.setDes(des);
                favorite.setImg(img);

                fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isInMyFavorite){
                            favoriteRef.child(currentUserId).child(idCate+idTuan+idDetail).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            String text = "Remove"+" "+name+" "+"from favorite list";
                                            Spannable centeredText = new SpannableString(text);
                                            centeredText.setSpan(
                                                    new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                                    0, text.length() - 1,
                                                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                            );

                                            Toast.makeText(BaiVietDetailActivity.this, centeredText, Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(ItemDetailActivity.this, "Removed from favorite list", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            favoriteRef.child(currentUserId).child(idCate+idTuan+idDetail).setValue(favorite)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            String text = "Add"+" "+name+" "+"to favorite list";
                                            Spannable centeredText = new SpannableString(text);
                                            centeredText.setSpan(
                                                    new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                                    0, text.length() - 1,
                                                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                            );

                                            Toast.makeText(BaiVietDetailActivity.this, centeredText, Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(ItemDetailActivity.this, "Added to favorite list", Toast.LENGTH_SHORT).show();
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

    public void favoriteCheck() {

        fav = findViewById(R.id.fav);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        favoriteRef.child(userId).child(idCate+idTuan+idDetail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isInMyFavorite = snapshot.exists();
                        if (isInMyFavorite){
                            fav.setBackgroundResource(R.drawable.save_true);
                        }else {
                            fav.setBackgroundResource(R.drawable.save_false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        favoriteRef.child(userId).child(idFromFav)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isInMyFavorite = snapshot.exists();
                        if (isInMyFavorite){
                            fav.setBackgroundResource(R.drawable.save_true);
                        }else {
                            fav.setBackgroundResource(R.drawable.save_false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}