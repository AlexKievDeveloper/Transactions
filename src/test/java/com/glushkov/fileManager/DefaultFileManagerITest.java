package com.glushkov.fileManager;

import com.glushkov.fileManager.DefaultFileManager;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultFileManagerITest {
    String jsonExpected = "[{\r\n" +
            "\"invoiceInto\": 1,\r\n" +
            "\"invoiceTo\": 2,\r\n" +
            "\"status\": \"READY\",\r\n" +
            "\"amount\": 1000.0\r\n" +
            "},{\r\n" +
            "\"invoiceInto\": 2,\r\n" +
            "\"invoiceTo\": 3,\r\n" +
            "\"status\": \"READY\",\r\n" +
            "\"amount\": 200.0\r\n" +
            "}]";

    DefaultFileManager defaultFileManager = new DefaultFileManager();
    boolean created = new File("TestJsonFileSecond.json").createNewFile();

    public DefaultFileManagerITest() throws IOException {
    }

    @Test
    public void readFile() {
        String jsonFromFile = defaultFileManager.readFile("TransactionFile.json");
        defaultFileManager.saveTo("TransactionFile.json", jsonExpected);

        assertEquals(jsonExpected, jsonFromFile);
    }

    @Test
    public void saveTo() {
        defaultFileManager.saveTo("TestJsonFileSecond.json", jsonExpected);
        String jsonFromFile = defaultFileManager.readFile("TestJsonFileSecond.json");

        assertEquals(jsonExpected, jsonFromFile);
    }

    @After
    public void after(){
        File TestJsonFileSecond = new File("TestJsonFileSecond.json");
        assertTrue(TestJsonFileSecond.delete());
    }
}