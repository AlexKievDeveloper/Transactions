package com.glushkov.dao;


import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class JdbcTransactionDaoITest {

    Transaction transaction;
    JdbcTransactionDao jdbcTransactionDao;

    @Before
    public void setUp() {
        transaction = new Transaction(101, 1001, Status.READY, 11111,
                LocalDateTime.of(2001, 11, 11, 11, 1, 1));

        jdbcTransactionDao = new JdbcTransactionDao();
    }

    @Test
    public void saveTest() throws SQLException {

        DefaultDataSource defaultDataSource = new DefaultDataSource();
        Statement statement = defaultDataSource.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT `Into`, 'To', Status, Amount, Date FROM transactions.mytable");
        resultSet.last();
        int countRawsBefore = resultSet.getRow();

        jdbcTransactionDao.save(transaction);

        resultSet = statement.executeQuery("SELECT `Into`, 'To', Status, Amount, Date FROM transactions.mytable");
        resultSet.last();
        int countRawsAfter = resultSet.getRow();
        statement.close();
        resultSet.close();
        assertEquals(countRawsBefore + 1, countRawsAfter);
    }
}