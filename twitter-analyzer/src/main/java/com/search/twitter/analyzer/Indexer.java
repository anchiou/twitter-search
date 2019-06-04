package com.search.twitter.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Indexer {
    public void stdIndexer(String lang) throws IOException {
        //Default std analyzer and path
        Analyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "StdIndex");
        if (lang == "en"){
            analyzer = new EnglishAnalyzer();
            path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "EnIndex");
        }
        else if (lang == "ja"){
            analyzer = new CJKAnalyzer();
            path = Paths.get(System.getProperty("user.home"), "twitter-search/twitter-data", "CJKIndex");
        }

        Directory directory = FSDirectory.open(path);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexwriter = new IndexWriter(directory, config);
        LuceneDocumentService luceneDocumentService = new LuceneDocumentService();

        String filenames[] = {"processed_tweets1.txt",
                "processed_tweets2.txt",
                "processed_tweets3.txt",
                "processed_tweets4.txt",
                "processed_tweets5.txt"};
        System.out.println("Initialized");
        for (String filename : filenames) {

            Path filePath = Paths.get(System.getProperty("user.home"), "twitter-search", filename);

            try{
                //System.out.println("Trying to open file");

                Stream<String> stream = Files.lines(filePath);

                //System.out.println("Opened File");

                stream.forEach(line -> {
                    Document doc = luceneDocumentService.getDocument(line);
                    try {
                        indexwriter.addDocument(doc);
                        //System.out.println("Writing");
                    } catch (IOException ex) {System.out.println("Error in IndexWriter"); }
                });
            } catch (StreamCorruptedException s) {System.out.println("Error in stream");}
        }
        indexwriter.close();
    }
}
