//package com.twitterstream;
//
//import com.mongodb.client.MongoCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.swing.text.Document;
//
//public class MongoAccess {
//    @Autowired
//    MongoConnection mongoConnection;
//
//    MongoCollection<org.bson.Document> tweets = mongoConnection.getMongoDatabase().getCollection("tweets");
//    MongoCollection<org.bson.Document> users = mongoConnection.getMongoDatabase().getCollection("users");
//
//    Document updateQuery = new Document("song", "One Sweet Day");
//        tweets.updateOne(updateQuery, new Document("$set", new Document("artist", "Mariah Carey ft. Boyz II Men")));
//
//    /*
//     * Finally we run a query which returns all the hits that spent 10
//     * or more weeks at number 1.
//     */
//
//    Document findQuery = new Document("weeksAtOne", new Document("$gte",10));
//    Document orderBy = new Document("decade", 1);
//
//    MongoCursor<Document> cursor = tweets.findAll(findQuery).sort(orderBy).iterator();
//
//        try {
//        while (cursor.hasNext()) {
//            Document doc = cursor.next();
//            System.out.println(
//                    "In the " + doc.get("decade") + ", " + doc.get("song") +
//                            " by " + doc.get("artist") + " topped the charts for " +
//                            doc.get("weeksAtOne") + " straight weeks."
//            );
//        }
//    } finally {
//        cursor.close();
//    }
//
//    // Since this is an example, we'll clean up after ourselves.
//
//        songs.drop();
//
//    // Only close the connection when your app is terminating
//
//        client.close();
//}
