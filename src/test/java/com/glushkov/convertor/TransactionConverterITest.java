package com.glushkov.convertor;

import com.glushkov.convertor.TransactionConverter;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import jakarta.xml.bind.JAXBException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionConverterITest {
    Transaction transaction;
    TransactionConverter transactionConverter;

    File jsonFile;
    File csvFile;
    File xmlFile;

    byte[] arrayJSON;
    byte[] arrayCSV;
    byte[] arrayXML;

    @Before
    public void setUp() {
        transaction = new Transaction(7878, 7878, Status.READY, 787878,
                LocalDateTime.of(2020, 4, 5, 4, 5, 4));
        transactionConverter = new TransactionConverter(transaction);

        String stringJSON = "{\"Invoice into\":7878,\"Invoice to\":7878,\"Status of transaction\":\"READY\",\"Amount\":787878.0,\"Created date\":1586048704000}";
        arrayJSON = stringJSON.getBytes();

        String stringCSV = "7878\t7878\tREADY\t787878.0\t1586048704000";
        arrayCSV = stringCSV.getBytes();

        String stringXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<transaction>\n" +
                "    <invoiceInto>7878</invoiceInto>\n" +
                "    <invoiceTo>7878</invoiceTo>\n" +
                "    <status>READY</status>\n" +
                "    <amount>787878.0</amount>\n" +
                "    <date>2020-04-05T04:05:04+03:00</date>\n" +
                "</transaction>";
        arrayXML = stringXML.getBytes();

        jsonFile = new File("TestTransactionFile.json");
        csvFile = new File("TestTransactionFile.csv");
        xmlFile = new File("TestTransactionFile.xml");
    }


    @Test
    public void toJSONTest() throws IOException {
        transactionConverter.toJSON();

        assertTrue(jsonFile.exists());

        byte[] jsonFileBytes = new byte[(int) jsonFile.length()];
        try (InputStream inputStream = new FileInputStream(jsonFile)) {
            inputStream.read(jsonFileBytes);

            for (int i = 0; i < arrayJSON.length; i++) {
                assertEquals(arrayJSON[i], jsonFileBytes[i]);
            }
        }
    }

    @Test
    public void toCSVTest() throws IOException {
        transactionConverter.toCSV();

        assertTrue(csvFile.exists());

        byte[] csvFileBytes = new byte[(int) csvFile.length()];
        try (InputStream inputStream = new FileInputStream(csvFile)) {
            inputStream.read(csvFileBytes);

            for (int i = 0; i < arrayCSV.length; i++) {
                assertEquals(arrayCSV[i], csvFileBytes[i]);
            }
        }
    }

    @Test
    public void toXMLTest() {
        transactionConverter.toXML();

        assertTrue(xmlFile.exists());

        byte[] xmlFileBytes = new byte[(int) xmlFile.length()];
        try (InputStream inputStream = new FileInputStream(xmlFile)) {
            inputStream.read(xmlFileBytes);

            for (int i = 0; i < arrayXML.length; i++) {
                assertEquals(arrayXML[i], xmlFileBytes[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        jsonFile.delete();
        csvFile.delete();
        xmlFile.delete();
    }
}