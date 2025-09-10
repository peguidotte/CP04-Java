package com.restaurante.financeiro.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public void initializeTables(Connection connection) {
        String createFinancialTransactionTable =
                "BEGIN\n" +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE FINANCIAL_TRANSACTION (\n" +
                        "    ID NUMBER GENERATED ALWAYS AS IDENTITY,\n" +
                        "    TRANSACTION_TYPE VARCHAR2(50) NOT NULL,\n" +
                        "    VALUE NUMBER(10, 2) NOT NULL,\n" +
                        "    DESCRIPTION VARCHAR2(255),\n" +
                        "    DATE_TIME TIMESTAMP NOT NULL,\n" +
                        "    PAYMENT_METHOD VARCHAR2(50) NOT NULL,\n" +
                        "    CATEGORY VARCHAR2(50) NOT NULL,\n" +
                        "    CONSTRAINT PK_FINANCIAL_TRANSACTION PRIMARY KEY (ID))';\n" +
                        "EXCEPTION\n" +
                        "  WHEN OTHERS THEN\n" +
                        "    IF SQLCODE = -955 THEN\n" +
                        "      NULL;\n" +
                        "    ELSE\n" +
                        "      RAISE;\n" +
                        "    END IF;\n" +
                        "END;";

        String createSalesItemTable =
                "BEGIN\n" +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE SALES_ITEM (\n" +
                        "    ID NUMBER GENERATED ALWAYS AS IDENTITY,\n" +
                        "    ITEM_NAME VARCHAR2(100) NOT NULL,\n" +
                        "    QUANTITY NUMBER(10) NOT NULL,\n" +
                        "    UNIT_PRICE NUMBER(10, 2) NOT NULL,\n" +
                        "    FINANCIAL_TRANSACTION_ID NUMBER NOT NULL,\n" +
                        "    CONSTRAINT PK_SALES_ITEM PRIMARY KEY (ID),\n" +
                        "    CONSTRAINT FK_SALES_ITEM_FINANCIAL_TRANSACTION FOREIGN KEY (FINANCIAL_TRANSACTION_ID) REFERENCES FINANCIAL_TRANSACTION(ID))';\n" +
                        "EXCEPTION\n" +
                        "  WHEN OTHERS THEN\n" +
                        "    IF SQLCODE = -955 THEN\n" +
                        "      NULL;\n" +
                        "    ELSE\n" +
                        "      RAISE;\n" +
                        "    END IF;\n" +
                        "END;";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createFinancialTransactionTable);
            System.out.println("Tabela FINANCIAL_TRANSACTION criada ou já existente.");

            stmt.execute(createSalesItemTable);
            System.out.println("Tabela SALES_ITEM criada ou já existente.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar as tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}