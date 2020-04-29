package com.twitterstream;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;
@Slf4j
@Component
public class TwitterStatusListener {
    @Autowired
    MongoConnection mongoConnection;
    @Autowired
    TwitterConnection twitterConnection;
    public TwitterStream establishTwitterStreamHandler(){
        MongoCollection<Document> tweets = mongoConnection.getMongoDatabase().getCollection("tweets");
        MongoCollection<Document> users = mongoConnection.getMongoDatabase().getCollection("users");

        TwitterStream twitterStream = new TwitterStreamFactory(twitterConnection.configurationBuilder().build()).getInstance().addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                log.info("Received Tweet from Twitter: {}", status.getText());
                tweets.insertOne(createTweetDataFromStatus(status));
                //users.insertOne(createUserDataFromStatus(status));
                //System.out.println(status.getUser());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
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

    private Document createTweetDataFromStatus(Status status) {
        Document document = new Document();

          document.put("id",status.getUser().getId());
          document.put("tweet_text", status.getText());
//        document.put("contributors", status.getContributors());
//        document.put("created_at", status.getCreatedAt());
//        document.put("tweet_id", status.getId());
//        document.put("current_user_retweet_id", status.getCurrentUserRetweetId());
//        document.put("display_text_range_end", status.getDisplayTextRangeEnd());
//        document.put("display_text_range_start", status.getDisplayTextRangeStart());
//        document.put("favorite_count", status.getFavoriteCount());
//        //document.put("geo_location", status.getGeoLocation());
//        document.put("in_reply_to_screen_name", status.getInReplyToScreenName());
//        document.put("in_reply_to_status_id", status.getInReplyToStatusId());
//        document.put("in_reply_to_user_id", status.getInReplyToUserId());
//        document.put("language", status.getLang());
//        //document.put("place", status.getPlace());
//        //document.put("quoted_status", status.getQuotedStatus());
//        document.put("quoted_status_id", status.getQuotedStatusId());
//        //document.put("quoted_status_permalink", status.getQuotedStatusPermalink());
//        document.put("retweet_count", status.getRetweetCount());
//        //document.put("retweet_status", status.getRetweetedStatus());
//        //document.put("scopes", status.getScopes());
//        document.put("source", status.getSource());
//        document.put("withheld_in_countries", status.getWithheldInCountries());
//        document.put("favorited", status.isFavorited());
//        document.put("possibly_sensitive", status.isPossiblySensitive());
//        document.put("retweet", status.isRetweet());
//        document.put("retweeted", status.isRetweeted());
//        document.put("retweeted_by_me", status.isRetweetedByMe());
//        document.put("truncated", status.isTruncated());
        return document;

    }

    private  Document createUserDataFromStatus(Status status) {
        Document document = new Document();

        document.put("400x400_profile_image_url", status.getUser().get400x400ProfileImageURL());
        document.put("400x400_profile_image_url_https", status.getUser().get400x400ProfileImageURLHttps());
        document.put("bigger_profile_image_url", status.getUser().getBiggerProfileImageURL());
        document.put("bigger_profile_image_url_https", status.getUser().getBiggerProfileImageURLHttps());
        document.put("created_at", status.getUser().getCreatedAt());
        document.put("description", status.getUser().getDescription());
        document.put("description_url_entities", status.getUser().getDescriptionURLEntities());
        document.put("email", status.getUser().getEmail());
        document.put("favourites_count", status.getUser().getFavouritesCount());
        document.put("followers_count", status.getUser().getFollowersCount());
        document.put("friends_count", status.getUser().getFriendsCount());
        document.put("id", status.getUser().getId());
        document.put("language", status.getUser().getLang());
        document.put("listed_count", status.getUser().getListedCount());
        document.put("location", status.getUser().getLocation());
        document.put("mini_profile_image_url", status.getUser().getMiniProfileImageURL());
        document.put("mini_profile_image_url_https", status.getUser().getMiniProfileImageURLHttps());
        document.put("name", status.getUser().getName());
        document.put("original_profile_image_url", status.getUser().getOriginalProfileImageURL());
        document.put("original_profile_image_url_https", status.getUser().getOriginalProfileImageURLHttps());
        document.put("profile_background_color", status.getUser().getProfileBackgroundColor());
        document.put("profile_background_image_url", status.getUser().getProfileBackgroundImageURL());
        document.put("profile_background_image_url_https", status.getUser().getProfileBackgroundImageUrlHttps());
        document.put("profile_banner_300x100_url", status.getUser().getProfileBanner300x100URL());
        document.put("profile_banner_600x200_url", status.getUser().getProfileBanner600x200URL());
        document.put("profile_banner_1500x500_url", status.getUser().getProfileBanner1500x500URL());
        document.put("profile_banner_ipad_retina_url", status.getUser().getProfileBannerIPadRetinaURL());
        document.put("profile_banner_ipad_url", status.getUser().getProfileBannerIPadURL());
        document.put("profile_banner_mobile_retina_url", status.getUser().getProfileBannerMobileRetinaURL());
        document.put("profile_banner_mobile_url", status.getUser().getProfileBannerMobileURL());
        document.put("profile_banner_retina_url", status.getUser().getProfileBannerRetinaURL());
        document.put("profile_banner_url", status.getUser().getProfileBannerURL());
        document.put("profile_image_url", status.getUser().getProfileImageURL());
        document.put("profile_image_url_https", status.getUser().getProfileImageURLHttps());
        document.put("profile_link_color", status.getUser().getProfileLinkColor());
        document.put("profile_sidebar_border_color", status.getUser().getProfileSidebarBorderColor());
        document.put("profile_sidebar_fill_color", status.getUser().getProfileSidebarFillColor());
        document.put("profile_text_color", status.getUser().getProfileTextColor());
        document.put("screen_name", status.getUser().getScreenName());
        document.put("status", status.getUser().getStatus());
        document.put("statuses_count", status.getUser().getStatusesCount());
        document.put("time_zone", status.getUser().getTimeZone());
        document.put("url", status.getUser().getURL());
        document.put("url_entity", status.getUser().getURLEntity());
        document.put("utc_offset", status.getUser().getUtcOffset());
        document.put("withheld_in_countries", status.getUser().getWithheldInCountries());
        document.put("contributors_enabled", status.getUser().isContributorsEnabled());
        document.put("default_profile", status.getUser().isDefaultProfile());
        document.put("default_profile_image", status.getUser().isDefaultProfileImage());
        document.put("follow_request_sent", status.getUser().isFollowRequestSent());
        document.put("geo_enabled", status.getUser().isGeoEnabled());
        document.put("profile_background_tiled", status.getUser().isProfileBackgroundTiled());
        document.put("profile_use_background_image", status.getUser().isProfileUseBackgroundImage());
        document.put("protected", status.getUser().isProtected());
        document.put("show_all_inline_media", status.getUser().isShowAllInlineMedia());
        document.put("translator", status.getUser().isTranslator());
        document.put("verified", status.getUser().isVerified());

        return document;
    }

}
