package com.glushkov.fileManager;

public interface FileManager {

    String readFile(String pathToFile);

    void saveTo(String pathToFile, String text);
}
