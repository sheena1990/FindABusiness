package com.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBUtil {
	public static MongoDatabase getDatabase(){
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase(Constants.DATABASE_NAME);
		//DB asd = client.getDB(Constants.DATABASE_NAME);
		return database;
	}
}
