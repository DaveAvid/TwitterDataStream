//package com.twitterstream;
//
//import com.mongodb.client.MongoCollection;
//import org.bson.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//public class MongoAccess {
//    @Autowired
//    MongoConnection mongoConnection;
//
//    public Document findAll() {
//        MongoCollection<Document> tweetsAdded = mongoConnection.getMongoDatabase().getCollection("tweets_added");
//
//        Document findQuery = new Document("id", 1256330908149231618);
//        Document orderBy = new Document("decade", 1);
//
//        MongoCursor<Document> cursor = tweets.findAll(findQuery).sort(orderBy).iterator();
//
//        try {
//            while (cursor.hasNext()) {
//                Document doc = cursor.next();
//                System.out.println(
//                        "In the " + doc.get("decade") + ", " + doc.get("song") +
//                                " by " + doc.get("artist") + " topped the charts for " +
//                                doc.get("weeksAtOne") + " straight weeks."
//                );
//            }
//        } finally {
//            cursor.close();
//        }
//
//        // Since this is an example, we'll clean up after ourselves.
//
//        songs.drop();
//
//        // Only close the connection when your app is terminating
//
//        client.close();
//    }
//}