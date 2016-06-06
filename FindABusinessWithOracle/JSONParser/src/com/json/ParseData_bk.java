package com.json;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
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
import com.util.ConnectionUtil;

import org.json.simple.parser.JSONParser;

public class ParseData_bk {

	static Session session = ConnectionUtil.getSessionFactory().openSession();
	/*public static void main(String[] args) {
		BufferedReader in = null;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		try {
			String str;
			for (int i = 0; i < args.length; i++) {
				in = new BufferedReader(new FileReader(args[i]));
				while ((str = in.readLine()) != null) {
					jsonObject = (JSONObject) parser.parse(str);
					System.out.println(jsonObject);
					String type = (String) jsonObject.get("type");
					if ("business".equals(type)) {
						parseBusiness(jsonObject);
					} else if ("review".equals(type)) {

					} else if ("user".equals(type)) {

					}
				}
				
			}

		} catch (IOException ioe) {
			System.out.println("Incorrect input files.");
			ioe.printStackTrace();
		} catch (ParseException pe) {
			System.out.println("Incorrect parsing.");
			pe.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("Error while closing input stream.");
				e.printStackTrace();
			}
			session.close();
		}
	}*/
	
	private static void parseBusiness(JSONObject jsonObject) {
		Business business = new Business();
	/*	business.setBusiness_id((String) jsonObject.get("business_id"));
		business.setFull_address((String) jsonObject.get("full_address"));*/
		// business.setHours((String) jsonObject.get("hours"));
		// business.setCategories((String)
		// jsonObject.get("categories"));
		business.setCity((String) jsonObject.get("city"));
		business.setName((String) jsonObject.get("name"));
		business.setState((String) jsonObject.get("state"));
		
		//business.setStars((Double) jsonObject.get("stars"));
		// process business attributes
		JSONObject attributes = (JSONObject) jsonObject.get("attributes");
		int combinedAttributeWeight = 0;
		Set<Map.Entry> attributeSet = attributes.entrySet();
		String attribute = null;
		for (Map.Entry entry : attributeSet) {
			/*
			 * String key = entry.getKey().toString();
			 * while(entry.getValue() instanceof JSONObject){
			 * Set<Map.Entry> entrySet =
			 * ((JSONObject)entry.getValue()).entrySet();
			 * for(Map.Entry subEntry : entrySet){
			 * 
			 * } }
			 */
			if (entry.getValue() instanceof JSONObject) {
				String key = entry.getKey().toString();
				Set<Map.Entry> entrySet = ((JSONObject) entry.getValue()).entrySet();
				for (Map.Entry subEntry : entrySet) {
					/*business.getAttributes().add(key + "_" + subEntry.getKey().toString() + "_"
							+ subEntry.getValue().toString());*/
					attribute = key + "_" + subEntry.getKey().toString() + "_"
							+ subEntry.getValue().toString();
					int attributeWeight = 0;
					combinedAttributeWeight += attributeWeight;
					
				}
			} else {
				/*business.getAttributes()
						.add(entry.getKey().toString() + "_" + entry.getValue().toString());*/
				attribute = entry.getKey().toString() + "_" + entry.getValue().toString();
			}
		}
		/*
		 * String name = (String) jsonObject.get("name");
		 * System.out.println(name); System.out.println((String)
		 * jsonObject.get("city")); System.out.println(((Long)
		 * jsonObject.get("review_count")) );
		 */
		//addBusiness(business);
		
	}

	private static int calculateAttributeWeight(String attribute){
		if(isExistsInDb(attribute)){
			
		}
		else{
			
		}
		return 0;
	}
	
	   private static boolean isExistsInDb(String attribute) {
		// TODO Auto-generated method stub
		   /*Criteria cr = session.createCriteria(Attribute.class);
		   cr.add(Restrictions.eq("salary", 2000));
		   List results = cr.list();*/
		   
		   
		  /* String hql = "SELECT a.attribute_bit FROM Attributes a WHERE a.attribute_name = '" + attribute + "'\"";
		   Query query = session.createQuery(hql);
		   List results = query.list();*/
		return false;
	}

	/* Method to add a business in the database */
	   public static String saveBusiness(Business business){
		   
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
	         session.close(); 
	      }
	      return businessID;
	   }

}
