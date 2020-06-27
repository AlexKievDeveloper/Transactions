package com.glushkov.fileManager;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultFileManagerITest {
    private String jsonExpected = "[{\n" +
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

    DefaultFileManager defaultFileManager = new DefaultFileManager();
    private boolean created = new File("testResources/TestJsonFileSecond.json").createNewFile();

    public DefaultFileManagerITest() throws IOException {
    }

    @Test
    public void readFileTest() {
        String jsonFromFile = defaultFileManager.readFile("testResources/TransactionFile.json");
        defaultFileManager.saveTo("testResources/TransactionFile.json", jsonExpected);

        assertEquals(jsonExpected, jsonFromFile);
    }

    @Test
    public void saveToTest() {
        defaultFileManager.saveTo("testResources/TestJsonFileSecond.json", jsonExpected);
        String jsonFromFile = defaultFileManager.readFile("testResources/TestJsonFileSecond.json");

        assertEquals(jsonExpected, jsonFromFile);
    }

    @After
    public void after() {
        File TestJsonFileSecond = new File("testResources/TestJsonFileSecond.json");
        assertTrue(TestJsonFileSecond.delete());
    }
}