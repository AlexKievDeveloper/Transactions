package com.glushkov.convertor;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.glushkov.entity.ListOfTransactions;
import com.glushkov.entity.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.json.JSONArray;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionConverter implements Convert {

    public List<Transaction> parseJson(String json) {
        List<Transaction> jsonList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (json1, type, jsonDeserializationContext) -> ZonedDateTime.parse(json1.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
            Transaction transaction = gson.fromJson(jsonArray.get(i).toString(), Transaction.class);
            jsonList.add(transaction);
        }
        return jsonList;
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
        csvMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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

    public static class LocalDateAdapter extends XmlAdapter<String, LocalDateTime> {
        public LocalDateTime unmarshal(String v) throws Exception {
            return LocalDateTime.parse(v);
        }

        public String marshal(LocalDateTime v) throws Exception {
            return v.toString();
        }
    }
}
