package com.twitterstream.controller;

import com.twitterstream.MongoAccess;
import com.twitterstream.model.Tweet;
import com.twitterstream.model.SearchText;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class SiteController {
    @Autowired
    MongoAccess mongoAccess;

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Document> myList = mongoAccess.findAllToArray();
        List<Tweet> tweetList = new ArrayList<>();
        for (Document record : myList) {
            Tweet tweet = new Tweet();
            tweet.setName(record.get("user", Document.class).getString("name"));
            tweet.setScreenName(record.get("user", Document.class).getString("screen_name"));
            tweet.setText(record.getString("text"));
            tweet.setCreatedAt(record.getString("created_at"));
            tweetList.add(tweet);
        }
        model.addAttribute("tweets", tweetList);
        model.addAttribute("tweet", new SearchText());
        return "index";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<Document> myList = mongoAccess.findAllToArray();
        List<Tweet> tweetList = new ArrayList<>();
        for (Document record : myList) {
            Tweet tweet = new Tweet();
            tweet.setName(record.get("user", Document.class).getString("name"));
            tweet.setScreenName(record.get("user", Document.class).getString("screen_name"));
            tweet.setText(record.getString("text"));
            tweet.setCreatedAt(record.getString("created_at"));
            tweetList.add(tweet);
        }
        model.addAttribute("tweets", tweetList);
        model.addAttribute("name", new SearchText());
        return "users";
    }

    @GetMapping("/tweets")
    public String showTweets(Model model) {
        List<Document> myList = mongoAccess.findAllToArray();
        List<Tweet> tweetList = new ArrayList<>();
        for (Document record : myList) {
            Tweet tweet = new Tweet();
            tweet.setName(record.get("user", Document.class).getString("name"));
            tweet.setScreenName(record.get("user", Document.class).getString("screen_name"));
            tweet.setText(record.getString("text"));
            tweet.setCreatedAt(record.getString("created_at"));
            tweetList.add(tweet);
        }
        model.addAttribute("tweets", tweetList);
        model.addAttribute("tweet", new SearchText());
        return "tweets";
    }
//    @RequestMapping("/tweets")
//    public String list(ModelMap model, @SortDefault("username") Pageable pageable){
//        //model.addAttribute("page", userService.find(pageable));
//
//        return "userData";
//    }
//    @RequestMapping("/users")
//    public String list(ModelMap model, @SortDefault("username") Pageable pageable){
//        //model.addAttribute("page", userService.find(pageable));
//
//        return "tweetData";
//    }
}
