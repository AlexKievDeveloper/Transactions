package com.glushkov;

import com.glushkov.entity.Status;
import com.glushkov.entity.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Starter {

    public static void main(String[] args) throws IOException, SQLException {
        Transaction transaction = new Transaction(7878, 7878, Status.READY, 787878,
                LocalDateTime.of(2020, 4, 5, 4, 5,4));
    }
}
