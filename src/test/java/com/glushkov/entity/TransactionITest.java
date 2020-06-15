package com.glushkov.entity;


import com.glushkov.convertor.TransactionConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


public class TransactionITest {
    Transaction transaction;
    TransactionConverter transactionConverter;
    File jsonFile;


    @Before
    public void setUp() {
        transaction = new Transaction(404, 505, Status.READY, 10000,
                LocalDateTime.of(2020, 04, 10, 15, 34, 22));

        transactionConverter = new TransactionConverter(transaction);
        transactionConverter.toJSON();

        jsonFile = new File("TestTransactionFile.json");
    }

    @After
    public void tearDown() {
        jsonFile.delete();
    }

/*    @Test
    public void JSONtoTransactionTest() {//НЕТУ ТОЛКОМ ТЕСТА
        Transaction fromJsonTransaction = Transaction.JSONtoTransaction("TestTransactionFile.json");
        assertEquals(transaction, fromJsonTransaction);
    }*/
}

