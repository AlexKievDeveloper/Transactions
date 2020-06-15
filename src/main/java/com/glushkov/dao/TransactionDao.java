package com.glushkov.dao;

import com.glushkov.entity.Transaction;


public interface TransactionDao {

    void save(Transaction transaction);
}
