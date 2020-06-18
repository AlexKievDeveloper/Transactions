package com.glushkov.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DefaultDataSource {

    final Properties properties = new Properties();

    public DefaultDataSource() {
        ClassLoader classLoader = DefaultDataSource.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        this.loadProperties(inputStream);
    }

    void loadProperties(InputStream inputStream) {
        try {
            this.properties.load(inputStream);
        } catch (IOException exception) {
            System.out.println("Exception of loading properties.Please check properties " + "\n" +
                    properties.getProperty("jdbc.host") + "\n " +
                    properties.getProperty("jdbc.user") + "\n " +
                    properties.getProperty("jdbc.password") + "\n and try again.");
            exception.printStackTrace();
        }
    }
}


