package com.twitterstream;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class TwitterStatusListener {
    @Autowired
    MongoConnection mongoConnection;
    public TwitterStream establishTwitterStreamHandler(){

        MongoCollection<Document> tweets = mongoConnection.getMongoDatabase().getCollection("tweets");
        MongoCollection<Document> users = mongoConnection.getMongoDatabase().getCollection("users");
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                tweets.insertOne(createTweetDataFromStatus(status));
                users.insertOne(createUserDataFromStatus(status));
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        });
        return twitterStream;
    }

    private Document createTweetDataFromStatus(Status status) {
        Document document = new Document();

        document.append("tweet_text", status.getText());
        document.append("contributors", status.getContributors());
        document.append("tweet_text", status.getText());
        document.append("tweet_text", status.getText());
        document.append("tweet_text", status.getText());
        document.append("tweet_text", status.getText());

        return document;

    }

    private  Document createUserDataFromStatus(Status status) {
        Document document = new Document();

        return document;
    }

}
