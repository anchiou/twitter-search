package com.search.twitter.analyzer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Indexer indexer = new Indexer();
        String std = "std";
        String en = "en";
        String ja = "ja";
        try {
            indexer.stdIndexer(std);
        } catch (IOException e) {}
        try {
            indexer.stdIndexer(en);
        } catch (IOException ex) {}
        try {
            indexer.stdIndexer(ja);
        } catch (IOException exc) {}
    }
}
