package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hours {
	int id;
	String day;
	String openTime;
	String closeTime;
	
	@Column(name="hour_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public Hours() {
	}
	
	public Hours(String day, String openTime, String closeTime) {
		this.day = day;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}
	
	public Hours(String openTime, String closeTime) {
		this.openTime = openTime;
		this.closeTime = closeTime;
	}
}
