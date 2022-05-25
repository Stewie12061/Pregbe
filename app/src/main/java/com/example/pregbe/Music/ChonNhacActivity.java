package com.example.pregbe.Music;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregbe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChonNhacActivity extends AppCompatActivity {
  ArrayList<com.example.pregbe.Music.Nhac> nhacArrayList;
  TextView label;
	com.example.pregbe.Music.NhacAdapter nhacAdapter;
  RecyclerView recyclerView;
  SharedPreferences sharedPreferences;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	nhacArrayList= new ArrayList<>();
	recyclerView=findViewById(R.id.recyclerView);
	label = findViewById(R.id.label);
	sharedPreferences = getSharedPreferences("MusicList", MODE_PRIVATE);
	LinearLayoutManager linear = new LinearLayoutManager(this);
	recyclerView.setLayoutManager(linear);
	nhacAdapter = new com.example.pregbe.Music.NhacAdapter(nhacArrayList, new com.example.pregbe.Music.NhacAdapter.Listener() {
	  @Override
	  public void Listener(com.example.pregbe.Music.Nhac nhac) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("URL",  String.valueOf(nhac.getUrl()));
		editor.putString("namesong", String.valueOf(nhac.song));
		editor.commit();
		Intent intent = new Intent(ChonNhacActivity.this, com.example.pregbe.Music.TestActivity.class);
		Toast.makeText(ChonNhacActivity.this, "Đã chọn bài hát " + String.valueOf(nhac.song), Toast.LENGTH_SHORT).show();
		startActivity(intent);
//		 Log.e("aa", String.valueOf(nhac.getUrl()));
	  }
	});
	recyclerView.setAdapter(nhacAdapter);
	if(sharedPreferences.getString("List", "").equals("Baby")){
	  ListNhac_Baby();
	}
	if(sharedPreferences.getString("List", "").equals("Mom")){
	  ListNhac_Mom();
	}
	if(sharedPreferences.getString("List", "").equals("Radio")){
	  ListNhac_Radio();
	}
	if(sharedPreferences.getString("List", "").equals("Relax")){
	  ListNhac_Relax();
	}
	  if(sharedPreferences.getString("List", "").equals("Truyen Thai Giao")){
		  ListNhac_Truyen();
	  }
  }





  void ListNhac_Baby(){
	FirebaseDatabase database = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
	DatabaseReference myRef = database.getReference("Music");
	label.setText("Nhạc cho bé");
	myRef.child("Baby").addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot snapshot) {
		for ( DataSnapshot ds : snapshot.getChildren() ) {
			com.example.pregbe.Music.Nhac listNhac = ds.getValue(com.example.pregbe.Music.Nhac.class);
		  nhacArrayList.add(listNhac);
			com.example.pregbe.Music.Utilities.url.add(listNhac);


//		  Log.e(TAG, Utilities.url.toString());
		}
		nhacAdapter.notifyDataSetChanged();
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError error) {

	  }
	});
  }
  void ListNhac_Mom(){
	FirebaseDatabase database = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
	DatabaseReference myRef = database.getReference("Music");
	label.setText("Nhạc cho mẹ");
	myRef.child("Mom").addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot snapshot) {
		for ( DataSnapshot ds : snapshot.getChildren() ) {
		  com.example.pregbe.Music.Nhac listNhac = ds.getValue(com.example.pregbe.Music.Nhac.class);
		  nhacArrayList.add(listNhac);
		  com.example.pregbe.Music.Utilities.url.add(listNhac);


//		  Log.e(TAG, Utilities.url.toString());
		}
		nhacAdapter.notifyDataSetChanged();
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError error) {

	  }
	});
  }
  void ListNhac_Radio(){
	  FirebaseDatabase database = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
	  DatabaseReference myRef = database.getReference("Music");
	label.setText("Radio cho mẹ");
	myRef.child("Radio").addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot snapshot) {
		for ( DataSnapshot ds : snapshot.getChildren() ) {
		  com.example.pregbe.Music.Nhac listNhac = ds.getValue(com.example.pregbe.Music.Nhac.class);
		  nhacArrayList.add(listNhac);
		  com.example.pregbe.Music.Utilities.url.add(listNhac);


//		  Log.e(TAG, Utilities.url.toString());
		}
		nhacAdapter.notifyDataSetChanged();
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError error) {

	  }
	});
  }
  void ListNhac_Relax(){
	  FirebaseDatabase database = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
	  DatabaseReference myRef = database.getReference("Music");
	label.setText("Nhạc thư giãn");
	myRef.child("Relax").addListenerForSingleValueEvent(new ValueEventListener() {
	  @Override
	  public void onDataChange(@NonNull DataSnapshot snapshot) {
		for ( DataSnapshot ds : snapshot.getChildren() ) {
		  com.example.pregbe.Music.Nhac listNhac = ds.getValue(com.example.pregbe.Music.Nhac.class);
		  nhacArrayList.add(listNhac);
		  com.example.pregbe.Music.Utilities.url.add(listNhac);
		}
		nhacAdapter.notifyDataSetChanged();
	  }

	  @Override
	  public void onCancelled(@NonNull DatabaseError error) {

	  }
	});
  }

	void ListNhac_Truyen(){
		FirebaseDatabase database = FirebaseDatabase.getInstance("https://pregbe-default-rtdb.asia-southeast1.firebasedatabase.app/");
		DatabaseReference myRef = database.getReference("Music");
		label.setText("Đọc truyện cho mẹ");
		myRef.child("Truyen Thai Giao").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for ( DataSnapshot ds : snapshot.getChildren() ) {
					com.example.pregbe.Music.Nhac listNhac = ds.getValue(com.example.pregbe.Music.Nhac.class);
					nhacArrayList.add(listNhac);
					com.example.pregbe.Music.Utilities.url.add(listNhac);
				}
				nhacAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
}