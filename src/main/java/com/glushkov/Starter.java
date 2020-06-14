package com.glushkov;

import com.glushkov.convertor.TransactionConverter;
import com.glushkov.dao.JdbcTransactionDao;
import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Starter {

/*  1) DataSource методы и интерфейс
    2) resources read
    3) properties
    4) exceptions - везде словить и написать сообщение
    5) тесты - переделать запрос в скл таблицу, тесты должны проверять содержимое
    6) Transaction I test - написать нормально тесты
    7) Main - написать точку входа в программу
 */

    public static void main(String[] args) throws IOException, SQLException {
        Transaction transaction = new Transaction(4545, 5454, Status.READY, 4500000,
                LocalDateTime.of(2020, 4, 5, 4, 5,4));

        TransactionConverter transactionConverter = new TransactionConverter(transaction);
        transactionConverter.toJSON();

        Transaction transaction1 = Transaction.JSONtoTransaction("TestTransactionFile.json");

        JdbcTransactionDao jdbcTransactionDao = new JdbcTransactionDao();
        jdbcTransactionDao.save(transaction1);
    }
}
