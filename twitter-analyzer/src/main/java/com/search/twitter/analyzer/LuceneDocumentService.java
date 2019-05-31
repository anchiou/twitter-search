package com.search.twitter.analyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
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
        String created_at = jsonObject.getString("created_at");
        doc.add(new TextField("created_at", created_at));
        String tweet_id_str = jsonObject.getString("id_str");
        doc.add(new TextField("id_str", tweet_id_str));
        String tweet_text = jsonObject.getString("text");
        doc.add(new TextField("text", tweet_text));
        String in_reply_to_screen_name = jsonObject.optString("in_reply_to_screen_name");
        doc.add(new TextField("in_reply_to_screen_name", in_reply_to_screen_name));
        Integer retweet_count = jsonObject.getInt("retweet_count");
        doc.add(new )
        JSONObject favorite_count = jsonObject.getJSONObject("favorite_count");
        JSONObject reply_count = jsonObject.getJSONObject("reply_count");
        JSONObject lang = jsonObject.getJSONObject("lang");


        //Coordinates and Place
        JSONObject coordinates = jsonObject.getJSONObject("coordinates");
        JSONArray tweet_coordinates = coordinates.getJSONArray("coordinates");
        JSONObject place = jsonObject.getJSONObject("place");
        JSONObject tweet_country = place.getJSONObject("country");
        JSONObject tweet_country_code = place.getJSONObject("country_code");
        JSONObject tweet_place_full_name = place.getJSONObject("full_name");

        //Entities
        JSONObject entities = jsonObject.getJSONObject("entities");
        JSONArray hashtags_array = entities.optJSONArray("hashtags");
        //FIXME : Extract hashtags from text field in hashtag
        JSONArray user_mentions = entities.optJSONArray("user_mentions");
        //FIXME : grab name and screen name from the arrays

        //Users
        JSONObject user = jsonObject.getJSONObject("user");
        JSONObject user_name = user.getJSONObject("name")
        JSONObject user_screen_name = user.getJSONObject("screen_name");
        JSONObject user_verified = user.getJSONObject("verified");



        doc.add(Field.UnStired("body", bodyText));
        return doc;

    }
}
