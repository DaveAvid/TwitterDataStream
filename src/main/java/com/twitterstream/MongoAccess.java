package com.twitterstream;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MongoAccess {
    @Autowired
    MongoConnection mongoConnection;

    public ArrayList<Document> findAllToArray() {
        MongoCollection<Document> tweetsAdded = mongoConnection.getMongoDatabase().getCollection("tweets_added");
        long timeOffset = System.currentTimeMillis() - (60*60*1000);
        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put("timestamp_ms", new BasicDBObject("$gt", timeOffset+""));
        ArrayList<Document> allTweets = tweetsAdded.find(getQuery).into(new ArrayList<Document>());
        log.info("All tweets have been added to list: {}", allTweets.toString());
        return allTweets;
    }

    public List<Document> findTweetsByKeyword(List<String> keywords) {
        MongoCollection<Document> tweetsAdded = mongoConnection.getMongoDatabase().getCollection("tweets_added");
        BasicDBObject getQuery = new BasicDBObject();
        for(String keyword: keywords){
            getQuery.put("text", Pattern.compile(keyword, Pattern.CASE_INSENSITIVE));
        }
        ArrayList<Document> allTweets = tweetsAdded.find(getQuery).into(new ArrayList<Document>());
        log.info("All tweets have been added to list: {}", allTweets.toString());

        return allTweets;
    }
}