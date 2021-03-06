
package com.glushkov.convertor;

import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;
import com.glushkov.fileManager.DefaultFileManager;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionConverterITest {
    private String jsonToParse = "[{\n" +
            "\"invoiceInto\": 1,\n" +
            "\"invoiceTo\": 2,\n" +
            "\"status\": \"READY\",\n" +
            "\"amount\": 1000.0,\n" +
            "\"createDate\": \"2010-01-01\"\n" +
            "},{\n" +
            "\"invoiceInto\": 2,\n" +
            "\"invoiceTo\": 3,\n" +
            "\"status\": \"READY\",\n" +
            "\"amount\": 200.0,\n" +
            "\"createDate\": \"2010-01-01\"\n" +
            "}]";

    private String xmlExpected = "<transactions>\n" +
            "    <transaction>\n" +
            "        <id>0</id>\n" +
            "        <invoiceInto>1</invoiceInto>\n" +
            "        <invoiceTo>2</invoiceTo>\n" +
            "        <status>READY</status>\n" +
            "        <amount>1000.0</amount>\n" +
            "        <createDate>2010-01-01</createDate>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "        <id>0</id>\n" +
            "        <invoiceInto>2</invoiceInto>\n" +
            "        <invoiceTo>3</invoiceTo>\n" +
            "        <status>READY</status>\n" +
            "        <amount>200.0</amount>\n" +
            "        <createDate>2010-01-01</createDate>\n" +
            "    </transaction>\n" +
            "</transactions>";

    private String csvExpected = "0\t1\t2\tREADY\t1000.0\t2010-01-01\n" +
            "0\t2\t3\tREADY\t200.0\t2010-01-01\n";

    TransactionConverter transactionConverter;
    DefaultFileManager defaultFileManager;

    @Before
    public void setUP() {
        transactionConverter = new TransactionConverter();
        defaultFileManager = new DefaultFileManager();
    }


    @Test
    public void parseJsonWithArrayOfEntitiesTest() {
        List<Transaction> list = transactionConverter.parseJson(jsonToParse);

        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getInvoiceInto());
        assertEquals(2, list.get(0).getInvoiceTo());
        assertEquals(Status.READY, list.get(0).getStatus());
        assertEquals(1000.0d, list.get(0).getAmount(), 1);
        assertEquals(LocalDate.of(2010, 1, 1), list.get(0).getDate());
        assertEquals(2, list.get(1).getInvoiceInto());
        assertEquals(3, list.get(1).getInvoiceTo());
        assertEquals(Status.READY, list.get(1).getStatus());
        assertEquals(200.0d, list.get(1).getAmount(), 1);
        assertEquals(LocalDate.of(2010, 1, 1), list.get(0).getDate());
    }

    @Test
    public void parseJsonWithOneEntityTest() {
        String OneEntityTestFile = defaultFileManager.readFile("testResources/OneEntityTestFile.json");
        List<Transaction> list = transactionConverter.parseJson(OneEntityTestFile);

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getInvoiceInto());
        assertEquals(2, list.get(0).getInvoiceTo());
        assertEquals(Status.READY, list.get(0).getStatus());
        assertEquals(1000.0d, list.get(0).getAmount(), 1);
        assertEquals(LocalDate.of(2010, 1, 1), list.get(0).getDate());
    }

    @Test
    public void toXMLTest() {
        List<Transaction> list = transactionConverter.parseJson(jsonToParse);

        String xml = transactionConverter.toXML(list);

        assertEquals(xmlExpected, xml);
    }

    @Test
    public void toCSVTest() {
        List<Transaction> list = transactionConverter.parseJson(jsonToParse);

        String csv = transactionConverter.toCSV(list);

        assertEquals(csvExpected, csv);
    }
}
