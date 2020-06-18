package com.glushkov.convertor;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.glushkov.entity.Transaction;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.json.JSONArray;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TransactionConverter implements Convert {
    public List<Transaction> parseJson(String json) {
        List<Transaction> jsonList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            Gson gson = new Gson();
            Transaction transaction = gson.fromJson(jsonArray.get(i).toString(), Transaction.class);
            jsonList.add(transaction);
        }
        return jsonList;
    }


    public String toXML(List<Transaction> transactionListWithID) {
        String xml = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Transaction.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter stringWriter = new StringWriter();

            for (Transaction transaction : transactionListWithID) {
                marshaller.marshal(transaction, stringWriter);
                stringWriter.append("\n");
            }
            xml = stringWriter.toString();
            stringWriter.close();
        } catch (IOException | JAXBException ioException) {
            throw new RuntimeException("Error writing transaction to String csv." +
                    " Please check transactions in transactionListWithID and try again");
        }
        return xml;
    }

    public String toCSV(List<Transaction> transactionListWithID) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Transaction.class);
        csvSchema = csvSchema.withColumnSeparator('\t');
        String csv = null;
        ObjectWriter myObjectWriter = csvMapper.writer(csvSchema);
        StringWriter stringWriter = new StringWriter();

        try {
            for (Transaction transaction : transactionListWithID) {
                myObjectWriter.writeValue(stringWriter, transaction);
            }
            csv = stringWriter.toString();
            stringWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("Error writing transaction to String csv." +
                    " Please check transactions in transactionListWithID and try again");
        }
        return csv;
    }
}
