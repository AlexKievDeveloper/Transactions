package com.glushkov.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class DefaultDataSource implements DataSource {

    final Properties properties = new Properties();

    public Connection getConnection() {
        ClassLoader classLoader = DefaultDataSource.class.getClassLoader();

        try {
            try (InputStream inputStream = classLoader.getResourceAsStream("application.properties")) {
                this.load(inputStream);
                return DriverManager.getConnection(properties.getProperty("jdbc.host"),
                        properties.getProperty("jdbc.user"), properties.getProperty("jdbc.password"));
            }
        } catch (Exception e){
            throw new RuntimeException("Error while establishing database connection.Please check properties " + "\n" +
                    properties.getProperty("jdbc.host") + "\n " +
                    properties.getProperty("jdbc.user") + "\n " +
                    properties.getProperty("jdbc.password") + "\n and try again.");
        }
    }


    void load(InputStream inputStream) {
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
