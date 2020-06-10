package com.glushkov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TransactionConverter {
    private final Transaction transaction;

    public TransactionConverter(Transaction transaction) {
        this.transaction = transaction;
    }

    public void toJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("TestTransactionFile.json"), transaction);
    }

    public void toCSV() throws IOException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Transaction.class);
        csvSchema = csvSchema.withColumnSeparator('\t');

        ObjectWriter myObjectWriter = csvMapper.writer(csvSchema);
        File tempFile = new File("TestTransactionFile.csv");
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
        myObjectWriter.writeValue(writerOutputStream, transaction);
    }

    public void toXML() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Transaction.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(transaction, new File("TestTransactionFile.xml"));
    }
}
