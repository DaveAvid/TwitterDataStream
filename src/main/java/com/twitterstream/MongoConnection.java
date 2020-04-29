package com.twitterstream;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
@Slf4j
public class MongoConnection {
    @Value("${mongodb.uri}")
    private String mongodbUri;
    @Value("${mongodb.database}")
    private String mongodbDatabase;
    public MongoDatabase getMongoDatabase() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientURI uri = new MongoClientURI(mongodbUri);
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase db = mongoClient.getDatabase(mongodbDatabase);
        db = db.withCodecRegistry(pojoCodecRegistry);
        db.getName();
        log.error("Connected to Mongo Name:{} DB: {}",db.getName(),mongodbDatabase);
        return db;
    }
}
