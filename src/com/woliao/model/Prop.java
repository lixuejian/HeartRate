package com.woliao.model;

import java.util.Date;

public class Prop {
	private int id;
	
	private String username;
	
	private int sex;
	
	private int age;
	
	private int sportlevel;
	
	private int bloodpressure;
	
	private int bloodsugar;
	
	private int drinkwater;
	
	private double longitude;//经度

	private double latitude;//纬度
	
	private Date uploadtime;
	
	private Date bpuploadtime;
	
	private Date bsuploadtime;
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public int getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(int bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

	public int getBloodsugar() {
		return bloodsugar;
	}

	public void setBloodsugar(int bloodsugar) {
		this.bloodsugar = bloodsugar;
	}

	public int getDrinkwater() {
		return drinkwater;
	}

	public void setDrinkwater(int drinkwater) {
		this.drinkwater = drinkwater;
	}

	public Date getBpuploadtime() {
		return bpuploadtime;
	}

	public void setBpuploadtime(Date bpuploadtime) {
		this.bpuploadtime = bpuploadtime;
	}

	public Date getBsuploadtime() {
		return bsuploadtime;
	}

	public void setBsuploadtime(Date bsuploadtime) {
		this.bsuploadtime = bsuploadtime;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSportlevel() {
		return sportlevel;
	}

	public void setSportlevel(int sportlevel) {
		this.sportlevel = sportlevel;
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

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
	

	
}
