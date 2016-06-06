package com.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sheen
 *
 */
public class SearchCriteria {
	private List<String> selectedCategoryList = new ArrayList<>();
	private List<String> selectedSubcategoryList = new ArrayList<>();
	private List<String> selectedAttributeList = new ArrayList<>();
	private String selectedDay;
	private String selectedStartTime;
	private String selectedEndTime;
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
	public List<String> getSelectedSubcategoryList() {
		return selectedSubcategoryList;
	}
	public void setSelectedSubcategoryList(List<String> selectedSubcategoryList) {
		this.selectedSubcategoryList = selectedSubcategoryList;
	}
	public List<String> getSelectedAttributeList() {
		return selectedAttributeList;
	}
	public void setSelectedAttributeList(List<String> selectedAttributeList) {
		this.selectedAttributeList = selectedAttributeList;
	}
	public String getSelectedDay() {
		return selectedDay;
	}
	public void setSelectedDay(String selectedDay) {
		this.selectedDay = selectedDay;
	}
	public String getSelectedStartTime() {
		return selectedStartTime;
	}
	public void setSelectedStartTime(String selectedStartTime) {
		this.selectedStartTime = selectedStartTime;
	}
	public String getSelectedEndTime() {
		return selectedEndTime;
	}
	public void setSelectedEndTime(String selectedEndTime) {
		this.selectedEndTime = selectedEndTime;
	}
	
}
