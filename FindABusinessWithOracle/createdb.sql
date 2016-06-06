  CREATE TABLE "BUSINESS" 
   (	"B_ID" VARCHAR2(40 BYTE) NOT NULL ENABLE, 
	"B_NAME" VARCHAR2(100 BYTE), 
	"FULL_ADDRESS" VARCHAR2(200 BYTE), 
	"CITY" VARCHAR2(60 BYTE), 
	"STATE" VARCHAR2(5 BYTE), 
	"ATTRIBUTES" VARCHAR2(1500 BYTE), 
	"CATEGORIES" VARCHAR2(200 BYTE), 
	"HOURS" VARCHAR2(400 BYTE), 
	"STARS" NUMBER, 
	 PRIMARY KEY ("B_ID")
	 );
   
   CREATE TABLE Business_Category
  (
    bc_id   VARCHAR2(10) NOT NULL PRIMARY KEY,
    bc_name VARCHAR2(30)
  );
  
    CREATE TABLE "YELP_USER" 
   (	"YELPING_SINCE" VARCHAR2(20 BYTE), 
	"VOTES_FUNNY" NUMBER, 
	"VOTES_COOL" NUMBER, 
	"VOTES_USEFUL" NUMBER, 
	"REVIEW_COUNT" NUMBER, 
	"NAME" VARCHAR2(50 BYTE), 
	"USER_ID" VARCHAR2(40 BYTE) NOT NULL ENABLE, 
	"FRIENDS" LONG, 
	"FANS" NUMBER, 
	"AVERAGE_STARS" NUMBER, 
	"COMPLIMENTS" VARCHAR2(1000 BYTE), 
	"ELITE" VARCHAR2(1000 BYTE), 
	 PRIMARY KEY ("USER_ID")
	 );
   
   CREATE TABLE review
  (
    review_id    VARCHAR2(40) NOT NULL PRIMARY KEY,
    user_id      VARCHAR2(40),
    business_id  VARCHAR2(40),
    stars        NUMBER,
    r_date       VARCHAR2(20),
    text         long,
    votes_funny  NUMBER,
    votes_useful NUMBER,
    votes_cool   NUMBER,
    CONSTRAINT fk_review_business FOREIGN KEY (business_id) REFERENCES business(B_id),
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES yelp_User(user_id)
  );
  
   SET DEFINE OFF;
INSERT INTO business_category VALUES (1, 'Active Life');
INSERT INTO business_category VALUES (2, 'Arts & Entertainment');
INSERT INTO business_category VALUES (3, 'Automotive');
INSERT INTO business_category VALUES (4, 'Car Rental');
INSERT INTO business_category VALUES (5, 'Cafes');
INSERT INTO business_category VALUES (6, 'Beauty & Spas');
INSERT INTO business_category VALUES (7, 'Convenience Stores');
INSERT INTO business_category VALUES (8, 'Dentists');
INSERT INTO business_category VALUES (9, 'Doctors');
INSERT INTO business_category VALUES (10, 'Drugstores');
INSERT INTO business_category VALUES (11, 'Department Stores');
INSERT INTO business_category VALUES (12, 'Education');
INSERT INTO business_category VALUES (13, 'Event Planning & Services');
INSERT INTO business_category VALUES (14, 'Flowers & Gifts');
INSERT INTO business_category VALUES (15, 'Food');
INSERT INTO business_category VALUES (16, 'Health & Medical');
INSERT INTO business_category VALUES (17, 'Home Services');
INSERT INTO business_category VALUES (18, 'Home & Garden');
INSERT INTO business_category VALUES (19, 'Hospitals');
INSERT INTO business_category VALUES (20, 'Hotels & Travel');
INSERT INTO business_category VALUES (21, 'Hardware Stores');
INSERT INTO business_category VALUES (22, 'Grocery');
INSERT INTO business_category VALUES (23, 'Medical Centers');
INSERT INTO business_category VALUES (24, 'Nurseries & Gardening');
INSERT INTO business_category VALUES (25, 'Nightlife');
INSERT INTO business_category VALUES (26, 'Restaurants');
INSERT INTO business_category VALUES (27, 'Shopping');
INSERT INTO business_category VALUES (28, 'Transportation');
COMMIT;