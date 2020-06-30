
package com.glushkov.dao;


import com.glushkov.convertor.TransactionConverter;
import com.glushkov.entity.Transaction;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcTransactionDaoITest {
    private String jsonToParse = "[{\n" +
            "\"invoiceInto\": 1,\n" +
            "\"invoiceTo\": 2,\n" +
            "\"status\": \"READY\",\n" +
            "\"amount\": 1000.0,\n" +
            "\"date\": \"2010-01-01T12:00:00+01:00\"\n" +
            "},{\n" +
            "\"invoiceInto\": 2,\n" +
            "\"invoiceTo\": 3,\n" +
            "\"status\": \"READY\",\n" +
            "\"amount\": 200.0,\n" +
            "\"date\": \"2010-01-01T12:00:00+01:00\"\n" +
            "}]";

    TransactionConverter transactionConverter = new TransactionConverter();
    DefaultDataSource defaultDataSource = new DefaultDataSource();
    JdbcTransactionDao jdbcTransactionDao = new JdbcTransactionDao(defaultDataSource);

    @Test
    public void saveTest() {
        List<Transaction> list = transactionConverter.parseJson(jsonToParse);

        List<Transaction> dbListBeforeSaving = jdbcTransactionDao.getAll();
        int sizeBefore = dbListBeforeSaving.size();

        jdbcTransactionDao.save(list);

        List<Transaction> dbListAfterSaving = jdbcTransactionDao.getAll();
        assertEquals(sizeBefore + list.size(), dbListAfterSaving.size());

        for (int i = dbListAfterSaving.size() - list.size(); i < dbListAfterSaving.size(); i++) {
            assertNotNull(dbListAfterSaving.get(i).getId());
            assertNotNull(dbListAfterSaving.get(i).getInvoiceInto());
            assertNotNull(dbListAfterSaving.get(i).getInvoiceTo());
            assertNotNull(dbListAfterSaving.get(i).getStatus());
            assertNotNull(dbListAfterSaving.get(i).getAmount());
            assertNotNull(dbListAfterSaving.get(i).getDate());
        }
    }

    @Test
    public void getAllTest() throws SQLException {
        List<Transaction> transactions = jdbcTransactionDao.getAll();
        assertFalse(transactions.isEmpty());
        for (Transaction transaction : transactions) {
            assertNotNull(transaction.getId());
            assertNotNull(transaction.getInvoiceInto());
            assertNotNull(transaction.getInvoiceTo());
            assertNotNull(transaction.getStatus());
            assertNotNull(transaction.getAmount());
            assertNotNull(transaction.getDate());
        }
    }
}

