package com.twitterstream.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Tweet {
    String screenName;
    String createdAt;
    String name;
    String text;
}
