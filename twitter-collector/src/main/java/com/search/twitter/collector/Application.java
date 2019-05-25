package com.search.twitter.collector;

import java.io.*;

import twitter4j.*;

public class Application {
    public static void main(String[] args) throws TwitterException {
        FileService fileService = new FileService();
        DataService dataService = new DataService();

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener statusListener = new StatusListener() {
            File outputFile = fileService.nextFile("tweets");

            public void onStatus(Status status) {
                if (fileService.checkSize(outputFile)) {
                    try {
                        String json = TwitterObjectFactory.getRawJSON(status);
                        storeJSON(json, outputFile.getName());
                        System.out.println(outputFile.getName() + ": tweets stored successfully.");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        System.out.println("Failed to store tweets: " + ioe.getMessage());
                    }
                } else if (fileService.getFileCount() >= 5) { // Each file is ~1GB, so 5 files == 5GB
                    for (String file : fileService.getCreatedFiles()) {
                        try {
                            dataService.extractTitles(file);
                        } catch (IOException e) {
                            System.out.println(file + ": could not extract titles from file");
                        }
                    }
                    twitterStream.cleanUp();
                    twitterStream.shutdown();
                } else {
                    outputFile = fileService.nextFile("tweets");
                }

            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onScrubGeo(long userId, long upToStatusId) {
            }

            public void onStallWarning(StallWarning warning) {
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        twitterStream.addListener(statusListener);
        twitterStream.sample();
    }

    private static void storeJSON(String rawJSON, String fileName) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fileName, true);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            bw.write(rawJSON);
            bw.newLine();
            bw.flush();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ignore) {}
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException ignore) {}
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignore) {}
            }
        }
    }
}