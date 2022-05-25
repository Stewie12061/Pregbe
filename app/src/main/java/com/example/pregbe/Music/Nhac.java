package com.example.pregbe.Music;

public class Nhac {
  int id;
  String song;
  String time;
  String url;

  public Nhac(){}

  public Nhac(int id, String song, String time, String url) {
	this.id = id;
	this.song = song;
	this.time = time;
	this.url = url;
  }

  public int getId( ) {
	return id;
  }

  public void setId(int id) {
	this.id = id;
  }

  public String getSong( ) {
	return song;
  }

  public void setSong(String song) {
	this.song = song;
  }

  public String getTime( ) {
	return time;
  }

  public void setTime(String time) {
	this.time = time;
  }

  public String getUrl( ) {
	return url;
  }

  public void setUrl(String url) {
	this.url = url;
  }
}
