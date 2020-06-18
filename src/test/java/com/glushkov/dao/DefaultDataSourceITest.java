
package com.glushkov.dao;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class DefaultDataSourceITest {
    DefaultDataSource defaultDataSource = new DefaultDataSource();
    ClassLoader classLoader = DefaultDataSource.class.getClassLoader();

    String host = "jdbc:postgresql://localhost:5432/Transactions";
    String user = "postgres";
    String password = "RbtdAlexander18!";

    @Test
    public void loadTest() {
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");

        defaultDataSource.loadProperties(inputStream);

        assertEquals(host, defaultDataSource.properties.getProperty("jdbc.host"));
        assertEquals(user, defaultDataSource.properties.getProperty("jdbc.user"));
        assertEquals(password, defaultDataSource.properties.getProperty("jdbc.password"));
    }
}
