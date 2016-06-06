package com.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.model.entity.Location;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Util {
 public List<String> getCategories(){
	 List<String> categoryList = new ArrayList<>();
	 categoryList.add("Active Life");
	 categoryList.add("Arts & Entertainment");
	 categoryList.add("Automotive");
	 categoryList.add("Car Rental");
	 categoryList.add("Cafes");
	 categoryList.add("Beauty & Spas");
	 categoryList.add("Convenience Stores");
	 categoryList.add("Dentists");
	 categoryList.add("Doctors");
	 categoryList.add("Drugstores");
	 categoryList.add("Department Stores");
	 categoryList.add("Education");
	 categoryList.add("Event Planning & Services");
	 categoryList.add("Flowers & Gifts");
	 categoryList.add("Food");
	 categoryList.add("Health & Medical");
	 categoryList.add("Home Services");
	 categoryList.add("Home & Garden");
	 categoryList.add("Hospitals");
	 categoryList.add("Hotels & Travel");
	 categoryList.add("Hardware Stores");
	 categoryList.add("Grocery");
	 categoryList.add("Medical Centers");
	 categoryList.add("Nurseries & Gardening");
	 categoryList.add("Nightlife");
	 categoryList.add("Restaurants");
	 categoryList.add("Shopping");
	 categoryList.add("Transportation");
	 return categoryList;
 }

public List<Location> createLocationList() {
	// TODO Auto-generated method stub
	List<Location> locationList = new ArrayList<>();
	Location loc1 = new Location();
	loc1.setName("my cafe");
	loc1.setLatitude(43.107227000000002);
	loc1.setLongitude(-89.496994999999998);
	locationList.add(loc1);
	Location loc2 = new Location();
	loc2.setName("my bar");
	loc2.setLatitude(33.564941500000003);
	loc2.setLongitude(-112.25514219999999);
	locationList.add(loc2);
	Location loc3 = new Location();
	loc3.setName("my home");
	loc3.setLatitude(36.029445099999997);
	loc3.setLongitude(-115.0862422);
	locationList.add(loc3);
	Location loc4 = new Location();
	loc4.setName("my college");
	loc4.setLatitude(43.477273946091003);
	loc4.setLongitude(-80.525498650968103);
	locationList.add(loc4);
	Location loc5 = new Location();
	loc5.setName("my office");
	loc5.setLatitude(55.944169600000002);
	loc5.setLongitude(-3.2025293000000001);
	locationList.add(loc5);
	return locationList;
}
}
