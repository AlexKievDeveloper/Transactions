package com.glushkov;

import com.glushkov.convertor.TransactionConverter;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import jakarta.xml.bind.JAXBException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionConverterITest {
    Transaction transaction;
    TransactionConverter transactionConverter;
    File jsonFile;
    File csvFile;
    File xmlFile;

    @Before
    public void setUp() throws Exception {
        transaction = new Transaction(404, 505, Status.READY, 10000,
                LocalDateTime.of(2020, 04, 10, 15, 34, 22));
        transactionConverter = new TransactionConverter(transaction);

        jsonFile = new File("TestTransactionFile.json");
        csvFile = new File("TestTransactionFile.csv");
        xmlFile = new File("TestTransactionFile.xml");
    }



    @Test
    public void toJSON() throws IOException {
        transactionConverter.toJSON();

        assertTrue(jsonFile.exists());
    }

    @Test
    public void toCSV() throws IOException {
        transactionConverter.toCSV();

        assertTrue(csvFile.exists());
    }

    @Test
    public void toXML() throws JAXBException {
        transactionConverter.toXML();

        assertTrue(xmlFile.exists());
    }

    @After
    public void tearDown() throws Exception {
        jsonFile.delete();
        csvFile.delete();
        xmlFile.delete();
    }
}