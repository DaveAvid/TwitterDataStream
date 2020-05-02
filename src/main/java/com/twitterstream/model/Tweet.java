package com.twitterstream.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class Tweet {
    String screenName;
    String createdAt;
    String name;
    String text;
}
