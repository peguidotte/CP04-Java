package com.restaurante.financeiro.tests;

import com.restaurante.financeiro.util.DatabaseInitializer;
import com.restaurante.financeiro.util.OracleConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            System.out.println("Tentando conectar ao banco de dados...");
            // Obtém a única instância do OracleConnectionManager
            OracleConnectionManager manager = OracleConnectionManager.getInstance();

            // Pega a conexão
            connection = manager.getConnection();

            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso! ✅");
                DatabaseInitializer initializer = new DatabaseInitializer();
                initializer.initializeTables(connection);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar ao banco de dados!");
            e.printStackTrace(); // Imprime o rastreamento completo da exceção
            System.err.println("Detalhes do erro: " + e.getMessage());

        } finally {
            // Garante que a conexão seja fechada, mesmo se houver um erro
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexão fechada com sucesso. 👋");
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar a conexão.");
                    e.printStackTrace();
                }
            }
        }
    }
}