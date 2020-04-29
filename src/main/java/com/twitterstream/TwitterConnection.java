package com.twitterstream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
@Slf4j
public class TwitterConnection {
        @Value("${oauth.consumerKey}")
        private String consumerKey;
        @Value("${oauth.consumerSecret}")
        private String consumerSecret;
        @Value("${oauth.accessToken}")
        private String accessToken;
        @Value("${oauth.accessTokenSecret}")
        private String accessTokenSecret;
        public ConfigurationBuilder configurationBuilder() {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(consumerKey)
                    .setOAuthConsumerSecret(consumerSecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);
            return cb;
        }
    }

