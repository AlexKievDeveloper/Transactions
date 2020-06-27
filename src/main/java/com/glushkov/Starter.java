package com.glushkov;

import com.glushkov.convertor.TransactionConverter;
import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcTransactionDao;
import com.glushkov.entity.Transaction;
import com.glushkov.fileManager.DefaultFileManager;

import java.util.List;

public class Starter {

    public static void main(String[] args) {
        //arguments
        String pathToJson = args[0];
        String pathToXML = args[1];
        String pathToCSV = args[2];

        //config
        DefaultFileManager defaultFileManager = new DefaultFileManager();
        TransactionConverter transactionConverter = new TransactionConverter();

        DefaultDataSource defaultDataSource = new DefaultDataSource();
        JdbcTransactionDao jdbcTransactionDao = new JdbcTransactionDao(defaultDataSource);


        //flow
        String json = defaultFileManager.readFile(pathToJson);
        List<Transaction> transactionList = transactionConverter.parseJson(json);

        jdbcTransactionDao.save(transactionList);
        List<Transaction> transactionsWithID = jdbcTransactionDao.getAll();

        String xml = transactionConverter.toXML(transactionsWithID);
        defaultFileManager.saveTo(pathToXML, xml);

        String csv = transactionConverter.toCSV(transactionsWithID);
        defaultFileManager.saveTo(pathToCSV, csv);
    }
}

