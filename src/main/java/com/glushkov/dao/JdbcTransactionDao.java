package com.glushkov.dao;


import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JdbcTransactionDao implements TransactionDao {

    private static final String SAVE_TRANSACTION_TO_DB = "INSERT INTO public.transactions_table1(invoiceinto, invoiceto, " +
            "status, amount, date) VALUES (?, ?, ?, ?, ?);";

    private static final String GET_ALL = "SELECT id, invoiceinto, invoiceto, status, amount, date " +
            "FROM public.transactions_table1;";

    private DataSource dataSource;

    public JdbcTransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(List<Transaction> transactionList) {
        try (Connection connection = dataSource.getConnection();) {

            for (Transaction transaction : transactionList) {
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TRANSACTION_TO_DB);
                preparedStatement.setInt(1, transaction.getInvoiceInto());
                preparedStatement.setInt(2, transaction.getInvoiceTo());
                preparedStatement.setString(3, String.valueOf(transaction.getStatus()));
                preparedStatement.setDouble(4, transaction.getAmount());

                LocalDate createDate = transaction.getDate();
                Date date = Date.valueOf(createDate);
                preparedStatement.setDate(5, date);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't add transaction to DB ", e);
        }
    }

    public List<Transaction> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL)) {

            List<Transaction> transactionListWithId = new ArrayList<>();

            while (resultSet.next()) {
                Transaction transaction = new TransactionRowMapper().transactionRowMapper(resultSet);
                transactionListWithId.add(transaction);
            }

            return transactionListWithId;

        } catch (SQLException e) {
            throw new RuntimeException("Can't show all transactions. ", e);
        }
    }

    protected static class TransactionRowMapper {
        public Transaction transactionRowMapper(ResultSet resultSet) {

            Transaction transaction = new Transaction();
            try {
                transaction.setId(resultSet.getInt("id"));
                transaction.setInvoiceInto(resultSet.getInt("invoiceinto"));
                transaction.setInvoiceTo(resultSet.getInt("invoiceto"));
                transaction.setStatus(Status.valueOf(resultSet.getString("status")));
                transaction.setAmount(resultSet.getDouble("amount"));
                Timestamp createDateTimestamp = resultSet.getTimestamp("date");

                if (!Objects.equals(createDateTimestamp, null)) {
                    transaction.setDate(createDateTimestamp.toLocalDateTime().toLocalDate());
                }
                return transaction;
            } catch (SQLException sqlException) {
                throw new RuntimeException("Can`t get value from result set. ", sqlException);
            }
        }
    }
}
