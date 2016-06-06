package com.model.dao;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.model.entity.Business;
import com.model.entity.DisplayLists;
import com.model.entity.Review;
import com.model.entity.SearchCriteria;
import com.pages.Constants;
import com.util.ConnectionUtil;
import com.util.Util;

import javafx.util.converter.LocalTimeStringConverter;

public class BusinessDao {

	static Session session = ConnectionUtil.getSessionFactory().openSession();
	List<List<String>> displayList = new ArrayList<List<String>>();
	public List<List<String>> getsubcategoryNames(List<String> selectedCategories){
	      Transaction tx = null;
	      //DisplayLists displayList = new DisplayLists();
	      if(!displayList.isEmpty()){
	    	  displayList.clear();
	      }
	      try{
	         tx = session.beginTransaction();
	         /*String hql = "select subcategoryName "
		         		+ "FROM Subcategory sub, BusinessCategory cat, CategorySubcategory catSub"
		         		+ "where sub.subcategoryId = catSub.subcategoryId"
		         		+ "and cat.businessCategoryId = catSub.businessCategoryId"
		         		+ "and cat.businessCategoryName IN elements(selectedCategories)";*/
	         /*String hql = "select subcategoryName "
		         		+ "FROM Subcategory";*/
/*	         List<String> subcategories = session.createQuery(hql).list();*/
	         /*Query hql = session.createQuery("select subcategoryName "
	         		+ "FROM Subcategory , BusinessCategory , CategorySubcategory  "
	         		+ "where "
	         		+ "Subcategory.subcategoryId = CategorySubcategory.subcategoryId"
			         		+ "and BusinessCategory.businessCategoryId = CategorySubcategory.businessCategoryId"
	         		+ "and BusinessCategory.businessCategoryName in (:names)").setParameterList("names", selectedCategories);*/
	         /*Query query1 = session.createQuery("select businessCategoryId from BusinessCategory where businessCategoryName in (:names)").setParameterList("names", selectedCategories);
	         Query query2 = session.createQuery("select subcategoryId from CategorySubcategory where businessCategoryId in (:catIds)").setParameterList("catIds", query1.list());
	         Query query3 = session.createQuery("select subcategoryName from Subcategory where subcategoryId in (:subIds)").setParameterList("subIds", query2.list());
	         List<String> subcategories = query3.list();*/
	         List<Business> results = session.createQuery("from Business").list();
	         Util util = new Util();
	         JSONParser parser = new JSONParser();
	         List<String> subcategoryNameList = new ArrayList<>();
	         List<String> attributeNameList = new ArrayList<>();
	         for(Business business: results){
	        	 JSONArray categoryArray = (JSONArray) (parser.parse(business.getCategories()));
	        	 JSONObject attributes = (JSONObject) (parser.parse(business.getAttributes()));
	        	 boolean containsCategory = false;
	        	 for(String category: selectedCategories){
	        		 if(categoryArray.contains(category)){
	        			 containsCategory = true;
	        			 break;
	        		 }
	        	 }
	        	 if(containsCategory){
	        		 
	        		 for(Object category: categoryArray){
	        			 String cat = category.toString();
	        			 if(!util.getCategoryList().contains(cat)){
	        				 if(!subcategoryNameList.contains(cat)){
	        					 subcategoryNameList.add(cat);
	        				 }
	        			 }
	        		 }
	        		 for(String attribute: createAttributeList(attributes)){
	        			 if(!attributeNameList.contains(attribute)){
	        				 attributeNameList.add(attribute);
	        			 }
	        		 }
	        	 }
	         }
	         displayList.add(subcategoryNameList);
    		 displayList.add(attributeNameList);
/*	         
	         subcategoryNameList = new ArrayList<String>();
	         for(String subcategory : subcategories) {
	        	 subcategoryNameList.add(subcategory);
	         }*/
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	         //session.close(); 
	      }
	      return displayList;
	}

	private List<String> createAttributeList(JSONObject attributes) {
		List<String> attributeList = new ArrayList<>();
		Set<Map.Entry> attributeSet = attributes.entrySet();
		String attribute = null;
		for (Map.Entry entry : attributeSet) {
			if (entry.getValue() instanceof JSONObject) {
				String key = entry.getKey().toString();
				Set<Map.Entry> entrySet = ((JSONObject) entry.getValue()).entrySet();
				for (Map.Entry subEntry : entrySet) {
					attribute = key + "_" + subEntry.getKey().toString() + "_"
							+ subEntry.getValue().toString();
				}
			} else {
				attribute = entry.getKey().toString() + "_" + entry.getValue().toString();
			}
			attributeList.add(attribute);
		}
		
		return attributeList;
	}

	public List<Business> searchBusiness(SearchCriteria searchCriteria) {
	      Transaction tx = null;
	      List<Business> businessList = new ArrayList<>();
	      try{
	         tx = session.beginTransaction();
	         List<Business> results = session.createQuery("from Business").list();
	         Util util = new Util();
	         JSONParser parser = new JSONParser();
	         for(Business business: results){
	        	 JSONArray categoryArray = (JSONArray) (parser.parse(business.getCategories()));
	        	 JSONObject attributes = (JSONObject) (parser.parse(business.getAttributes()));
	        	 
	        	 boolean containsCategory = false;
	        	 for(String category: searchCriteria.getSelectedCategoryList()){
	        		 if(categoryArray.contains(category)){
	        			 containsCategory = true;
	        			 break;
	        		 }
	        	 }
	        	 boolean containsSubcategory = false;
	        	 if(searchCriteria.getSelectedSubcategoryList().isEmpty()){
	        		 containsSubcategory = true;
	        	 }
	        	 for(String category: searchCriteria.getSelectedSubcategoryList()){
	        		 if(categoryArray.contains(category)){
	        			 containsSubcategory = true;
	        			 break;
	        		 }
	        	 }
	        	 boolean containsAttributes = false;
	        	 if(containsCategory && containsSubcategory){
		        	 if(searchCriteria.getSelectedAttributeList().isEmpty()){
		        		 containsAttributes = true;
		        	 }
	        		 List<String> attributeNameList = createAttributeList(attributes);
	        		 if(searchCriteria.getSearchFor() == null){
	        			 searchCriteria.setSearchFor(Constants.ALL);
	        		 }
	        		 if((searchCriteria.getSearchFor().equals(Constants.ALL)) && attributeNameList.containsAll(searchCriteria.getSelectedAttributeList())){
	        			 containsAttributes = true;
	        		 }
	        		 for(String attribute: searchCriteria.getSelectedAttributeList()){
	        			 if((searchCriteria.getSearchFor().equals(Constants.ANY)) && attributeNameList.contains(attribute)){
	        				 containsAttributes = true;
	        				 break;
	        			 }
	        		 }
	        	 }
        		 boolean isValidDay = false;
        		 boolean isValidOpen = false;
        		 boolean isValidClose = false;
	        	 if(containsAttributes){
	        		 if(searchCriteria.getSelectedDay() == null){
	        			 isValidDay = true;
	        		 }
	        		 if(searchCriteria.getSelectedStartTime() == null){
	        			 isValidOpen = true;
	        		 }
	        		 if(searchCriteria.getSelectedEndTime() == null){
	        			 isValidClose = true;
	        		 }
	        		 JSONObject hours = (JSONObject) parser.parse(business.getHours());
	        		 if(hours != null && (hours.get(searchCriteria.getSelectedDay()) != null)){
	        			 isValidDay = true;
	        			 JSONObject day = (JSONObject)hours.get(searchCriteria.getSelectedDay());
	        			 String openTime = (String)day.get("open");
	        			 String closeTime = (String)day.get("close");
	        			 SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
	        			 if(((sdf.parse(openTime).before(sdf.parse(searchCriteria.getSelectedStartTime()))) || (openTime.equals(searchCriteria.getSelectedStartTime())))){
	        				 isValidOpen = true;
	        			 }
	        			 if(((sdf.parse(closeTime).after(sdf.parse(searchCriteria.getSelectedEndTime()))) || (closeTime.equals(searchCriteria.getSelectedEndTime())))){
	        				 isValidClose = true;
	        			 }
	        			 /*System.out.println(sdf.parse(startDate).before(sdf.parse(endDate)));
	        			 LocalTime openTim = new LocalTimeStringConverter("hh:mm");*/
	        		 }
	        	 }
	        	 if(isValidDay && isValidOpen && isValidClose){
	        		 businessList.add(business);
	        	 }
	         }
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	         //session.close(); 
	      }
	      return businessList;
	
	}

	public List<Review> searchReviews(SearchCriteria searchCriteria) {
		Transaction tx = null;
		List<Review> reviewList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			
			
			
			reviewList = session.createQuery("from Review where business_id = (:id)")
					.setParameter("id", searchCriteria.getBusiness().getBusinessId()).list();
			for(Review review: reviewList){
				Query query1 = session.createQuery("select name from User where user_id = (:id)").setParameter("id", review.getUser_id());
		        review.setUserName((String) query1.list().get(0)); 
			}
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return reviewList;
	}

	public List<String> getCategoryList() {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List categoryList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			categoryList = session.createQuery("businessCategoryName from BusinessCategory").list();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return categoryList;
	}

}
