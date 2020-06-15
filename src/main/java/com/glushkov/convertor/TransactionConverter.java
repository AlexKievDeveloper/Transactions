package com.glushkov.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.glushkov.entity.Transaction;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TransactionConverter {
    private Transaction transaction;

    public TransactionConverter(Transaction transaction) {
        this.transaction = transaction;
    }

    public void toJSON() {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("TestTransactionFile.json"), transaction);
        } catch (IOException ioException){
            System.out.println("Error writing transaction to file: TestTransactionFile.json." +
                    " Please check transaction in TransactionConverter and try again");
            ioException.printStackTrace();
        }
    }

    public void toCSV() {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Transaction.class);
        csvSchema = csvSchema.withColumnSeparator('\t');

        ObjectWriter myObjectWriter = csvMapper.writer(csvSchema);
        File tempFile = new File("TestTransactionFile.csv");
        try {
            FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
            OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
            myObjectWriter.writeValue(writerOutputStream, transaction);
            writerOutputStream.close();
            bufferedOutputStream.close();
            tempFileOutputStream.close();
        }
        catch (IOException ioException){
            System.out.println("Error writing transaction to file: TestTransactionFile.csv." +
                    " Please check transaction in TransactionConverter and try again");
            ioException.printStackTrace();
        }
    }

    public void toXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(Transaction.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(transaction, new File("TestTransactionFile.xml"));
        } catch (JAXBException jaxbException){
            System.out.println("Error writing transaction to file: TestTransactionFile.xml." +
                    " Please check transaction in TransactionConverter and try again");
        }
    }
}
