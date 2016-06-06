package com.json;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.model.Business;
import com.model.Hours;
import com.model.Review;
import com.model.User;
import com.util.ConnectionUtil;

import org.json.simple.parser.JSONParser;
import com.model.Attribute;

/**
 * Populate Yelp database with given JSON data
 * @author Sheen
 *
 */
public class populate {
	static int i = 0;

	static Session session = ConnectionUtil.getSessionFactory().openSession();
	static Transaction tx = null;
	public static void main(String[] args) {
		BufferedReader in = null;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		try {
			String str;
			clearData();
			for (int i = 0; i < args.length; i++) {
				in = new BufferedReader(new FileReader(args[i]));
				tx = session.beginTransaction();
				int count = 0;
				while ((str = in.readLine()) != null) {
					jsonObject = (JSONObject) parser.parse(str.toString());
					String type = (String) jsonObject.get("type");
					if ("business".equals(type)) {
						parseBusiness(jsonObject);
					} else if ("review".equals(type)) {
						parseReview(jsonObject);
					} else if ("user".equals(type)) {
						parseUser(jsonObject);
					}
					//batch processing - clear memory
					if(++count % 100 == 0){
						session.flush();
						session.clear();
					}
				}
				tx.commit();
				System.out.println("End of parsing one object type");
			}

		} catch (IOException ioe) {
			System.out.println("Incorrect input files.");
			ioe.printStackTrace();
		} catch (ParseException pe) {
			System.out.println("Incorrect parsing.");
			pe.printStackTrace();
		}finally{
			try {
				if(in != null){
					in.close();
					session.close();
				}
				else{
					System.out.println("Please enter valid file names.");
				}
			} catch (IOException e) {
				System.out.println("Error while closing input stream.");
				e.printStackTrace();
			}
		}
	}
	
	private static void parseBusiness(JSONObject jsonObject) {
		Business business = new Business();
		business.setBusinessId((String) jsonObject.get("business_id"));
		business.setFullAddress((String) jsonObject.get("full_address"));
		//Parse Hours
		int hourIdForBuiseness = 1;
		Set<Map.Entry> hours = ((JSONObject) jsonObject.get("hours")).entrySet();
		Hours h1 = null;
		for(Map.Entry entry : hours) {
			h1 = new Hours();
			h1.setId(hourIdForBuiseness);
			
			h1.setDay(entry.getKey().toString());
			JSONObject openClose = (JSONObject) entry.getValue();
			h1.setCloseTime((String) openClose.get("close"));
			h1.setOpenTime((String) openClose.get("open"));
			//business.getHours().add(h1);
		}
		// business.setHours((String) jsonObject.get("hours"));
		// business.setCategories((String)
		// jsonObject.get("categories"));
		business.setCity((String) jsonObject.get("city"));
		business.setName((String) jsonObject.get("name"));
		business.setState((String) jsonObject.get("state"));
		
		business.setStars((Double) jsonObject.get("stars"));
		// process business attributes
		//System.out.println("before att");
		String attributes = jsonObject.get("attributes").toString();
		business.setAttributes(attributes);
		business.setCategories(jsonObject.get("categories").toString());
		business.setHours(jsonObject.get("hours").toString());
		//System.out.println("after cat");
		//saveBusiness(business);
		session.save(business); 
		//addBusiness(business);
		
		//System.out.println("End of parsing for one record");
		
	}

	private static void parseReview(JSONObject jsonObject) {
			Review review = new Review();
			JSONObject votes = (JSONObject) jsonObject.get("votes");
			review.setVotes_cool((long)votes.get("cool"));
			review.setVotes_funny((long)votes.get("funny"));
			review.setVotes_useful((long)votes.get("useful"));
			review.setUser_id((String) jsonObject.get("user_id"));
			review.setReview_id((String) jsonObject.get("review_id"));
			review.setStars((Long) jsonObject.get("stars"));
			review.setDate((String) jsonObject.get("date"));
			review.setText((String) jsonObject.get("text"));
			review.setBusiness_id((String) jsonObject.get("business_id"));
			//saveReview(review);
			session.save(review);
	}

	private static void parseUser(JSONObject jsonObject) {
		// TODO Auto-generated method stub
	
		User user = new User();
		JSONObject votes = (JSONObject) jsonObject.get("votes");
		user.setVotes_cool((long)votes.get("cool"));
		user.setVotes_funny((long)votes.get("funny"));
		user.setVotes_useful((long)votes.get("useful"));
		user.setYelping_since((String) jsonObject.get("yelping_since"));
		user.setReview_count((long) jsonObject.get("review_count"));
		user.setName((String) jsonObject.get("name"));
		user.setUser_id((String) jsonObject.get("user_id"));
		user.setFriends(jsonObject.get("friends").toString());
		//user.setFans((long) jsonObject.get("fans"));
		//user.setAverage_stars((double) jsonObject.get("average_stars"));
		//user.setCompliments((String) jsonObject.get("compliments").toString());
		//user.setElite((String) jsonObject.get("elite").toString());
		//saveUser(user);
		session.save(user);
	}

	/* Method to add a business in the database */
	   private static String saveBusiness(Business business){
		   
	      Transaction tx = null;
	      String businessID = null;
	      try{
	         tx = session.beginTransaction();
	         businessID = (String) session.save(business); 
	         tx.commit(); 
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         //session.close(); 
	      }
	      return businessID;
	   }

	private static void saveReview(Review review) {
		Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         session.save(review); 
	         tx.commit(); 
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         //session.close(); 
	      }
	}

	private static void saveUser(User user) {
		Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         session.save(user); 
	         tx.commit(); 
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         //session.close(); 
	      }
	}

	private static void clearData(){
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql2 = "delete from Review";
		         session.createQuery(hql2).executeUpdate();
		         String hql1 = "delete from Business";
		         session.createQuery(hql1).executeUpdate();
		         String hql3 = "delete from User";
		         session.createQuery(hql3).executeUpdate();
	
		         //session.delete(new Attribute());
		         tx.commit(); 
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         //session.close(); 
		      }
	   }
	
	private static void closeSession(){
		session.close();
	}

	private static int saveAttribute(String attribute) {
	      Transaction tx = null;
	      int attribute_bit = -1;
	      try{
	         tx = session.beginTransaction();
/*	         Attribute a = new Attribute();
	         a.setAttributeName(attribute);
	         attribute_bit = (int) session.save(a);*/
	         if(!isExistsInDb(attribute, "attributeName", Attribute.class)){
	        	 attribute_bit = (int) session.save(new Attribute(i++, attribute));
	        	 if(attribute_bit > 63)
	        	 System.out.println(attribute_bit);
	         }
	         tx.commit(); 
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         //session.close(); 
	      }
	      return attribute_bit;
	}

/*	private static int calculateAttributeWeight(String attribute){
		if(isExistsInDb(attribute)){
			
		}
		else{
			
		}
		return 0;
	}*/
	
	   private static boolean isExistsInDb(String value, String column, Class table) {
		// TODO Auto-generated method stub
		   Criteria cr = session.createCriteria(table);
		   cr.add(Restrictions.eq(column, value));
		   List results = cr.list();
		   /*List results = session.createQuery("select from " + table + " where " + column + " = (:name)").setParameter("name", value).list();*/
/*		   String hql = "FROM Attribute a WHERE a.attributeName = '" + value + "'";
		   Query query = session.createQuery(hql);
		   List results = query.list();*/
		   if(!results.isEmpty()){
			   return true;
		   }
		   else{
			   return false;
		   }
		   
		  /* String hql = "SELECT a.attribute_bit FROM Attributes a WHERE a.attribute_name = '" + attribute + "'\"";
		   Query query = session.createQuery(hql);
		   List results = query.list();*/
	   }
}
