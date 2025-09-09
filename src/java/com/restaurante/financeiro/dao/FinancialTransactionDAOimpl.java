package java.com.restaurante.financeiro.dao;

import java.com.restaurante.financeiro.entities.FinancialTransaction;
import java.com.restaurante.financeiro.exceptions.FinancialException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinancialTransactionDAOimpl implements FinancialTransactionDAO{

    private Connection connection;

    public void FinancialTransactionDAOImpl() {
        try {
            this.connection = OracleConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect with the database.", e);
        }
    }

    @Override
    public FinancialTransaction save(FinancialTransaction transaction) throws FinancialException {
        String sql = "INSERT INTO FINANCIAL_TRANSACTION (TRANSACTION_TYPE, VALUE, DESCRIPTION, DATE_TIME, PAYMENT_METHOD, CATEGORY) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"})) {
            stmt.setString(1, transaction.getType().name());
            stmt.setDouble(2, transaction.getValue());
            stmt.setString(3, transaction.getDescription());
            stmt.setObject(4, transaction.getDate());
            stmt.setString(5, transaction.getPaymentMethod().name());
            stmt.setString(6, transaction.getCategory().name());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new FinancialException("Failed to save the transaction, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getLong(1));
                } else {
                    throw new FinancialException("Failed to save the transaction, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new FinancialException("Error to save in the database.", e);
        }
        return transaction;
    }

    @Override
    public List<FinancialTransaction> findByFilter(FinancialTransactionFilter filter) throws FinancialException {
        StringBuilder sql = new StringBuilder("SELECT * FROM FINANCIAL_TRANSACTION WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (filter.getType() != null) {
            sql.append(" AND TRANSACTION_TYPE = ?");
            parameters.add(filter.getType().name());
        }

        if (filter.getCategory() != null) {
            sql.append(" AND CATEGORY = ?");
            parameters.add(filter.getCategory().name());
        }

        if (filter.getStartDate() != null) {
            sql.append(" AND DATE_TIME >= ?");
            parameters.add(filter.getStartDate());
        }

        if (filter.getEndDate() != null) {
            sql.append(" AND DATE_TIME <= ?");
            parameters.add(filter.getEndDate());
        }

        // Adicione a lógica para outros filtros aqui...

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<FinancialTransaction> transactions = new ArrayList<>();
                while (rs.next()) {
                    // Lógica para mapear o ResultSet para um objeto FinancialTransaction
                    // ...
                }
                return transactions;
            }
        } catch (SQLException e) {
            throw new FinancialException("Error retrieving transactions with filters.", e);
        }
    }

    @Override
    public List<FinancialTransaction> findAll() throws FinancialException {
        return List.of();
    }

    @Override
    public void update(FinancialTransaction transaction) throws FinancialException {

    }

    @Override
    public void delete(long id) throws FinancialException {

    }
}
