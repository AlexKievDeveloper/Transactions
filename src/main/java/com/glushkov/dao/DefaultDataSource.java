package com.glushkov.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DefaultDataSource implements DataSource {

    private final Properties properties = new Properties();

    public Connection getConnection() { //поправить путь на относительный

        try (InputStream propertiesInputStream = new FileInputStream("C:\\Users\\DzeN-BooK\\IdeaProjects" +
                "\\Transaction\\src\\main\\resources\\application.properties")) {

            this.load(propertiesInputStream);

            Connection connection = DriverManager.getConnection(properties.getProperty("jdbc.host"),
                    properties.getProperty("jdbc.user"), properties.getProperty("jdbc.password"));

            return connection;
        }
        catch (IOException | SQLException exception) {
            exception.printStackTrace();
        }
        return null;//ЭТО так себе Заглушка
    }


    private void load(InputStream inputStream) {
        try {
            this.properties.load(inputStream);
        }
        catch (IOException exception) {
            System.out.println("Exception of loading properties. Please check properties and try again.");
            exception.printStackTrace();//Сделать правильные exceptions
        }
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }

}
