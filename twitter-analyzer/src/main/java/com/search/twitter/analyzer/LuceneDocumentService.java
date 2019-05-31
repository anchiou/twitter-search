package com.search.twitter.analyzer;

import org.apache.lucene.document.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

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

        Integer replyCount = jsonObject.getInt("reply_count");
        doc.add(new IntPoint("reply_count", replyCount));

        Integer retweetCount = jsonObject.getInt("retweet_count");
        doc.add(new IntPoint("retweet_count", retweetCount));

        Integer favoriteCount = jsonObject.optInt("favorite_count",0);
        doc.add(new IntPoint("favorite_count", favoriteCount));

        String lang = jsonObject.optString("lang","en");
        doc.add(new StringField("lang", lang, Field.Store.YES));

        //Coordinates
        JSONObject coordinates = jsonObject.optJSONObject("coordinates");
        JSONArray tweetCoordinates = coordinates.optJSONArray("coordinates");
        Double longitude = tweetCoordinates.optDouble(0);
        Double latitude = tweetCoordinates.optDouble(1);
        doc.add(new LatLonDocValuesField("coordinates", latitude, longitude));

        //Place
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
        //Storing all hashtags as a single Text Field
        String hashtags = "";
        for (int i = 0; i < hashtagsArray.length(); i++)
        {
            JSONObject hashtag = hashtagsArray.optJSONObject(i);
            String hashtagText = hashtag.getString("text");
            hashtags = hashtags + " " + hashtagText;
        }
        doc.add(new TextField("hashtags", hashtags, Field.Store.YES));

        JSONArray userMentions = entities.optJSONArray("user_mentions");
        //Storing all user mention names as a single Text Field
        //Storing all user mention screen names as a single text field
        String mentionNames = "";
        String mentionScreenNames = "";
        for (int i = 0; i < userMentions.length(); i++)
        {
            JSONObject mention = userMentions.optJSONObject(i);
            String mentionName = mention.getString("name");
            mentionNames = mentionNames + " " + mentionName;
            String mentionScreenName = mention.optString("screen_name");
            mentionScreenNames = mentionScreenNames + " " + mentionScreenName;
        }
        doc.add(new TextField("mention_names", mentionNames, Field.Store.YES));
        doc.add(new TextField("mention_screen_names", mentionScreenNames, Field.Store.YES));

        //User
        JSONObject user = jsonObject.getJSONObject("user");
        String userName = user.getString("name");
        doc.add(new TextField("user_name", userName, Field.Store.YES));
        String userScreenName = user.getString("screen_name");
        doc.add(new StringField("user_screen_name", userScreenName, Field.Store.YES));
        String userVerified = user.getString("verified");//Semantically it should be a bool check "true"/"false"
        doc.add(new StringField("verified", userVerified, Field.Store.YES));

        return doc;

    }
}
