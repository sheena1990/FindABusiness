package com.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class User {
	private String user_id;
	private String yelping_since;
	private long votes_funny;
	private long votes_cool;
	private long votes_useful;
	private long review_count;
	private String name;
	private String friends;
	private long fans;
	private double average_stars;
	private String compliments;
	private String elite;

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getYelping_since() {
		return yelping_since;
	}
	public void setYelping_since(String yelping_since) {
		this.yelping_since = yelping_since;
	}

	public long getVotes_funny() {
		return votes_funny;
	}
	public void setVotes_funny(long votes_funny) {
		this.votes_funny = votes_funny;
	}
	public long getVotes_cool() {
		return votes_cool;
	}
	public void setVotes_cool(long votes_cool) {
		this.votes_cool = votes_cool;
	}
	public long getVotes_useful() {
		return votes_useful;
	}
	public void setVotes_useful(long votes_useful) {
		this.votes_useful = votes_useful;
	}
	public long getReview_count() {
		return review_count;
	}
	public void setReview_count(long review_count) {
		this.review_count = review_count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}
	public String getCompliments() {
		return compliments;
	}
	public void setCompliments(String compliments) {
		this.compliments = compliments;
	}
	public String getElite() {
		return elite;
	}
	public void setElite(String elite) {
		this.elite = elite;
	}
	public long getFans() {
		return fans;
	}
	public void setFans(long fans) {
		this.fans = fans;
	}
	public double getAverage_stars() {
		return average_stars;
	}
	public void setAverage_stars(double average_stars) {
		this.average_stars = average_stars;
	}
}
