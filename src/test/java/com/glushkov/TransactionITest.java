package com.glushkov;


import com.glushkov.convertor.TransactionConverter;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


public class TransactionITest {
    Transaction transaction;
    TransactionConverter transactionConverter;
    File jsonFile;


    @Before
    public void setUp() throws Exception {
        transaction = new Transaction(404, 505, Status.READY, 10000,
                LocalDateTime.of(2020, 04, 10, 15, 34, 22));

        transactionConverter = new TransactionConverter(transaction);
        transactionConverter.toJSON();

        jsonFile = new File("TestTransactionFile.json");
    }

    @After
    public void tearDown() throws Exception {
        jsonFile.delete();
    }

    @Test
    public void JSONtoTransaction() throws IOException {//НЕТУ ТОЛКОМ ТЕСТА
        Transaction fromJsonTransaction = Transaction.JSONtoTransaction("TestTransactionFile.json");
    }
}

