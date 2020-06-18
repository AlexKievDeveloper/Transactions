package com.glushkov.convertor;

import com.glushkov.entity.Transaction;

import java.util.List;

public interface Convert {

    List<Transaction> parseJson(String json);

    String toXML(List<Transaction> transactionListWithID);

    String toCSV(List<Transaction> transactionListWithID);
}
