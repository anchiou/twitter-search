package com.search.twitter.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {

    public void searcher(String lang) throws IOException {
        //Assuming they are using the STD Index// FIXME: Add options for other indicies
        Path path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "StdIndex");
        Analyzer analyzer = new StandardAnalyzer();

        Directory directory = FSDirectory.open(path);
        DirectoryReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //full_name means city full name
        String[] fields = {"created_at", "id_str", "text", "in_reply_to_screen_name",
                "reply_count", "retweet_count", "favorite_count", "lang", "coordinates",
                "country", "country_code", "full_name", "hashtags", "mention_names",
                "mention_screen_names", "titles", "user_name", "user_screen_name", "verified"};

        Map<String, Float> boosts = new HashMap<>();
        //FIXME: Here are the fields and their weights, Should be adjusted
        boosts.put(fields[0], 1.0f); // created_at
        boosts.put(fields[1], 0.5f); // id_str
        boosts.put(fields[2], 1.0f); // text
        boosts.put(fields[3], 0.5f); // in_reply_to_screen_name
        boosts.put(fields[4], 1.0f); // reply count
        boosts.put(fields[5], 0.5f); // retweet_count
        boosts.put(fields[6], 1.0f); // favorite count
        boosts.put(fields[7], 0.5f); // lang
        boosts.put(fields[8], 1.0f); // coordinates
        boosts.put(fields[9], 0.5f); // country
        boosts.put(fields[10], 1.0f); // country_code
        boosts.put(fields[11], 0.5f); // full_name (of the place)
        boosts.put(fields[12], 1.0f); // hashtags
        boosts.put(fields[13], 0.5f); // mention_names
        boosts.put(fields[14], 1.0f); // mention_screen_names
        boosts.put(fields[15], 0.5f); // titles (of the urls)
        boosts.put(fields[16], 1.0f); // user_name
        boosts.put(fields[17], 0.5f); // user_screen_name
        boosts.put(fields[18], 1.0f); // verified

        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer, boosts);
        try {
            //FIXME: THis part needs to come from user input
            Query query = parser.parse("UCR");
            // Query query = parser.parse("UCR discussion");
            // QueryParser parser = new QueryParser("content", analyzer);
            // Query query = parser.parse("(title:ucr)^1.0 (content:ucr)^0.5");
            System.out.println(query.toString());
            int topHitCount = 100;
            ScoreDoc[] hits = indexSearcher.search(query, topHitCount).scoreDocs;

            // Iterate through the results: FIXME:This needs to be  changed to match our needs
            for (int rank = 0; rank < hits.length; ++rank) {
                Document hitDoc = indexSearcher.doc(hits[rank].doc);
                System.out.println((rank + 1) + " (score:" + hits[rank].score + ") --> " +
                        hitDoc.get("title") + " - " + hitDoc.get("content"));
                // System.out.println(indexSearcher.explain(query, hits[rank].doc));
            }
            indexReader.close();
            directory.close();
        } catch (ParseException p) {}

    }
}

