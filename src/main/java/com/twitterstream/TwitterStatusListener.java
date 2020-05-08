package com.twitterstream;

import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bson.BSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.*;
import twitter4j.json.DataObjectFactory;

@Slf4j
@Component
public class TwitterStatusListener {
    @Autowired
    MongoConnection mongoConnection;
    @Autowired
    TwitterConnection twitterConnection;
    @Value("${delete_database_at_startup}")
    boolean deleteDatabaseAtStartup;

    public TwitterStream establishTwitterStreamHandler() {
        if (deleteDatabaseAtStartup) {
            mongoConnection.getMongoDatabase().getCollection("tweets_added").drop();
            mongoConnection.getMongoDatabase().getCollection("tweets_deleted").drop();
        }
        if (mongoConnection.getMongoDatabase().getCollection("tweets_added") == null) {
            mongoConnection.getMongoDatabase().createCollection("tweets_added");
            mongoConnection.getMongoDatabase().getCollection("tweets_added").createIndex(Indexes.descending("timestamp_ms"));
        }
        if (mongoConnection.getMongoDatabase().getCollection("tweets_deleted") == null) {
            mongoConnection.getMongoDatabase().createCollection("tweets_deleted");
        }
        MongoCollection<Document> tweetsAdded = mongoConnection.getMongoDatabase().getCollection("tweets_added");
        MongoCollection<Document> tweetsDeleted = mongoConnection.getMongoDatabase().getCollection("tweets_deleted");
        TwitterStream twitterStream = new TwitterStreamFactory(twitterConnection.configurationBuilder().build()).getInstance().addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                Document parseDocument = createTweetDataFromTwitterResponse(status);
                if (parseDocument != null) {
                    tweetsAdded.insertOne(parseDocument);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

                Document parseDocument = createTweetDataFromDeletionNotice(statusDeletionNotice);
                if (parseDocument != null) {
                    tweetsDeleted.insertOne(parseDocument);
                }

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
        return twitterStream.sample();
    }

    private Document createTweetDataFromTwitterResponse(TwitterResponse twitterResponse) {
        String foundJson = DataObjectFactory.getRawJSON(twitterResponse);
        Document returnDocument = null;
        try {
            returnDocument = Document.parse(foundJson);
        } catch (Exception e) {
            log.error("Logging out status: {}", foundJson, e);
        }
        return returnDocument;
    }

    private Document createTweetDataFromDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        Gson gson = new Gson();
        String foundJson = gson.toJson(statusDeletionNotice);
        Document returnDocument = null;
        try {
            returnDocument = Document.parse(foundJson);
        } catch (Exception e) {
            log.error("Logging out deletion: {}", foundJson, e);
        }
        return returnDocument;
    }


}
