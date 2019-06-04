package com.search.twitter.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
//---- Code used to create indexes ----
//    public void createIndex {
//        Indexer indexer = new Indexer();
//        String std = "std";
//        String en = "en";
//        String ja = "ja";
//        try {
//            indexer.stdIndexer(std);
//        } catch (IOException e) {}
//        try {
//            indexer.stdIndexer(en);
//        } catch (IOException ex) {}
//        try {
//            indexer.stdIndexer(ja);
//        } catch (IOException exc) {}
//    }
}
