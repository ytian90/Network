package src;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Connect the Java project to MongoDB
 * @author yutian
 * @since Apr 15, 2016
 */
public class MongodbConn {

	public static void main(String[] args) {
		
    	try {

    		/**** Connect to MongoDB ****/
    		// Since 2.10.0, uses MongoClient
    		MongoClient mongo = new MongoClient("localhost", 27017);

    		/**** Get database ****/
    		// if database doesn't exists, MongoDB will create it for you
    		DB db = mongo.getDB("testdb");

    		/**** Get collection / table from 'testdb' ****/
    		// if collection doesn't exists, MongoDB will create it for you
    		DBCollection table = db.getCollection("user");

    		/**** Insert ****/
    		// create a document to store key and value
    		BasicDBObject document = new BasicDBObject();
    		document.put("name", "larry");
    		document.put("age", 50);
    		document.put("createdDate", new Date());
    		table.insert(document);

    		/**** Find and display ****/
    		BasicDBObject searchQuery = new BasicDBObject();
    		searchQuery.put("name", "larry");

    		DBCursor cursor = table.find(searchQuery);

    		while (cursor.hasNext()) {
    			System.out.println(cursor.next());
    		}

    		/**** Update ****/
    		// search document where name="mkyong" and update it with new values
    		BasicDBObject query = new BasicDBObject();
    		query.put("name", "larry");

    		BasicDBObject newDocument = new BasicDBObject();
    		newDocument.put("name", "new-larry");

    		BasicDBObject updateObj = new BasicDBObject();
    		updateObj.put("$set", newDocument);

    		table.update(query, updateObj);

    		/**** Find and display ****/
    		BasicDBObject searchQuery2 
    		    = new BasicDBObject().append("name", "new-larry");

    		DBCursor cursor2 = table.find(searchQuery2);

    		while (cursor2.hasNext()) {
    			System.out.println(cursor2.next());
    		}

    		/**** Done ****/
    		System.out.println("Done");

    	    } catch (UnknownHostException e) {
    		e.printStackTrace();
    	    } catch (MongoException e) {
    		e.printStackTrace();
    	    }
		
	}
	
	public String generateUniqueKey(){
    	String id = UUID.randomUUID().toString();
    	return id;
    }
	
	public void add(BasicDBObject obj) {
		try {

    		/**** Connect to MongoDB ****/
    		// Since 2.10.0, uses MongoClient
    		MongoClient mongo = new MongoClient("localhost", 27017);

    		/**** Get database ****/
    		// if database doesn't exists, MongoDB will create it for you
    		DB db = mongo.getDB("testdb");

    		/**** Get collection / table from 'testdb' ****/
    		// if collection doesn't exists, MongoDB will create it for you
    		DBCollection table = db.getCollection("user");

    		/**** Insert ****/
    		// create a document to store key and value
    		table.insert(obj);

    	    } catch (UnknownHostException e) {
    		e.printStackTrace();
    	    } catch (MongoException e) {
    		e.printStackTrace();
    	    }
		
		
	}

}
