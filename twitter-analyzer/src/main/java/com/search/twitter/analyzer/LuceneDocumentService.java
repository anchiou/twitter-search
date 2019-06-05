package com.search.twitter.analyzer;

import org.apache.lucene.document.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class LuceneDocumentService {
    public Document getDocument(String string) {
        Document doc = new Document();
        //Main JSON File from line

        //System.out.println("Created Document");

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

        int replyCount = jsonObject.getInt("reply_count");
        doc.add(new StringField("reply_count", Integer.toString(replyCount), Field.Store.YES));
        //System.out.println("ret_cnt: " + replyCount.toString());
//        doc.add(new IntPoint("reply_count", replyCount));
//        doc.add(new StoredField("reply_count", replyCount));

        int retweetCount = jsonObject.getInt("retweet_count");
        doc.add(new StringField("retweet_count", Integer.toString(retweetCount), Field.Store.YES));
//        //System.out.println("ret_cnt: " + retweetCount.toString());
//        doc.add(new IntPoint("retweet_count", retweetCount));
//        doc.add(new StoredField("retweet_count", retweetCount));

        int favoriteCount = jsonObject.getInt("favorite_count");
        doc.add(new StringField("favorite_count", Integer.toString(favoriteCount), Field.Store.YES));

//        Integer favoriteCount = jsonObject.optInt("favorite_count", 0);
//        //System.out.println("fav_cnt: " + favoriteCount.toString());
//        doc.add(new IntPoint("favorite_count", favoriteCount));
//        doc.add(new StoredField("favorite_count", favoriteCount));

        String lang = jsonObject.optString("lang","en");
        doc.add(new StringField("lang", lang, Field.Store.YES));

        //System.out.println("Outside Fields Done");

        //Coordinates
        JSONObject coordinates = jsonObject.optJSONObject("coordinates");
        if (coordinates != null) {
            JSONArray tweetCoordinates = coordinates.optJSONArray("coordinates");
            Double longitude = tweetCoordinates.optDouble(0);
            Double latitude = tweetCoordinates.optDouble(1);
            doc.add(new LatLonDocValuesField("coordinates", latitude, longitude));
        }
        else {
            doc.add(new LatLonDocValuesField("coordinates", 0.0, 0.0));
        }

        //System.out.println("Coordinates Fields Done");

        //Place
        JSONObject place = jsonObject.optJSONObject("place");
        if (place != null) {
            String tweetCountry = place.optString("country", null);
            doc.add(new StringField("country", tweetCountry, Field.Store.YES));
            String tweetCountryCode = place.optString("country_code", null);
            doc.add(new StringField("country_code", tweetCountryCode, Field.Store.YES));
            String tweetPlaceFullName = place.optString("full_name", null);
            doc.add(new TextField("full_name", tweetPlaceFullName, Field.Store.YES));
        }

        //System.out.println("Place Fields Done");

        //Entities
        JSONObject entities = jsonObject.getJSONObject("entities");
        JSONArray hashtagsArray = entities.optJSONArray("hashtags");
        if (hashtagsArray != null) {
            //Storing all hashtags as a single Text Field
            String hashtags = "";
            for (int i = 0; i < hashtagsArray.length(); i++) {
                JSONObject hashtag = hashtagsArray.optJSONObject(i);
                String hashtagText = hashtag.getString("text");
                hashtags = hashtags + " " + hashtagText;
            }
            doc.add(new TextField("hashtags", hashtags, Field.Store.YES));
        }
        else {
            doc.add(new TextField("hashtags", null, Field.Store.YES));
        }

        //System.out.println("Hashtag Field done");

        JSONArray userMentions = entities.optJSONArray("user_mentions");
        if (userMentions != null) {
            //Storing all user mention names as a single Text Field
            //Storing all user mention screen names as a single text field
            String mentionNames = "";
            String mentionScreenNames = "";
            for (int i = 0; i < userMentions.length(); i++) {
                JSONObject mention = userMentions.optJSONObject(i);
                String mentionName = mention.optString("name", "Username Unavailable");
                mentionNames = mentionNames + " " + mentionName;
                String mentionScreenName = mention.optString("screen_name");
                mentionScreenNames = mentionScreenNames + " " + mentionScreenName;
            }
            doc.add(new TextField("mention_names", mentionNames, Field.Store.YES));
            doc.add(new TextField("mention_screen_names", mentionScreenNames, Field.Store.YES));
        }

        //System.out.println("Mention Fields Done");

        //Titles
        JSONArray titleArray = jsonObject.optJSONArray("titles");
        if (titleArray != null) {
            //Storing all titles as a single Text Field
            String titles = "";
            for (int i = 0; i < titleArray.length(); i++) {
                String title = hashtagsArray.optString(i);
                titles = titles + " " + title;
            }
            doc.add(new TextField("titles", titles, Field.Store.YES));
        }

        //System.out.println("Title Field Done");

        //User
        JSONObject user = jsonObject.getJSONObject("user");
        String userName = user.getString("name");
        doc.add(new TextField("user_name", userName, Field.Store.YES));
        String userScreenName = user.getString("screen_name");
        doc.add(new StringField("user_screen_name", userScreenName, Field.Store.YES));
        String userVerified = Boolean.toString(user.getBoolean("verified"));//Semantically it should be a bool check "true"/"false"
        doc.add(new StringField("verified", userVerified, Field.Store.YES));

        //System.out.println("User Fields Done \n ... Returning...");

        return doc;
    }
}
