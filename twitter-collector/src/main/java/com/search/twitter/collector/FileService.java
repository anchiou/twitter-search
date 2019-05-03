package com.search.twitter.collector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.apache.commons.io.FileUtils.ONE_GB;
import static org.apache.commons.io.FileUtils.sizeOf;

public class FileService {
    int fileCount = 0;

    FileService() {
        fileCount = 0;
    };

    /**
     *
     * @return Next file to write to
     */
    public File nextFile() {
        String fileName = "tweets" + ++fileCount + ".txt";
        String path = Paths.get(System.getProperty("user.home"), "twitter-search", fileName).toString();
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException ioe) {}
        return file;
    }

    public Path getPath(String fileName) {
        return Paths.get(System.getProperty("user.home"), "twitter-data", fileName);
    }

    /**
     *
     * @param file
     * @return true if file size < 1GB, false otherwise
     */
    public boolean checkSize(File file) {
        if (sizeOf(file) < ONE_GB) {
            return true;
        }
        return false;
    }
}
