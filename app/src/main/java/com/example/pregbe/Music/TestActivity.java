package com.example.pregbe.Music;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pregbe.R;

import java.util.Random;

public class TestActivity extends AppCompatActivity {
  private Button  btnPre, btnNext, btnLap, btnDao;
  private ImageView PlayPause;
  private TextView textCurrentTime, songNametv, EndTime;
  private ImageView image_playing;
  private MediaPlayer mediaPlayer;
  private Animation animRotate;
  private SeekBar playerSeekbar;
  int count;
  String URLnhac;
  View view;
  private Handler handler= new Handler();
  static Boolean  daobai = false, lapbai = false;
  SharedPreferences sharedPreferences;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_test);

	sharedPreferences = getSharedPreferences("MusicList", MODE_PRIVATE);
	URLnhac = sharedPreferences.getString("URL", "");
	textCurrentTime= findViewById(R.id.textCurrentTime);
	EndTime= findViewById(R.id.EndTime);
	songNametv= findViewById(R.id.songNametv);
	image_playing = findViewById(R.id.image_playing);
	animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
	mediaPlayer = new MediaPlayer();
	for( int i = 0; i < Utilities.url.size() ; i++){
	  if(Utilities.url.get(i).getUrl().equals(URLnhac)){
		count = i;
		Log.e("count",String.valueOf(count));
		break;
	  }
	}
	songNametv.setText(sharedPreferences.getString("namesong", ""));


	//seekbar click
	playerSeekbar = findViewById(R.id.playerSeekbar);
	playerSeekbar.setMax(100);
	playerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
	  @Override
	  public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
		if (b){
		  SeekBar playerSeek = (SeekBar) view;
		  int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
		  mediaPlayer.seekTo(playPosition);
		  textCurrentTime.setText(miliSecondsToTime(mediaPlayer.getCurrentPosition()));
		}
	  }
	  @Override
	  public void onStartTrackingTouch(SeekBar seekBar) {
	  }

	  @Override
	  public void onStopTrackingTouch(SeekBar seekBar) {
	  }
	});


	//btnNext click
	btnNext= findViewById(R.id.btnNext);
	btnNext.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View view) {
		if(!lapbai  && !daobai ){
		  count++;
		  if(count > Utilities.url.size()-1) {
			count = Utilities.url.size()-1;
		  }
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		  mediaPlayer.reset();
		  A(URLnhac);
		  PlayPause.setImageResource(R.drawable.ic_pause_music);
		  updateSeekbar();
		}
		if(lapbai  && !daobai){
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		  mediaPlayer.reset();
		  A(URLnhac);
		  updateSeekbar();
		}
		if (daobai  && !lapbai){
		  Random dr = new Random();
		  count = dr.nextInt(Utilities.url.size() -1);
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  mediaPlayer.reset();
		  A(URLnhac);
		  updateSeekbar();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		}
		if(daobai && lapbai ){
		  count++;
		  if(count > Utilities.url.size()-1) {
			count = Utilities.url.size()-1;
		  }
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		  mediaPlayer.reset();
		  A(URLnhac);
		  updateSeekbar();
		}
	  }
	});

	//btnPre click
	btnPre= findViewById(R.id.btnPre);
	btnPre.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View view) {
		if(!lapbai  && !daobai ){
		  count--;
		  if(count <= 0) {
			count = 0;
		  }
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		  mediaPlayer.reset();
		  A(URLnhac);
		  PlayPause.setImageResource(R.drawable.ic_pause_music);
		  updateSeekbar();
		}
		if(lapbai  && !daobai){
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		  mediaPlayer.reset();
		  A(URLnhac);
		}
		if (daobai  && !lapbai){
		  Random dr = new Random();
		  count = dr.nextInt(Utilities.url.size() -1);
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  mediaPlayer.reset();
		  A(URLnhac);
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		}
		if(daobai && lapbai ){count--;
		  if(count <= 0) {
			count = 0;
		  }
		  URLnhac = Utilities.url.get(count).getUrl().toString();
		  songNametv.setText(Utilities.url.get(count).getSong().toString());
		  mediaPlayer.reset();
		  A(URLnhac);
		  }
		}
	});

	//btnLap click
	btnLap= findViewById(R.id.btnLap);
	btnLap.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View view) {
		if ( lapbai ){
		  btnLap.setBackgroundResource(R.drawable.ic_repeat_off);
		  lapbai = false;
		}else {
		  btnLap.setBackgroundResource(R.drawable.ic_repeat);
		  lapbai=true;
		}
	  }
	});

	//btnDao click
	btnDao= findViewById(R.id.btnDao);
	btnDao.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View view) {
		if ( daobai ){
		  btnDao.setBackgroundResource(R.drawable.ic_shuffle_off);
		  daobai = false;
		}else {
		  btnDao.setBackgroundResource(R.drawable.ic_shuffle);
		  daobai=true;
		}
	  }
	});

	//playpause click
	PlayPause= findViewById(R.id.PlayPause);
	PlayPause.setOnClickListener(new View.OnClickListener() {
	  @Override
	  public void onClick(View view) {
		if(mediaPlayer.isPlaying()){
		  handler.removeCallbacks(update);
		  mediaPlayer.pause();
		  PlayPause.setImageResource(R.drawable.ic_play);
		  image_playing.clearAnimation();
		}else {
		  mediaPlayer.start();
		  PlayPause.setImageResource(R.drawable.ic_pause_music);
		  updateSeekbar();
		  image_playing.startAnimation(animRotate);
		}

	  }
	});
	A(URLnhac);
	updateSeekbar();
  }
  private  Runnable update = new Runnable() {
	@Override
	public void run( ) {
	  updateSeekbar();
	  long currentDuration = mediaPlayer.getCurrentPosition();
	  textCurrentTime.setText(miliSecondsToTime(currentDuration));
	}
  };

  //cap nhat seekbar
  private  void updateSeekbar() {
	if (mediaPlayer.isPlaying()){
	  playerSeekbar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100 ));
	  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mediaPlayer) {
			count++;
			if(count > Utilities.url.size()-1) {
			  count = Utilities.url.size()-1;
			}
			URLnhac = Utilities.url.get(count).getUrl().toString();
			songNametv.setText(Utilities.url.get(count).getSong().toString());
			mediaPlayer.reset();
			A(URLnhac);
			updateSeekbar();
		}
	  });
	  handler.postDelayed(update, 100);
	}
  }


  //time
  private String miliSecondsToTime(long miliSeconds) {
	String timeString = "";
	String minutesString = null;
	String secondsString = null;
	int hours = (int) ( miliSeconds / ( 1000 * 60 * 60 ) );
	int minutes = (int) ( miliSeconds % ( 1000 * 60 * 60 ) ) / ( 1000 * 60 );
	int seconds = (int) ( ( miliSeconds % ( 1000 * 60 * 60 ) ) % ( 1000 * 60 ) / 1000 );

	if ( hours > 0 ) {
	  timeString = hours + ":";
	}
	if ( minutes < 10 ) {
	  minutesString = "0" + minutes;
	} else {
	  minutesString = "" + minutes;
	}
	if ( seconds < 10 ) {
	  secondsString = "0" + seconds;
	} else {
	  secondsString = "" + seconds;
	}
	timeString = timeString + minutesString + ":" + secondsString;
	return timeString;
  }
  //xu ly link nhac
  void A(String url){
	try {
	  mediaPlayer.setDataSource(url);
	  mediaPlayer.prepare();
	  mediaPlayer.start();
	  image_playing.startAnimation(animRotate);
	  EndTime.setText(miliSecondsToTime(mediaPlayer.getDuration()));
	}catch ( Exception exception ){
	  Toast.makeText(TestActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
	}

  }
}
