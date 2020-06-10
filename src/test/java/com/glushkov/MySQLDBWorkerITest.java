package com.glushkov;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MySQLDBWorkerITest {

    Transaction transaction;
    MySQLDBWorker mySQLDBWorker;
    final String HOST = "jdbc:mysql://localhost:3306/transactions?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "RbtdAlexander18!";
    final String INSERT_NEW = "INSERT INTO transactions.mytable(`Into`, `To`, Status, Amount, Date) " +
            "VALUES (?,?,?,?,?)";

    @Before
    public void setUp() throws Exception {
        transaction = new Transaction(101, 1001, Status.TRUE, 11111,
                LocalDateTime.of(2001, 11, 11, 11, 1, 1));

        mySQLDBWorker = new MySQLDBWorker();
        mySQLDBWorker.transactionToSQLtable(transaction);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void transactionToSQLtable() throws SQLException {

        try (Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD)){
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions.mytable");
            resultSet.last();
            int countRawsBefore = resultSet.getRow();
            mySQLDBWorker.transactionToSQLtable(transaction);
            resultSet = statement.executeQuery("SELECT * FROM transactions.mytable");
            resultSet.last();
            int countRawsAfter = resultSet.getRow();
            System.out.println(countRawsAfter);
            statement.close();
            resultSet.close();
            assertEquals(countRawsBefore+1, countRawsAfter);
        }
    }
}