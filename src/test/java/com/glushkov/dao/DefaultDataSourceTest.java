package com.glushkov.dao;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DefaultDataSourceTest {

    @Test
    public void getConnectionTest() throws SQLException {
        DefaultDataSource defaultDataSource = new DefaultDataSource();
        Statement statement = defaultDataSource.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT 'To' from transactions.mytable where id = 1");
        assertTrue(resultSet.last());
        assertEquals(1, resultSet.getRow());
        resultSet.close();
        statement.close();
    }

    @Test
    public void loadTest(){
        DefaultDataSource defaultDataSource = new DefaultDataSource();
        ClassLoader classLoader = DefaultDataSource.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");

        defaultDataSource.load(inputStream);

        assertEquals("root", defaultDataSource.properties.getProperty("jdbc.user"));
    }
}