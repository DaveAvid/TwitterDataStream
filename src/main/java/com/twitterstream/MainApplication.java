package com.twitterstream;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class MainApplication {
	@Autowired
	TwitterStatusListener twitterListener;
	@Autowired
	MongoConnection mongoConnection;
	@PostConstruct
	public void establishTwitterStream() {
		log.info("Starting Twitter Connection Stream...");
		twitterListener.establishTwitterStreamHandler();
		log.info("Connection Established.");
		MongoCollection<Document> tweets = mongoConnection.getMongoDatabase().getCollection("tweets");
		log.info("What is up:");
		Document exampleTweet = new Document();
		exampleTweet.append("twitter_text","test");
		tweets.insertOne(exampleTweet);
		log.info("???");

	}
	@Value("${server.port}")
	private int serverPortNumber;

	public static void main(String[] args) {
		log.info("Spring Boot Starting...");
		SpringApplication.run(MainApplication.class, args);
	}



	@PostConstruct
	public void getServerAddress() throws UnknownHostException {
		String ip = InetAddress.getLoopbackAddress().getHostAddress();
		String hostName = InetAddress.getLocalHost().getHostName();
		log.info("Server Started. View Server By IP Here: http://" + ip + ":" + serverPortNumber);
		log.info("Server Started. View Server By Hostname Here: http://" + hostName + ":" + serverPortNumber);
	}

}
