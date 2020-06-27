package com.glushkov;

import com.glushkov.convertor.TransactionConverter;
import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcTransactionDao;
import com.glushkov.entity.Status;
import com.glushkov.fileManager.DefaultFileManager;
import com.glushkov.entity.Transaction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
/*        Transaction transaction = new Transaction(10, 11, Status.READY, 20000,
                new Timestamp(2010-1900, 1, 1, 12, 0, 0, 0));
        Transaction transaction1 = new Transaction(1011, 1111, Status.READY, 30000,
                new Timestamp(2010-1900, 1, 1, 12, 0, 0, 0));*/

/*        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList.add(transaction1);*/