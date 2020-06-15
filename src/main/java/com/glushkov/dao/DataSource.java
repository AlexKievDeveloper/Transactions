package com.glushkov.dao;

import java.sql.Connection;


public interface DataSource {

    Connection getConnection();
}
