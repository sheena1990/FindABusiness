package com.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.model.entity.BusinessCategory;
import com.model.entity.CheckBoxItem;
import com.util.ConnectionUtil;

public class BusinessCategoryDao {
	static Session session = ConnectionUtil.getSessionFactory().openSession();
	
	public List<String> getBusinessCategoryNames(){
	      Transaction tx = null;
	      //List<String> businessCategoryNameList = null;
	      List<String> businessCategories = new ArrayList<>();
	      try{
	         tx = session.beginTransaction();
	         businessCategories = session.createQuery("select businessCategoryName FROM BusinessCategory").list();
	        /* Criteria cr = session.createCriteria(BusinessCategory.class)
	        		    .setProjection(Projections.projectionList()
	        		      .add(Projections.property("businessCategoryName"), "name"))
	        		    .setResultTransformer(Transformers.aliasToBean(String.class));

	         List<String> businessCategories = cr.list();*/
	         /*String abc = (String) cr.list().get(0);
	         System.out.println(abc);*/
/*	         if(businessCategories.isEmpty()){
	        	 System.out.println("i am empty");
	         }
	         else{
	        	 System.out.println("i am not");
	         }*/
/*	         businessCategoryNameList = new ArrayList<String>();
	         for(String businessCategory : businessCategories) {
	        	 businessCategoryNameList.add(businessCategory);
	         }*/
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         //session.close(); 
	      }
	      return businessCategories;
	}
}