package com.glushkov;


import java.sql.*;

public class MySQLDBWorker {
    private String HOST = "jdbc:mysql://localhost:3306/transactions?useUnicode=true&useSSL=" +
            "true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String USERNAME = "root";
    private String PASSWORD = "RbtdAlexander18!";
    private String INSERT_NEW_ROW = "INSERT INTO transactions.mytable(`Into`, `To`, Status, Amount, Date) " +
            "VALUES (?,?,?,?,?)";

    public MySQLDBWorker() {
    }

    public MySQLDBWorker(String HOST, String USERNAME, String PASSWORD, String INSERT_NEW_ROW) {
        this.HOST = HOST;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.INSERT_NEW_ROW = INSERT_NEW_ROW;
    }

    public void transactionToSQLtable(Transaction transaction) throws SQLException {

        try (Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ROW)) {

            preparedStatement.setInt(1, transaction.getInvoiceInto());
            preparedStatement.setInt(2, transaction.getInvoiceTo());
            preparedStatement.setString(3, String.valueOf(transaction.getStatus()));
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setTimestamp(5, new Timestamp(transaction.getDate().getTime()));
            preparedStatement.executeUpdate();
        }
    }
}
