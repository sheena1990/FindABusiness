/*package com.model.dao;

public class TryDao {
	double[] lat = {40, 36, 31, 34, 41};
	double[] longi = {74, 119, 99, 111, 87};
	jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"None", "New York", "California", "Texas", "Arizona", "Chicago"}));

	        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Any", "5", "10", "20", "30", "50"}));
	--------------------------------
	FindIterable<Document> iterable = db.getCollection("business").find();
	            // TODO add your handling code here:
	            int poi_id = jComboBox2.getSelectedIndex() - 1;
	            List<String> bIds1 = get_values_as_per_category_list();
	            List<String> bIds2 = new ArrayList<>();

	            if (poi_id >= 0) {
	                MongoClient mongo = new MongoClient("localhost", 27017);
	                DB data_base = mongo.getDB("db");

	                DBCollection loc_db_map = data_base.getCollection("loc_db_map");
	                System.out.println(loc_db_map.count());
	                if (loc_db_map.count() == 0) {

	                    MongoCursor<Document> iter = iterable.iterator();

	                    while (iter.hasNext()) {
	                        Document iterator = iter.next();
	                        BasicDBObject doc = new BasicDBObject();
	                        String longitude = iterator.get("longitude").toString();
	                        String latitude = iterator.get("latitude").toString();
	                        float[] loc_array = new float[]{Float.parseFloat(longitude), Float.parseFloat(latitude)};
	                        String business_id = iterator.get("business_id").toString();
	                        Float[] Loc = {Float.parseFloat(longitude), Float.parseFloat(latitude)};

	                        doc.append("Loc", loc_array);
	                        doc.append("business_id", business_id);
	                        //   System.out.println(doc);
	                        //show_business(bIds1);
	                        //;
	                        // doc);
	                        loc_db_map.insert(doc);
	                        System.out.println(loc_db_map.count());

	                    }
	                }

	                DBObject index2d = BasicDBObjectBuilder.start("Loc", "2dsphere").get();
	                //loc_db_map.createIndex(index2d);

	                BasicDBObject myLoc = new BasicDBObject();
	                //myLoc.append("type", "Point");
	                myLoc.append("geoNear", "loc_db_map");
	                //double[] lat = {40, 36, 31, 34, 41};
	                //double[] longi = {74, 119, 99, 111, 87};
	                double[] loc = {longi[poi_id], lat[poi_id]};
	                try {
	                    double dis = Double.parseDouble(jComboBox3.getSelectedItem().toString());

	                    myLoc.append("near", loc);
	                    myLoc.append("spherical", true);
	                    myLoc.append("maxDistance", dis );

	                    MongoClient mongoClient = new MongoClient("localhost", 27017);
	                    DB database = mongoClient.getDB("db");

	                    CommandResult commandResult = database.command(myLoc);
	                    System.out.println(myLoc);
	                    BasicDBList results = (BasicDBList) commandResult.get("results");
	                    for (Iterator< Object> it = results.iterator(); it.hasNext();) {
	                        BasicDBObject result = (BasicDBObject) it.next();
	                        BasicDBObject dbo = (BasicDBObject) result.get("obj");
	                        bIds2.add(dbo.getString("business_id"));
	                    }
	                    System.out.println(bIds2.size());
	                    bIds1.retainAll(bIds2);
	                } catch (Exception ex) {
	                }
	            }
	            int all = jComboBox4.getSelectedIndex();
	            if (all == 0 && jList2.getSelectedValuesList().size() >= 2) {

	                for (int j = 0; j < bIds1.size(); j++) {
	                    if (j % 2 == 0) {
	                        bIds1.remove(j);
	                    }
	                }

	            }
	            show_business(bIds1);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
}
*/