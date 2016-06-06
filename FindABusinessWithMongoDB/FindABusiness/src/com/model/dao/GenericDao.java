package com.model.dao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bson.BSON;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.model.entity.Business;
import com.model.entity.Review;
import com.model.entity.SearchCriteria;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.util.Constants;
import com.util.DBUtil;
import com.mongodb.DBCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import static com.mongodb.client.model.Projections.*;
import java.util.Collections;

public class GenericDao {
	DBUtil util = new DBUtil();
	MongoDatabase db = util.getDatabase();
	
	public List findAttributes(List<String> categories) {
		final List<String> attributeList = new ArrayList<>();
//		FindIterable<Document> iterable = db.getCollection(Constants.COLLECTION_BUSINESS).find();
		//FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS).find(Filters.eq("businesses.categories", "y"));
		//FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS).find(Filters.all("categories", Arrays.asList("Bakeries", "Food")));
		FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS)
				.find(Filters.all("categories", categories));
		iterable1.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Document attributes = (Document) document.get("attributes");
		    	String attribute = "";
		    	for(Entry entry: attributes.entrySet()){
		    		if(entry.getValue() instanceof Document){
		    			String key = entry.getKey().toString();
		    			Set<Entry<String,Object>> entrySet = ((Document) entry.getValue()).entrySet();
		    			for(Entry subEntry: entrySet){
		    				attribute = key + "-" + subEntry.getKey().toString() + "-"
	    							+ subEntry.getValue().toString();
		    			}
		    		}
		    		else{
		    			attribute = entry.getKey().toString() + "-" + entry.getValue().toString();
		    		}
		    		if(!attributeList.contains(attribute)){
		    			attributeList.add(attribute);
		    		}
		    	}
		        //System.out.println(document);
		    }
		});
		Collections.sort(attributeList);
		return attributeList;
	}
	
	public List<Business> findBusinesses(SearchCriteria searchCriteria) {
		final List<Business> businessList = new ArrayList<>();
		final List<Business> businessList2 = new ArrayList<>();
		/*FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS)
				.find(Filters.and(Filters.all("categories", searchCriteria.getSelectedCategoryList()),
						Filters.all("attributes", searchCriteria.getSelectedAttributeList())));*/
		/*and(or(eq("price", 0.99), eq("price", 1.99)
			    or(eq("sale", true), lt("qty", 20)))*/
		FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS)
				.find(Filters.all("categories", searchCriteria.getSelectedCategoryList()));
		iterable1.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Business business = new Business();
		    	business.setCity((String) document.get("city"));
		    	business.setState((String) document.get("state"));
		    	business.setStars(document.getDouble("stars"));
		    	business.setName((String) document.get("name"));
		    	business.setBusinessId(((String) document.get("business_id")));
		    	businessList.add(business);
		        //System.out.println(document);
		    }
		});
		//problem : take-out_True in value to be found
		/*FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS)
				.find(Filters.all("attributes.Take-out", true));*/
		//FindIterable<Document> iterableAnd = null;
		FindIterable<Document> iterable = null;
		Document attributeDocument = new Document();
		//attributeDocument.append(attributeKey, attributeValue);
		//DBObject clause1 = new BasicDBObject("post_title", regex);  
		    
		BasicDBList or = new BasicDBList();
		//or.add(clause1);
		
		DBObject clause;
		for(String attribute: searchCriteria.getSelectedAttributeList()){
			String attributeKey = "attributes." + attribute.substring(0, attribute.lastIndexOf('-')).replace('-', '.');
			Object attributeValue = attribute.substring(attribute.lastIndexOf('-') + 1);
			if(attributeValue.equals("true") || attributeValue.equals("false")){
				attributeValue = Boolean.valueOf(attributeValue.toString());
			}
			//to be tested for both boolean and string
			//use eq for attrbiutes
			/*clause = new BasicDBObject(attributeKey, attributeValue);
			or.add(clause);*/
			//attributeDocument.append("attributes", "Take-out.true");
			//attributeDocument.append("attributes.Take-out", true);
			attributeDocument.append(attributeKey, attributeValue);
			/*iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS)
					.find(Filters.all(attributeKey, attributeValue));*/
		}
		
		//DBObject query = new BasicDBObject("$or", or);
		//DBCursor cur = db.getCollection(Constants.COLLECTION_BUSINESS).find(query);
		//DBCollection collection = (DBCollection) db.getCollection(Constants.COLLECTION_BUSINESS);
		//DBCursor cur = collection.find(query);
		if(searchCriteria.getSearchFor().equalsIgnoreCase(Constants.ALL)){
			iterable = (db.getCollection(Constants.COLLECTION_BUSINESS).find(attributeDocument));
		}
		else{
			iterable = db.getCollection(Constants.COLLECTION_BUSINESS).find(Filters.or(attributeDocument));
		}
		
		//***********NEAR********************************************************************************************************
		QueryBuilder query = new QueryBuilder();
		query = query.near(searchCriteria.getLocation().getLatitude(), searchCriteria.getLocation().getLongitude(),
				searchCriteria.getMaxDistance());
		//db.getCollection("businesses").createIndex(new Document("latitude", 1).append("longitude", 1));
		
		//iterable = db.getCollection(Constants.COLLECTION_BUSINESS).find((Bson) query);
		db.getCollection("businesses").createIndex(new Document("loc", "2dsphere"));
		//db.getCollection("restaurants").createIndex(new BasicDBObject("loc", "2d"));
	    final BasicDBObject filter = new BasicDBObject("$near", new double[] { -89.496994999999998, 43.107227000000002 });
	    filter.put("$maxDistance", 5);
	        final BasicDBObject query1 = new BasicDBObject("loc", filter);
	        
	        Bson query2 = Filters.near("location", new Point(new Position(43.107227000000002, -89.496994999999998)), 5000.0, 1000.0);
	        
	        /*BsonArray arr = new BsonArray();
	        arr.add(new BsonDouble(43.107227000000002));
	        arr.add(new BsonDouble(-89.496994999999998));
	        BsonArray arr2 = new BsonArray();
	        arr2.add(arr);
	        arr2.add(new BsonDouble(5/3963.2));*/
	        BasicDBList arr = new BasicDBList();
	        //arr.add(new BsonDouble(43.107227000000002));
	        arr.add(43.107227000000002);
	        arr.add(-89.496994999999998);
	        BasicDBList arr2 = new BasicDBList();
	        arr2.add(arr);
	        arr2.add(5/3963.2);
//	        BsonDocument query3 = new BsonDocument("$match", new BsonDocument("loc", new BsonDocument("$within", new BsonDocument("$center", arr2))));
	        BasicDBObject query3 = new BasicDBObject("$match", new BasicDBObject("loc", new BasicDBObject("$within", new BasicDBObject("$center", arr2))));
	       // iterable = db.getCollection(Constants.COLLECTION_BUSINESS).find(query1);
	        BasicDBList geoCoord = new BasicDBList();
	        geoCoord.add(-89.496994999999998);
	        geoCoord.add(43.107227000000002);

	        BasicDBList geoParams = new BasicDBList();
	        geoParams.add(geoCoord);
	        geoParams.add(5/3963.2);

	        BasicDBList Loc = new BasicDBList();
	        Loc.add("$longitude");
	        Loc.add("$latitude");
	        BasicDBObject query4 = new BasicDBObject("Loc", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));
	        BasicDBObject query5 = new BasicDBObject("$match", query4);
	       // if((comboBox_Proximity.getSelectedItem()) != null ){
	        //businessQuery.append("Loc", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));
	        MongoClient client = new MongoClient();
	        DB db1 = client.getDB(Constants.COLLECTION_BUSINESS);
	        DBCursor cursor_geo = db1.getCollection(Constants.COLLECTION_BUSINESS).find(query4);    
	        DBObject res_geo;
	        while(cursor_geo.hasNext()){
	         res_geo= cursor_geo.next();
	         System.out.println(res_geo);
	        }
	        //}

	        int count = 0;
	        /*for (final DBObject venue : getCollection().find(query1).toArray()) {
	            //System.out.println("---- near venue: " + venue.get("name"));
	            count++;
	        }*/
	        FindIterable<Document> iterableAnd = null;
	        //iterableAnd = ((FindIterable<Document>) db.getCollection("businesses")).projection(Projections.fields(Projections.include("latitude", "longitude"), Projections.excludeId()));
	        
	        //***********NEAR********************************************************************************************************
		/*while(cur.hasNext()) {
			Business business = new Business();
	    	business.setCity((String) cur.next().get("city"));
	    	business.setState((String) cur.next().get("state"));
	    	business.setStars((double) cur.next().get("stars"));
	    	business.setName((String) cur.next().get("name"));
	    	businessList.add(business);
		    System.out.println(cur.next().get("post_id"));
		}*/
		/*iterable1 = db.getCollection(Constants.COLLECTION_BUSINESS)
				.find(query);*/
		
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Business business = new Business();
		    	business.setCity((String) document.get("city"));
		    	business.setState((String) document.get("state"));
		    	business.setStars(document.getDouble("stars"));
		    	business.setName((String) document.get("name"));
		    	business.setBusinessId(((String) document.get("business_id")));
		    	businessList2.add(business);
		        //System.out.println(document);
		    }
		});
		businessList.retainAll(businessList2);
		return businessList;
	}
	
	public List<Review> findReviews(SearchCriteria searchCriteria){

		final List<Review> reviewList = new ArrayList<>();
		FindIterable<Document> iterable1 = db.getCollection(Constants.COLLECTION_REVIEW)
				.find(Filters.eq("business_id", searchCriteria.getBusiness().getBusinessId()));
		iterable1.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	Review review = new Review();
		    	review.setDate(((String) document.get("date")));
		    	review.setText(((String) document.get("text")));
		    	int i = document.getInteger("stars");
		    	review.setStars(i);
//		    	review.setStars(document.getDouble("stars"));
		    	review.setUser_id(((String) document.get("user_id")));
		    	/*review.setVotes_useful((long) ((document.get("votes.useful"))));*/
		    	review.setVotes_useful(((Document)(document.get("votes"))).getInteger("useful"));
		    	reviewList.add(review);
		        //System.out.println(document);
		    }
		});
		for(Review review: reviewList){
			FindIterable<Document> iterable2 = db.getCollection(Constants.COLLECTION_USER)
					.find(Filters.eq("user_id", review.getUser_id()));
			iterable2.forEach(new Block<Document>() {
			    @Override
			    public void apply(final Document document) {
			    	review.setUserName((String) document.get("name"));
			    }
			});
		}
		return reviewList;
	}
}
