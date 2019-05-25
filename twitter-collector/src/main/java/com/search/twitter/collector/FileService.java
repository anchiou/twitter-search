package com.search.twitter.collector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.ONE_GB;
import static org.apache.commons.io.FileUtils.sizeOf;

public class FileService {
    List<String> createdFiles;
    int fileCount;

    FileService() {
        fileCount = 0;
        createdFiles = new ArrayList<String>();
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

    /**
     *
     * @return List of created files' names
     */
    public List<String> getCreatedFiles() {
        return this.createdFiles;
    }

    /**
     *
     * @return number of files created using nextFile
     */
    public int getFileCount() {
        return this.fileCount;
    }

    public Path getPath(String fileName) {
        return Paths.get(System.getProperty("user.home"), "twitter-data", fileName);
    }


    public File newFile(String fileName) {
        String path = Paths.get(System.getProperty("user.home"), "twitter-search", fileName).toString();
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException ioe) {}
        return file;
    }

    /**
     *
     * @return Next file to write to
     */
    public File nextFile(String filePrefix) {
        String fileName = filePrefix + ++fileCount + ".txt";
        String path = Paths.get(System.getProperty("user.home"), "twitter-search", fileName).toString();
        File file = new File(path);
        this.createdFiles.add(file.getName());
        try {
            file.createNewFile();
        } catch (IOException ioe) {}
        return file;
    }
}
