package com.glushkov.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.glushkov.entity.ListOfTransactions;
import com.glushkov.entity.Transaction;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TransactionConverter implements Convert {

    public List<Transaction> parseJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(objectMapper.readValue(json, Transaction[].class));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException("Сheck the correctness of your json file", jsonProcessingException);
        }
    }


    public String toXML(List<Transaction> transactionListWithID) {

        try {
            ListOfTransactions listOfTransactions = new ListOfTransactions(transactionListWithID);
            JAXBContext context = JAXBContext.newInstance(ListOfTransactions.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(listOfTransactions, stringWriter);
            stringWriter.close();
            return stringWriter.toString();
        } catch (IOException | JAXBException ioException) {
            throw new RuntimeException("Error writing transaction to String xml." +
                    " Please check transactions in transactionListWithID and try again", ioException);
        }
    }

    public String toCSV(List<Transaction> transactionListWithID) {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.findAndRegisterModules();
        csvMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        CsvSchema csvSchema = csvMapper.schemaFor(Transaction.class);
        csvSchema = csvSchema.withColumnSeparator('\t');
        ObjectWriter myObjectWriter = csvMapper.writer(csvSchema);
        StringWriter stringWriter = new StringWriter();

        try {
            for (Transaction transaction : transactionListWithID) {
                myObjectWriter.writeValue(stringWriter, transaction);
            }
            stringWriter.close();
            return stringWriter.toString();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("Error writing transaction to String csv." +
                    " Please check transactions in transactionListWithID and try again", ioException);
        }
    }

    public static class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
        public LocalDate unmarshal(String localDate) {
            return LocalDate.parse(localDate);
        }

        public String marshal(LocalDate localDate) {
            return localDate.toString();
        }
    }
}
