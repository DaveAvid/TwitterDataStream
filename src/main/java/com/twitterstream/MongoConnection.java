package com.twitterstream;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MongoConnection {
    @Value("${mongodb.uri}")
    private String mongodbUri;
    @Value("${mongodb.database}")
    private String mongodbDatabase;
    public MongoDatabase getMongoDatabase() {
        MongoClientURI uri = new MongoClientURI(mongodbUri);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase(mongodbDatabase);
        db.getName();
        log.error("Connected to Mongo Name:{} DB: {}",db.getName(),mongodbDatabase);
        return db;
    }
}
