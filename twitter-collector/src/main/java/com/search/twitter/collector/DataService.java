package com.search.twitter.collector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.*;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataService {
    public static void extractTitles(String filename) throws IOException {
        FileService fileService = new FileService();
        File outputFile = fileService.newFile("processed_" + filename);

        try {
            FileOutputStream fos = new FileOutputStream(outputFile.getName(), true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            final BufferedWriter bw = new BufferedWriter(osw);

            // Read File
            Path path = Paths.get(System.getProperty("user.home"), "twitter-search", filename);
            try {
                Stream<String> stream = Files.lines(path);
                stream.forEach(line -> {
                    String output = "";
                    //get JSON Object from stream
                    JSONObject jsonObject = new JSONObject(line);
                    JSONObject entityJsonObject = jsonObject.getJSONObject("entities");
                    JSONArray urlsJsonArray = entityJsonObject.optJSONArray("urls");

                    if (urlsJsonArray.length() != 0) {
                        //create array for the titles to be imputed
                        String[] titles = new String[urlsJsonArray.length()];
                        //Read in all of the URLS of the tweet
                        for (int i = 0; i < urlsJsonArray.length(); ++i) {
                            JSONObject item = urlsJsonArray.getJSONObject(i);
                            String url = item.optString("expanded_url", "");

                            if (url != "") {
                                //Get the titles of the urls using Jsoup
                                try {
                                    Document doc = Jsoup.connect(url).get();
                                    String title = doc.title();
                                    titles[i] = title;
                                } catch (IOException e) {}
                            }
                        }
                        //Add the titles to the original JSON Object
                        jsonObject.put("titles", titles);
                    }
                    output = jsonObject.toString();
                    try {
                        bw.write(output);
                        bw.newLine();
                    } catch (IOException e) {}
                });
            } catch (IOException e) {}
            bw.flush();
            try {
                bw.close();
            } catch (IOException ignore) {}
            try {
                osw.close();
            } catch (IOException ignore) {}
            try {
                fos.close();
            } catch (IOException ignore) {}
        } finally {}
    }
}
