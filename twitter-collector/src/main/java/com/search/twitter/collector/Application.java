package com.search.twitter.collector;

import twitter4j.*;

import java.nio.file.Path;

public class Application {
    public static void main(String[] args) throws TwitterException {
//        FileService fileService = new FileService();
//
//        String fileName;
//        Path path;

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {
            public void onStatus(Status status) {
//                System.out.println("@" + status.getUser().getScreenName() + " - " +
//                System.out.println("---------------------" + status.toString());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
//                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onStallWarning(StallWarning warning) {
//                System.out.println("Got stall warning:" + warning);
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        });

//        for (int i = 1; i <= 5; ++i) {
//            fileName = "tweets" + i + ".txt";
//            path = fileService.getPath(fileName);
//            try {
//                Files.write(path, );
//            } catch (IOException e) {
//                System.out.println("FileService.writeToFile: Could not create output file.");
//            }
//        }

        twitterStream.sample();
    }
}