package com.model.entity;

import java.util.List;

public class Review {
	long votes_funny;
	long votes_useful;
	long votes_cool;
	String user_id;
	String review_id;
	double stars;
	String date;
	String text;
	String business_id;
	String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getVotes_funny() {
		return votes_funny;
	}
	public void setVotes_funny(long votes_funny) {
		this.votes_funny = votes_funny;
	}
	public long getVotes_useful() {
		return votes_useful;
	}
	public void setVotes_useful(long votes_useful) {
		this.votes_useful = votes_useful;
	}
	public long getVotes_cool() {
		return votes_cool;
	}
	public void setVotes_cool(long votes_cool) {
		this.votes_cool = votes_cool;
	}
	public double getStars() {
		return stars;
	}
	public void setStars(double stars) {
		this.stars = stars;
	}



	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getReview_id() {
		return review_id;
	}
	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	
	
}
