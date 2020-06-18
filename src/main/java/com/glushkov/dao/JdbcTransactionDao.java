package com.glushkov.dao;


import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDao implements TransactionDao {

    String host, user, password;

    public JdbcTransactionDao(DefaultDataSource defaultDataSource) {
        host = defaultDataSource.properties.getProperty("jdbc.host");
        user = defaultDataSource.properties.getProperty("jdbc.user");
        password = defaultDataSource.properties.getProperty("jdbc.password");
    }

    public void save(List<Transaction> transactionsList) {
        final String INSERT_NEW_ROW = "INSERT INTO public.transactions_table (invoiceinto, invoiceto, status, amount)" +
                "VALUES (?,?,?,?)"; /*,?*/   /*, Date*/

        try (Connection connection = DriverManager.getConnection(host, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ROW)) {

            for (Transaction transaction : transactionsList) {
                preparedStatement.setInt(1, transaction.getInvoiceInto());
                preparedStatement.setInt(2, transaction.getInvoiceTo());
                preparedStatement.setString(3, String.valueOf(transaction.getStatus()));
                preparedStatement.setDouble(4, transaction.getAmount());
                /*preparedStatement.setTimestamp(5, new Timestamp(transactionsList.get(0).getDate().getTime()));*/
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException sqlException) {
            System.out.println("Can`t add transaction to DB. " +
                    "Please check the validity of transaction field values and application properties");
            sqlException.printStackTrace();
        }
    }


    public List<Transaction> getAll() {
        String getAllTransactions = "SELECT id, invoiceinto, invoiceto, status, amount\n" +
                "\tFROM public.transactions_table;";

        List<Transaction> transactionList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(host, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(getAllTransactions)) {
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int invoiceInto = resultSet.getInt("invoiceinto");
                int invoiceTo = resultSet.getInt("invoiceto");
                Status status = Status.valueOf(resultSet.getString("status"));
                int amount = resultSet.getInt("amount");
                //Date date = resultSet.getDate("date");
                Transaction transaction = new Transaction(id, invoiceInto, invoiceTo, status, amount);
                transactionList.add(transaction);
            }
        } catch (SQLException sqlException) {
            System.out.println("Can`t add transaction to DB. " +
                    "Please check the validity of transaction field values and application properties");
            sqlException.printStackTrace();
        }
        return transactionList;
    }
}
