package com.woliao.model;

import java.util.Date;

public class Recordheartrate {
	private int id;
	
	private String username;
	
	private String heartrecord;
	
	private Date recordtime;
	
	private String start;
	
	private String end;
	
	private int avarage;
	
	private  int max;
	
	private  int min;
	
	private String duration;
	
	private String date;
	

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getAvarage() {
		return avarage;
	}

	public void setAvarage(int avarage) {
		this.avarage = avarage;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHeartrecord() {
		return heartrecord;
	}

	public void setHeartrecord(String heartrecord) {
		this.heartrecord = heartrecord;
	}

	public Date getRecordtime() {
		return recordtime;
	}

	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}

	@Override
	public String toString() {
		return "id=" + id + " username=" + username
				+ " heartrecord=" + heartrecord + " recordtime=" + recordtime
				+ " start=" + start + " end=" + end + " avarage=" + avarage
				+ " max=" + max + " min=" + min + " duration=" + duration
				+ " date=" + date ;
	}

//	@Override
//	public String toString() {
//		return "Recordheartrate [id=" + id + ", username=" + username
//				+ ", heartrecord=" + heartrecord + ", recordtime=" + recordtime
//				+ "]";
//	}
	
	
	
}
