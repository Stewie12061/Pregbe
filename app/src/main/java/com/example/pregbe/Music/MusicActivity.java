//package com.example.pregbe.Music;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ViewFlipper;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.pregbe.R;
//
//public class MusicActivity extends AppCompatActivity {
//  Button next, previous, btn_baby,btn_relax,btn_meditation,btn_sleep;
//  SharedPreferences sharedPreferences;
//  private ViewFlipper view_flipper;
//  View view;
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//	super.onCreate(savedInstanceState);
//	setContentView(R.layout.activity_music);
//	view_flipper = findViewById(R.id.view_flipper);
//	sharedPreferences = getSharedPreferences("MusicList", MODE_PRIVATE);
//	btn_focus = findViewById(R.id.btn_focus);
//	btn_focus.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View view) {
//		SharedPreferences.Editor editor = sharedPreferences.edit();
//		editor.putString("List", "focus");
//		editor.commit();
////		Intent i = new Intent(MusicActivity.this, ChonNhacActivity.class);
//		startActivity(i);
//	  }
//	});
//
//	btn_sleep = findViewById(R.id.btn_sleep);
//	btn_sleep.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View view) {
//		SharedPreferences.Editor editor = sharedPreferences.edit();
//		editor.putString("List", "sleep");
//		editor.commit();
//		Intent intent = new Intent(MusicActivity.this, ChonNhacActivity.class);
//		startActivity(intent);
//	  }
//	});
//
//	btn_meditation = findViewById(R.id.btn_meditation);
//	btn_meditation.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View view) {
//		SharedPreferences.Editor editor = sharedPreferences.edit();
//		editor.putString("List", "meditation");
//		editor.commit();
//		Intent t = new Intent(MusicActivity.this, ChonNhacActivity.class);
//		startActivity(t);
//	  }
//	});
//
//	btn_relax = findViewById(R.id.btn_relax);
//	btn_relax.setOnClickListener(new View.OnClickListener() {
//	  @Override
//	  public void onClick(View view) {
//		SharedPreferences.Editor editor = sharedPreferences.edit();
//		editor.putString("List", "relax");
//		editor.commit();
//		Intent intent = new Intent(MusicActivity.this, ChonNhacActivity.class);
//		startActivity(intent);
//	  }
//	});
//
//  }
//  public  void previousView(View v){
//	view_flipper.showPrevious();
//
//  }
//  public  void nextView(View v){
//	view_flipper.showNext();
//  }
//
//}
