package com.controller;

import java.util.ArrayList;

import java.util.List;

import com.model.dao.GenericDao;
import com.model.entity.Business;
import com.model.entity.CheckBoxItem;
import com.model.entity.Review;
import com.model.entity.SearchCriteria;
/*import com.model.entity.Business;

import com.model.entity.Review;
import com.model.entity.SearchCriteria;*/
import com.util.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
	private ObservableList<CheckBoxItem> businessCategoryData = FXCollections.observableArrayList();
	private GenericDao genericDao = new GenericDao();
	Util util = new Util();

	public ObservableList<CheckBoxItem> getBusinessCategoryData() {
		if (!businessCategoryData.isEmpty()) {
			businessCategoryData.clear();
		}
		CheckBoxItem cb = null;
		for (String businessCategoryName : util.getCategories()) {
			cb = new CheckBoxItem(false, businessCategoryName);
			businessCategoryData.add(cb);
		}
		return businessCategoryData;
	}
	
	public ObservableList<CheckBoxItem> getAttributes(List<String> selectedCategories){
		List<String> attributeList = genericDao.findAttributes(selectedCategories);
		ObservableList<CheckBoxItem> attributeData = FXCollections.observableArrayList();
		CheckBoxItem cb = null;
		if (!selectedCategories.isEmpty()) {
			if(attributeList.size() >0){
			for (String attribute : attributeList) {
				cb = new CheckBoxItem(false, attribute);
				attributeData.add(cb);
			}
			}
		}
		return attributeData;
	}

	public ObservableList<Business> getBusiness(SearchCriteria searchCriteria) {
		ObservableList<Business> businessData = FXCollections.observableArrayList();
		List<Business> businessList = genericDao.findBusinesses(searchCriteria);
		businessData.addAll(businessList);
		return businessData;
	}

	public ObservableList<Review> getReview(SearchCriteria searchCriteria) {
		ObservableList<Review> reviewData = FXCollections.observableArrayList();
		List<Review> reviewList = genericDao.findReviews(searchCriteria);
		reviewData.addAll(reviewList);
		return reviewData;
	}

/*	public ObservableList<Business> getBusiness(SearchCriteria searchCriteria) {
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
	}*/

}