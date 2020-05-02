package com.twitterstream.controller;

import com.twitterstream.model.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class SiteController {

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Tweet> tweetList = new ArrayList<>();
        Tweet tweetOne = Tweet.builder().name("Bob Dole").screenName("Dole").createdAt("04-25-2020").text("This mongo shit sucks").build();
        Tweet tweetTwo = Tweet.builder().name("Ross Peru").screenName("Ross").createdAt("04-25-2020").text("This mongo shit sucks").build();
        tweetList.add(tweetOne);
        tweetList.add(tweetTwo);
        model.addAttribute("tweets",tweetList);
        return "tweets";}

    @GetMapping("/users")
    public String showUsers() {return "users";}

    @GetMapping("/tweets")
    public String showTweets() {return "tweets";}
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
