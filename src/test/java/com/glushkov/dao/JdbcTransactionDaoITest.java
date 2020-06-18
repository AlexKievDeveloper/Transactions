
package com.glushkov.dao;


import com.glushkov.convertor.TransactionConverter;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JdbcTransactionDaoITest {
    String jsonToParse = "[{\n" +
            "        \"invoiceInto\": 101,\n" +
            "        \"invoiceTo\": 2,\n" +
            "        \"status\": \"READY\",\n" +
            "        \"amount\": 1000.0\n" +
            "        },{\n" +
            "        \"invoiceInto\": 202,\n" +
            "        \"invoiceTo\": 3,\n" +
            "        \"status\": \"READY\",\n" +
            "        \"amount\": 200.0\n" +
            "        }]";

    String host;
    String user;
    String password;

    TransactionConverter transactionConverter;
    DefaultDataSource defaultDataSource;
    JdbcTransactionDao jdbcTransactionDao;

    @Before
    public void setUp() {
        transactionConverter = new TransactionConverter();
        defaultDataSource = new DefaultDataSource();
        jdbcTransactionDao = new JdbcTransactionDao(defaultDataSource);

        host = defaultDataSource.properties.getProperty("jdbc.host");
        user = defaultDataSource.properties.getProperty("jdbc.user");
        password = defaultDataSource.properties.getProperty("jdbc.password");
    }

    @Test
    public void saveTest() {
        List<Transaction> list = transactionConverter.parseJson(jsonToParse);

        jdbcTransactionDao.save(list);

        List<Transaction> dbListAfterSaving = jdbcTransactionDao.getAll();
        for (Transaction transaction : list) {
            assertTrue(dbListAfterSaving.contains(transaction));
        }
    }

    @Test
    public void getAllTest() throws SQLException {
        Connection connection = DriverManager.getConnection(host, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("SELECT id, invoiceinto, invoiceto, status, amount\n" +
                "\tFROM public.transactions_table;");

        List<Transaction> dbAllList = jdbcTransactionDao.getAll();


        for (int i = 0; i < resultSet.getRow(); i++) {
            assertEquals(dbAllList.get(i).getInvoiceInto(), resultSet.getInt("invoiceinto"));
            assertEquals(dbAllList.get(i).getInvoiceTo(), resultSet.getInt("invoiceto"));
            assertEquals(dbAllList.get(i).getStatus(), Status.valueOf(resultSet.getString("status")));
            assertEquals(dbAllList.get(i).getAmount(), resultSet.getDouble("amount"));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}

