
package com.glushkov.dao;


import com.glushkov.convertor.TransactionConverter;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcTransactionDaoITest {
    private String jsonToParse = "[{\n" +
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


    TransactionConverter transactionConverter;
    DefaultDataSource defaultDataSource = new DefaultDataSource();
    JdbcTransactionDao jdbcTransactionDao = new JdbcTransactionDao(defaultDataSource);



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
        List<Transaction> transactions = jdbcTransactionDao.getAll();
        assertFalse(transactions.isEmpty());
        for (Transaction transaction: transactions){
            assertNull(transaction.getId());
            assertNull(transaction.getInvoiceInto());
            assertNull(transaction.getInvoiceTo());
            assertNull(transaction.getAmount());
            assertNull(transaction.getStatus());
        }
    }
}

