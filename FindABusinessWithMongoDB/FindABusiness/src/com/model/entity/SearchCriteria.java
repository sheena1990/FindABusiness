package com.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sheen
 *
 */
public class SearchCriteria {
	private List<String> selectedCategoryList = new ArrayList<>();
	private List<String> selectedAttributeList = new ArrayList<>();
	private Location location;
	private int maxDistance;
	private String searchFor;
	private Business business;
	
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public String getSearchFor() {
		return searchFor;
	}
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	public List<String> getSelectedCategoryList() {
		return selectedCategoryList;
	}
	public void setSelectedCategoryList(List<String> selectedCategoryList) {
		this.selectedCategoryList = selectedCategoryList;
	}

	public List<String> getSelectedAttributeList() {
		return selectedAttributeList;
	}
	public void setSelectedAttributeList(List<String> selectedAttributeList) {
		this.selectedAttributeList = selectedAttributeList;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}
	
}
