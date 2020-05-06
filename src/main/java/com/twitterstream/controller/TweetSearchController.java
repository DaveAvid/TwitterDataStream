package com.twitterstream.controller;

import com.twitterstream.MongoAccess;
import com.twitterstream.model.Tweet;
import com.twitterstream.model.TweetSearchText;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class TweetSearchController {
    @Autowired
    MongoAccess mongoAccess;
    @PostMapping("/searchTweets")
    public String searchTweets(@ModelAttribute("tweet") @Valid TweetSearchText tweetSearchText,
                                      BindingResult result, Model model){
        List<String> keywords = new ArrayList<>();
        if(tweetSearchText.getSearchText().contains(",")) {
            String[] splitSearchText = tweetSearchText.getSearchText().split(",");
            for(String record: splitSearchText){
                keywords.add(StringUtils.strip(record));
            }
        }else{
            keywords.add(StringUtils.strip(tweetSearchText.getSearchText()));
        }
        List<Document> myList = mongoAccess.findTweetsByKeyword(keywords);
        List<Tweet> tweetList = new ArrayList<>();
        for(Document record: myList){
            Tweet tweet = new Tweet();
            tweet.setName(record.get("user",Document.class).getString("name"));
            tweet.setScreenName(record.get("user",Document.class).getString("screen_name"));
            tweet.setText(record.getString("text"));
            tweet.setCreatedAt(record.getString("created_at"));
            tweetList.add(tweet);
        }
        model.addAttribute("tweets",tweetList);
        log.debug("Searching for keywords within tweets: {}",tweetSearchText.getSearchText());
        return "index";
    }
}
