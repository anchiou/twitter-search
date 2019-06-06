package com.search.twitter.searcher.entity;

import com.search.twitter.searcher.JSONUtils;

public class TweetObject {
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------ 
   
    // Tweet username
    private String username;

    // Tweet screen name
    private String screen_name;

    // Tweet text
    private String text;

    // Tweet favorite_count
    private int favorite_count;

    // Tweet retweet_count
    private int retweet_count;

    // Tweet reply_count
    private int reply_count;

    // Tweet verified status
    private boolean verified;

    // Tweet score
    private double score;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    public TweetObject() { }

    public TweetObject(String username, String screen_name, String text, int favorite_count,
                       int retweet_count, int reply_count, boolean verified, double score) {
        this.username = username;
        this.screen_name = screen_name;
        this.text = text;
        this.favorite_count = favorite_count;
        this.retweet_count = retweet_count;
        this.reply_count = reply_count;
        this.verified = verified;
        this.score = score;
    }

    // Getter and setter methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getScreenName() {
        return screen_name;
    }

    public void setScreenName(String value) {
        this.screen_name = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public int getFavoriteCount() {
        return favorite_count;
    }

    public void setFavoriteCount(int value) {
        this.favorite_count = value;
    }

    public int getRetweetCount() {
        return retweet_count;
    }

    public void setRetweetCount(int value) {
        this.retweet_count = value;
    }

    public int getReplyCount() {
        return reply_count;
    }

    public void setReplyCount(int value) {
        this.reply_count = value;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean value){
        this.verified = value;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double value) {
        this.score = value;
    }

    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}