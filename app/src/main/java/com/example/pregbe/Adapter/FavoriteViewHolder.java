package com.example.pregbe.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.ItemClickListener;
import com.example.pregbe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView image;
    public TextView tieude, chuthich, fav;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference favoriteRef;
    public boolean isInMyFavorite = false;
    private ItemClickListener itemClickListener;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgFav);

        tieude = itemView.findViewById(R.id.tieudeFav);

        chuthich = itemView.findViewById(R.id.chuthichFav);

        itemView.setOnClickListener(this);
    }

    public void favoriteCheck(String id) {
        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app");
        favoriteRef = firebaseDatabase.getReference("Favorite");

        fav = itemView.findViewById(R.id.favFav);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        favoriteRef.child(userId).child(id)
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
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void onClick(View v) {
        itemClickListener.onClick(v, getBindingAdapterPosition(),false);
    }

}
