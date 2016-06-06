package com.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.util.ConnectionUtil;

public class AttributeDao {

	static Session session = ConnectionUtil.getSessionFactory().openSession();
	
	public List<String> getAttributeNames(){
	      Transaction tx = null;
	      List<String> attributeNameList = null;
	      try{
	         tx = session.beginTransaction();
	         List<String> attributes = session.createQuery("select attributeName FROM Attribute").list();

	         attributeNameList = new ArrayList<String>();
	         for(String attribute : attributes) {
	        	 attributeNameList.add(attribute);
	         }
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         //session.close(); 
	      }
	      return attributeNameList;
	}
}
