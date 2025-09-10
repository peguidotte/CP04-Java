package com.restaurante.financeiro.dao;

import com.restaurante.financeiro.entities.SalesItem;
import com.restaurante.financeiro.exceptions.FinancialException;
import com.restaurante.financeiro.util.OracleConnectionManager;
import java.sql.*;

public class SalesItemDAOimpl implements SalesItemDAO {

    private Connection connection;

    public void FinancialTransactionDAOImpl() {
        try {
            this.connection = OracleConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect with the database.", e);
        }
    }

    @Override
    public SalesItem save(SalesItem item) throws FinancialException {
        System.out.println("Saving SalesItem: " + item);
        return item;
    }


}
