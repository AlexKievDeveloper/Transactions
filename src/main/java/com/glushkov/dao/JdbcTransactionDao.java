package com.glushkov.dao;


import com.glushkov.entity.Transaction;

import java.sql.*;

public class JdbcTransactionDao implements TransactionDao{

    private final String INSERT_NEW_ROW = "INSERT INTO transactions.mytable(`Into`, `To`, Status, Amount, Date) " +
            "VALUES (?,?,?,?,?)";

    public void save(Transaction transaction) {

        try (PreparedStatement preparedStatement = new DefaultDataSource().getConnection().prepareStatement(INSERT_NEW_ROW)) {

            preparedStatement.setInt(1, transaction.getInvoiceInto());
            preparedStatement.setInt(2, transaction.getInvoiceTo());
            preparedStatement.setString(3, String.valueOf(transaction.getStatus()));
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setTimestamp(5, new Timestamp(transaction.getDate().getTime()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException){
            System.out.println("Can`t add transaction to DB. " +
            "Please check the validity of transaction field values: " + "\n" +
            transaction.getInvoiceInto() + "\n" + transaction.getInvoiceInto() + "\n" +
            transaction.getStatus() + "\n" + transaction.getAmount() + "\n" + transaction.getDate() +
            "\n"+ " and application properties");
            sqlException.printStackTrace();
        }
    }
}
