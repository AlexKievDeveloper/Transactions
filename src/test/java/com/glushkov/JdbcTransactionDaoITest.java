package com.glushkov;

import com.glushkov.dao.JdbcTransactionDao;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

class JdbcTransactionDaoITest {

    Transaction transaction;
    JdbcTransactionDao mySQLDBWorker;
    final String HOST = "jdbc:mysql://localhost:3306/transactions?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "RbtdAlexander18!";
    final String INSERT_NEW = "INSERT INTO transactions.mytable(`Into`, `To`, Status, Amount, Date) " +
            "VALUES (?,?,?,?,?)";

    @Before
    public void setUp() throws Exception {
        transaction = new Transaction(101, 1001, Status.READY, 11111,
                LocalDateTime.of(2001, 11, 11, 11, 1, 1));

        mySQLDBWorker = new JdbcTransactionDao();
        mySQLDBWorker.save(transaction);
    }

    @Test
    public void saveTest() throws SQLException {

        try (Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD)){
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT `Into`, 'To', Status, Amount, Date FROM transactions.mytable");
            resultSet.last();
            int countRawsBefore = resultSet.getRow();
            mySQLDBWorker.save(transaction);
            resultSet = statement.executeQuery("SELECT `Into`, 'To', Status, Amount, Date FROM transactions.mytable");
            resultSet.last();
            int countRawsAfter = resultSet.getRow();
            System.out.println(countRawsAfter);
            statement.close();
            resultSet.close();
            assertEquals(countRawsBefore+1, countRawsAfter);
        }
    }
}