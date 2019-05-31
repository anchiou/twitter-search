package com.search.twitter.analyzer;

import org.apache.lucene.document.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Service
public class LuceneDocumentService {
    public Document getDocument(String string) {
        Document doc = new Document();
        //Main JSON File from line
        JSONObject jsonObject = new JSONObject(string);
        //Outside fields

        String createdAt = jsonObject.getString("created_at");
        doc.add(new TextField("created_at", createdAt, Field.Store.YES));
        String tweetIdStr = jsonObject.getString("id_str");
        doc.add(new StringField("id_str", tweetIdStr, Field.Store.YES));
        String tweetText = jsonObject.getString("text");
        doc.add(new TextField("text", tweetText, Field.Store.YES));
        String inReplyToScreenName = jsonObject.optString("in_reply_to_screen_name");
        doc.add(new StringField("in_reply_to_screen_name", inReplyToScreenName, Field.Store.YES));
        Integer retweetCount = jsonObject.getInt("retweet_count");
        doc.add(new IntPoint("retweet_count", retweetCount));
        Integer favoriteCount = jsonObject.optInt("favorite_count",0);
        doc.add(new IntPoint("favorite_count", favoriteCount));
        Integer replyCount = jsonObject.getInt("reply_count");
        doc.add(new IntPoint("reply_count", replyCount));
        String lang = jsonObject.optString("lang","en");
        doc.add(new StringField("lang", lang, Field.Store.YES));

        //Coordinates and Place
        JSONObject coordinates = jsonObject.optJSONObject("coordinates");
        JSONArray tweetCoordinates = coordinates.optJSONArray("coordinates");
        //FIXME: HOW TO STORE?
        JSONObject place = jsonObject.optJSONObject("place");
        String tweetCountry = place.optString("country");
        doc.add(new StringField("country", tweetCountry, Field.Store.YES));
        String tweetCountryCode = place.optString("country_code");
        doc.add(new StringField("country_code", tweetCountryCode, Field.Store.YES));
        String tweetPlaceFullName = place.optString("full_name");
        doc.add(new TextField("full_name", tweetPlaceFullName, Field.Store.YES));

        //Entities
        JSONObject entities = jsonObject.getJSONObject("entities");
        JSONArray hashtagsArray = entities.optJSONArray("hashtags");
        //FIXME : Extract hashtags from text field in hashtag
        JSONArray userMentions = entities.optJSONArray("user_mentions");
        //FIXME : grab name and screen name from the arrays

        //Users
        JSONObject user = jsonObject.getJSONObject("user");
        String userName = user.getString("name");
        doc.add(new StringField("user_name", userName, Field.Store.YES));
        String userScreenName = user.getString("screen_name");
        doc.add(new StringField("user_screen_name", userScreenName, Field.Store.YES));
        Integer userVerified = user.getInt("verified");//Semantically it should be a bool
        doc.add(new IntPoint("verified", userVerified));

        return doc;
    }
}
