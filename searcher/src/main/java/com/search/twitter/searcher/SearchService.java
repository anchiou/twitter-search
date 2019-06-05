package com.search.twitter.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.expressions.*;
import org.apache.lucene.expressions.js.JavascriptCompiler;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.function.FunctionScoreQuery;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {
    public void searcher(String userQuery, String lang) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "StdIndex");
        if (lang == "en"){
            analyzer = new EnglishAnalyzer();
            path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "EnIndex");
        } else if (lang == "ja"){
            analyzer = new CJKAnalyzer();
            path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "CJKIndex");
        }

        Directory directory = FSDirectory.open(path);
        DirectoryReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        String[] fields = {"text", "in_reply_to_screen_name", "hashtags", "mention_names",
                "mention_screen_names", "titles", "user_name", "user_screen_name"};

        Map<String, Float> boosts = new HashMap<>();
        boosts.put(fields[0], 1.7f); // text
        boosts.put(fields[1], 1.1f); // in_reply_to_screen_name
        boosts.put(fields[2], 1.3f); // hashtags
        boosts.put(fields[3], 1.2f); // mention_names
        boosts.put(fields[4], 1.2f); // mention_screen_names
        boosts.put(fields[5], 1.5f); // titles (of the urls)
        boosts.put(fields[6], 1.7f); // user_name
        boosts.put(fields[7], 1.75f); // user_screen_name

        try {
//            Expression expr = JavascriptCompiler.compile("_score + ln(retweet_count) + ln(favorite_count) ");
//
//        SimpleBindings bindings = new SimpleBindings();
//        bindings.add(new SortField("_score", SortField.Type.SCORE));
//        bindings.add(new SortField("retweet_count", SortField.Type.INT));
//        bindings.add(new SortField("favorite_count", SortField.Type.INT));

        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer, boosts);

        // Queries are boosted, then filtered by language, then modified by expression
            Query boostedQuery = parser.parse(userQuery);
//            Query filteredQuery = new BooleanQuery.Builder()
//                    .add(boostedQuery, BooleanClause.Occur.MUST)
//                    .add(new TermQuery(new Term("lang", lang)), BooleanClause.Occur.FILTER)
//                    .build();
//            FunctionScoreQuery functionQuery = new FunctionScoreQuery(filteredQuery, expr.getDoubleValuesSource(bindings));
//            Query matchQuery = new TermQuery(new Term("verified", "true"));
//            Query finalQuery = functionQuery.boostByQuery(functionQuery, matchQuery, 1.1f);
            Query finalQuery = boostedQuery;
//            System.out.println(finalQuery.toString());

            int numTopHits = 25;
            ScoreDoc[] hits = indexSearcher.search(finalQuery, numTopHits).scoreDocs;

            // FIXME: Iterate through the results
            for (int rank = 0; rank < hits.length; ++rank) {
                Document hitDoc = indexSearcher.doc(hits[rank].doc);
                System.out.println((rank + 1) + " (score:" + hits[rank].score + ") --> "
                        + hitDoc.get("user_name") + "/n"
                        + hitDoc.get("user_screen_name") + "/n");
                // System.out.println(indexSearcher.explain(query, hits[rank].doc));
            }
            indexReader.close();
            directory.close();
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            System.out.println("SearchService.parser.parse: ParseException");
        }
//        catch (ParseException e) {
//            System.out.println("SearchService.expr.compile: ParseException");
//        }
    }
}

