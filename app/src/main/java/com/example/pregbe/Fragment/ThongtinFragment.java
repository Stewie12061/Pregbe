package com.example.pregbe.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.Adapter.BaiVietCateViewHolder;
import com.example.pregbe.Adapter.FavoriteViewHolder;
import com.example.pregbe.BaiViet2;
import com.example.pregbe.ItemClickListener;
import com.example.pregbe.Model.Baiviet;
import com.example.pregbe.Model.Favorite;
import com.example.pregbe.R;
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


public class ThongtinFragment extends Fragment {
    private RecyclerView RVtuanthai, RVsearch;
    private RecyclerView.LayoutManager manager;
    private TextView lable;
    FirebaseRecyclerAdapter<Baiviet, BaiVietCateViewHolder> adapter;

    FirebaseRecyclerOptions<Favorite> options;
    FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder> adapterSearch;

    SearchView searchView;
    Favorite favorite;

    public ThongtinFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference baivietcateRef, searchRef,favoriteRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongtin, container, false);
        lable = getActivity().findViewById(R.id.label);

        firebaseDatabase = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
        baivietcateRef = firebaseDatabase.getReference("BaiVietCate");
        searchRef = firebaseDatabase.getReference("Search");
        favoriteRef = firebaseDatabase.getReference("Favorite");

        favorite = new Favorite();

        RVtuanthai = view.findViewById(R.id.RVtuanthai);
        manager = new GridLayoutManager(getContext(),2);
        RVtuanthai.setLayoutManager(manager);

        RVsearch = view.findViewById(R.id.rvSearch);
        RVsearch.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        RVsearch.setVisibility(View.GONE);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RVtuanthai.setVisibility(View.GONE);
                RVsearch.setVisibility(View.VISIBLE);
                loadDataSearch("");
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                RVtuanthai.setVisibility(View.VISIBLE);
                RVsearch.setVisibility(View.GONE);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBaiVietCate();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDataSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadDataSearch(newText);
                return false;
            }
        });
    }

    private void loadDataSearch(String searchText) {
        Query query;
        if(searchText==""){
            query = searchRef.orderByChild("name");
        }
        else {
            query = searchRef.orderByChild("name").startAt(searchText).endAt(searchText+"\uf8ff");
        }

        options = new FirebaseRecyclerOptions.Builder<Favorite>().setQuery(query,Favorite.class).build();
        adapterSearch = new FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position, @NonNull Favorite model) {
                String postKey = adapterSearch.getRef(position).getKey();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = user.getUid();


                searchRef.child(postKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String Name = snapshot.child("name").getValue().toString();
                        String Des = snapshot.child("des").getValue().toString();
                        String Img = snapshot.child("img").getValue().toString();

                        holder.tieude.setText(Name);
                        holder.chuthich.setText(Des);
                        Picasso.get().load(Img).into(holder.image);

                        holder.favoriteCheck(postKey);

                        holder.fav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (holder.isInMyFavorite){
                                    favoriteRef.child(currentUserId).child(postKey).removeValue()
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

                                                    Toast.makeText(getContext(), centeredText, Toast.LENGTH_LONG).show();
//                                                    Toast.makeText(PropertyItemsActivity.this, "Remove"+" "+Name+" "+"from favorite list", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }else {
                                    favorite.setName(Name);
                                    favorite.setDes(Des);
                                    favorite.setImg(Img);

                                    favoriteRef.child(currentUserId).child(postKey).setValue(favorite)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    String text = "Add"+" "+Name+" "+"to favorite list";
                                                    Spannable centeredText = new SpannableString(text);
                                                    centeredText.setSpan(
                                                            new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                                            0, text.length() - 1,
                                                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                                    );

                                                    Toast.makeText(getContext(), centeredText, Toast.LENGTH_SHORT).show();
//                                                    Toast.makeText(PropertyItemsActivity.this, "Add"+" "+Name+" "+"to favorite list", Toast.LENGTH_SHORT).show();
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
        RVsearch.setAdapter(adapterSearch);
        adapterSearch.startListening();
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