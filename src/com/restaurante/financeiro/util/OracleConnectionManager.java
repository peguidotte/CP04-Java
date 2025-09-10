package com.restaurante.financeiro.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionManager {

    private static final String DB_URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String DB_USER = "RM556630";
    private static final String DB_PASSWORD = "050805";

    private static OracleConnectionManager instance;

    private Connection connection;

    private OracleConnectionManager() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC from Oracle not found.", e);
        }
    }

    public static OracleConnectionManager getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new OracleConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}