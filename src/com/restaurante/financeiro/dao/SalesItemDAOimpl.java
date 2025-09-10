package com.restaurante.financeiro.dao;

import com.restaurante.financeiro.entities.SalesItem;
import com.restaurante.financeiro.exceptions.FinancialException;
import com.restaurante.financeiro.util.OracleConnectionManager;
import java.sql.*;

public class SalesItemDAOimpl implements SalesItemDAO {

    private Connection connection;

    public SalesItemDAOimpl() {
        try {
            this.connection = OracleConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect with the database.", e);
        }
    }

    @Override
    public SalesItem save(SalesItem item) throws FinancialException {
        String sql = "INSERT INTO SALES_ITEM (ITEM_NAME, QUANTITY, UNIT_PRICE, FINANCIAL_TRANSACTION_ID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"})) {
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getAmount());
            stmt.setDouble(3, item.getPrice());
            stmt.setLong(4, item.getFinancialTransactionId() != null ? item.getFinancialTransactionId().getId() : 0);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new FinancialException("Failed to save the sales item, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                } else {
                    throw new FinancialException("Failed to save the sales item, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new FinancialException("Error saving sales item in the database.", e);
        }
        return item;
    }

    public java.util.List<SalesItem> findAll() throws FinancialException {
        String sql = "SELECT * FROM SALES_ITEM";
        java.util.List<SalesItem> items = new java.util.ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SalesItem item = new SalesItem();
                item.setId(rs.getLong("ID"));
                item.setName(rs.getString("ITEM_NAME"));
                item.setAmount(rs.getInt("QUANTITY"));
                item.setPrice(rs.getDouble("UNIT_PRICE"));
                // For simplicity, not loading FinancialTransaction object here
                items.add(item);
            }
        } catch (SQLException e) {
            throw new FinancialException("Error retrieving sales items from the database.", e);
        }
        return items;
    }

    public void update(SalesItem item) throws FinancialException {
        String sql = "UPDATE SALES_ITEM SET ITEM_NAME = ?, QUANTITY = ?, UNIT_PRICE = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getAmount());
            stmt.setDouble(3, item.getPrice());
            stmt.setLong(4, item.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new FinancialException("Failed to update the sales item, no rows affected.");
            }
        } catch (SQLException e) {
            throw new FinancialException("Error updating sales item in the database.", e);
        }
    }

    public void delete(long id) throws FinancialException {
        String sql = "DELETE FROM SALES_ITEM WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new FinancialException("Failed to delete the sales item, no rows affected.");
            }
        } catch (SQLException e) {
            throw new FinancialException("Error deleting sales item from the database.", e);
        }
    }


}