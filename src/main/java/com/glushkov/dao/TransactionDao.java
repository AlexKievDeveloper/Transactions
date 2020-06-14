package com.glushkov.dao;

import com.glushkov.entity.Transaction;

import java.sql.SQLException;

public interface TransactionDao {

    void save(Transaction transaction) throws SQLException;
}
