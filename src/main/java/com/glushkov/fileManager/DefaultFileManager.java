package com.glushkov.fileManager;


import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultFileManager implements FileManager {

    public String readFile(String pathToFile) {

        try (FileInputStream fileInputStream = new FileInputStream(pathToFile)) {
            return new String(fileInputStream.readAllBytes());
        } catch (IOException ioException) {
            throw new RuntimeException("Please check path to file: " + pathToFile + " and try again.", ioException);
        }
    }

    public void saveTo(String pathToFile, String text) {
        try (FileWriter writer = new FileWriter(pathToFile, false)) {
            writer.write(text);
        } catch (IOException ioException) {
            throw new RuntimeException("Please check path to file: " + pathToFile + " and try again.", ioException);
        }
    }
}
