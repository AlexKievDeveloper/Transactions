package com.glushkov.dao;

import com.glushkov.entity.Transaction;

import java.util.List;


public interface TransactionDao {

    void save(List<Transaction> transactionsList);

    List<Transaction> getAll();
}
