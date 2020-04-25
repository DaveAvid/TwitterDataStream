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

        document.append("user", status.getUser());
        document.append("tweet_text", status.getText());
        document.append("contributors", status.getContributors());
        document.append("created_at", status.getCreatedAt());
        document.append("tweet_id", status.getId());
        document.append("current_user_retweet_id", status.getCurrentUserRetweetId());
        document.append("display_text_range_end", status.getDisplayTextRangeEnd());
        document.append("display_text_range_start", status.getDisplayTextRangeStart());
        document.append("favorite_count", status.getFavoriteCount());
        document.append("geo_location", status.getGeoLocation());
        document.append("in_reply_to_screen_name", status.getInReplyToScreenName());
        document.append("in_reply_to_status_id", status.getInReplyToStatusId());
        document.append("in_reply_to_user_id", status.getInReplyToUserId());
        document.append("language", status.getLang());
        document.append("place", status.getPlace());
        document.append("quoted_status", status.getQuotedStatus());
        document.append("quoted_status_id", status.getQuotedStatusId());
        document.append("quoted_status_permalink", status.getQuotedStatusPermalink());
        document.append("retweet_count", status.getRetweetCount());
        document.append("retweet_status", status.getRetweetedStatus());
        document.append("scopes", status.getScopes());
        document.append("source", status.getSource());
        document.append("withheld_in_countries", status.getWithheldInCountries());
        document.append("is_favorited", status.isFavorited());
        document.append("is_possibly_sensitive", status.isPossiblySensitive());
        document.append("is_retweet", status.isRetweet());
        document.append("is_retweeted", status.isRetweeted());
        document.append("is_retweeted_by_me", status.isRetweetedByMe());
        document.append("is_truncated", status.isTruncated());
        return document;

    }

    private  Document createUserDataFromStatus(Status status) {
        Document document = new Document();

        document.append("400x400_profile_image_url", status.getUser().get400x400ProfileImageURL());
        document.append("400x400_profile_image_url_https", status.getUser().get400x400ProfileImageURLHttps());
        document.append("bigger_profile_image_url", status.getUser().getBiggerProfileImageURL());
        document.append("bigger_profile_image_url_https", status.getUser().getBiggerProfileImageURLHttps());
        document.append("created_at", status.getUser().getCreatedAt());
        document.append("description", status.getUser().getDescription());
        document.append("description_url_entities", status.getUser().getDescriptionURLEntities());
        document.append("email", status.getUser().getEmail());
        document.append("favourites_count", status.getUser().getFavouritesCount());
        document.append("followers_count", status.getUser().getFollowersCount());
        document.append("friends_count", status.getUser().getFriendsCount());
        document.append("id", status.getUser().getId());
        document.append("language", status.getUser().getLang());
        document.append("listed_count", status.getUser().getListedCount());
        document.append("location", status.getUser().getLocation());
        document.append("mini_profile_image_url", status.getUser().getMiniProfileImageURL());
        document.append("created_at", status.getUser().getCreatedAt());
        document.append("created_at", status.getUser().getCreatedAt());
        document.append("created_at", status.getUser().getCreatedAt());
        document.append("created_at", status.getUser().getCreatedAt());
        document.append("created_at", status.getUser().getCreatedAt());
        document.append("created_at", status.getUser().getCreatedAt());

        return document;
    }

}
