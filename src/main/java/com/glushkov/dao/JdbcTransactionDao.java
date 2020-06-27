package com.glushkov.dao;


import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDao implements TransactionDao {

    private static final String INSERT_NEW_ROW = "INSERT INTO public.transactions_table1(invoiceinto, invoiceto, " +
            "status, amount, date) VALUES (?, ?, ?, ?, ?);";

    private static final String GET_ALL = "SELECT id, invoiceinto, invoiceto, status, amount, date " +
            "FROM public.transactions_table1;";

    private DataSource dataSource;

    public JdbcTransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(List<Transaction> transactionsList) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ROW)) {

            for (Transaction transaction : transactionsList) {
                preparedStatement.setInt(1, transaction.getInvoiceInto());
                preparedStatement.setInt(2, transaction.getInvoiceTo());
                preparedStatement.setString(3, String.valueOf(transaction.getStatus()));
                preparedStatement.setDouble(4, transaction.getAmount());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException("Can`t add transaction to DB. Please check the validity of transaction field " +
                    "values and application properties", sqlException);
        }
    }

    public List<Transaction> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getResultSet(resultSet);
        } catch (SQLException sqlException) {
            throw new RuntimeException("Can`t show all transactions. ", sqlException);
        }
    }

    private List<Transaction> getResultSet(ResultSet resultSet) {
        try {
            List<Transaction> transactionList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int invoiceInto = resultSet.getInt("invoiceinto");
                int invoiceTo = resultSet.getInt("invoiceto");
                Status status = Status.valueOf(resultSet.getString("status"));
                double amount = resultSet.getDouble("amount");
                Timestamp date = resultSet.getTimestamp("date");  /*Date.valueOf(resultSet.getString("date"));*/
                Transaction transaction = new Transaction(id, invoiceInto, invoiceTo, status, amount,
                        date.toLocalDateTime());
                transactionList.add(transaction);
            }
            resultSet.close();
            return transactionList;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Can`t get transactions from result set.", sqlException);
        }
    }
}
