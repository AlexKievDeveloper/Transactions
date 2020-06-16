package com.glushkov;

import com.glushkov.convertor.TransactionConverter;
import com.glushkov.dao.DefaultDataSource;
import com.glushkov.dao.JdbcTransactionDao;
import com.glushkov.entity.Transaction;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Set;

public class Starter {


public static void main(String[] args) {
        //config
        Transaction transaction;
        TransactionConverter transactionConverter;

        /*DefaultDataSource not explicitly used. Inside the class, using the getconnection() method, a connection is
        established to the database which is used in the save() method of the jdbcTransactionDao class*/
        DefaultDataSource defaultDataSource = new DefaultDataSource();

        JdbcTransactionDao jdbcTransactionDao = new JdbcTransactionDao();

        //flow
        transaction = Transaction.JSONtoTransaction("TransactionFile.json");//get Transaction from json
        jdbcTransactionDao.save(transaction); //saving transaction do DB

        transactionConverter = new TransactionConverter(transaction); //create transaction Converter(initializing with transaction)
        transactionConverter.toXML();//convert transaction to XMLFile and saving in default program directory Transaction
        transactionConverter.toCSV();//convert transaction to CSVFile and saving in default program directory Transaction
    }
}




