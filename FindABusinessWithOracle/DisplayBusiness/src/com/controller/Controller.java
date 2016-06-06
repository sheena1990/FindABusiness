package com.controller;

import java.util.ArrayList;
import java.util.List;

import com.model.dao.AttributeDao;
import com.model.dao.BusinessCategoryDao;
import com.model.dao.BusinessDao;
import com.model.entity.Business;
import com.model.entity.CheckBoxItem;
import com.model.entity.Review;
import com.model.entity.SearchCriteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
	private BusinessCategoryDao businessCategoryDao = new BusinessCategoryDao();
	private ObservableList<CheckBoxItem> businessCategoryData = FXCollections.observableArrayList();
	
	private BusinessDao generalDao = new BusinessDao();
	//private ObservableList<CheckBoxItem> subcategoryData = FXCollections.observableArrayList();
	
	private AttributeDao attributeDao = new AttributeDao();
	//private ObservableList<CheckBoxItem> attributeData = FXCollections.observableArrayList();	

	public ObservableList<CheckBoxItem> getBusinessCategoryData() {
		if (!businessCategoryData.isEmpty()) {
			businessCategoryData.clear();
		}
		CheckBoxItem cb = null;
		for (String businessCategoryName : businessCategoryDao.getBusinessCategoryNames()) {
			cb = new CheckBoxItem(false, businessCategoryName);
			businessCategoryData.add(cb);
		}
		return businessCategoryData;
	}

/*	public ObservableList<CheckBoxItem> getSubcategoryData(List<String> selectedCategories) {
		if (!subcategoryData.isEmpty()) {
			subcategoryData.clear();
		}
		CheckBoxItem cb = null;
		if (!selectedCategories.isEmpty()) {
			for (String subcategoryName : subcategoryDao.getsubcategoryNames(selectedCategories)) {
				cb = new CheckBoxItem(false, subcategoryName);
				subcategoryData.add(cb);
			}
			
		}
		return subcategoryData;
	}*/
	
	public List<ObservableList<CheckBoxItem>> getDisplayLists(List<String> selectedCategories){
		List displayLists = new ArrayList<>();
		List<List<String>> displayList = generalDao.getsubcategoryNames(selectedCategories);
		//List<String> subcategoryData = displayList.get(0);
		ObservableList<CheckBoxItem> subcategoryData = FXCollections.observableArrayList();
		//List<String> attributeData = displayList.get(1);
		ObservableList<CheckBoxItem> attributeData = FXCollections.observableArrayList();
		
		CheckBoxItem cb = null;
		if (!selectedCategories.isEmpty()) {
			if(displayList.size() >0){
			for (String subcategoryName : displayList.get(0)) {
				cb = new CheckBoxItem(false, subcategoryName);
				subcategoryData.add(cb);
			}
			}
			if(displayList.size() >1)
			for (String businessCategoryName : displayList.get(1)) {
				cb = new CheckBoxItem(false, businessCategoryName);
				attributeData.add(cb);
			}
			displayLists.add(subcategoryData);
			displayLists.add(attributeData);
		}
		return displayLists;
	}

	/*public ObservableList<CheckBoxItem> getAttributeData() {
		if (!attributeData.isEmpty()) {
			attributeData.clear();
		}
		CheckBoxItem cb = null;
		for (String businessCategoryName : attributeDao.getAttributeNames()) {
			cb = new CheckBoxItem(false, businessCategoryName);
			attributeData.add(cb);
		}
		return attributeData;
	}*/

	public ObservableList<Business> getBusiness(SearchCriteria searchCriteria) {
		ObservableList<Business> businessData = FXCollections.observableArrayList();
		List<Business> businessList = generalDao.searchBusiness(searchCriteria);
		businessData.addAll(businessList);
		return businessData;
	}

	public ObservableList<Review> getReview(SearchCriteria searchCriteria) {
		ObservableList<Review> reviewData = FXCollections.observableArrayList();
		List<Review> reviewList = generalDao.searchReviews(searchCriteria);
		reviewData.addAll(reviewList);
		return reviewData;
	}

}