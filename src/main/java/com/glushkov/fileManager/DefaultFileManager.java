package com.glushkov.fileManager;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultFileManager implements FileManager {

    public String readFile(String pathToFile) {
        try {
            return new String(Files.readAllBytes(Paths.get(pathToFile)));
        } catch (IOException ioException) {
            throw new RuntimeException("Please check path to file: " + pathToFile + " and try again.");
        }
    }

    public void saveTo(String pathToFile, String text) {
        try (FileWriter writer = new FileWriter(pathToFile, false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println("Please check path to file: " + pathToFile + " and try again.");
        }
    }
}
