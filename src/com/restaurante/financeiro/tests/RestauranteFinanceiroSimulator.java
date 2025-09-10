package com.restaurante.financeiro.tests;

import com.restaurante.financeiro.dao.FinancialTransactionDAO;
import com.restaurante.financeiro.dao.FinancialTransactionDAOimpl;
import com.restaurante.financeiro.dao.SalesItemDAOimpl;
import com.restaurante.financeiro.entities.FinancialTransaction;
import com.restaurante.financeiro.entities.SalesItem;
import com.restaurante.financeiro.exceptions.FinancialException;
import com.restaurante.financeiro.util.DatabaseInitializer;
import com.restaurante.financeiro.util.OracleConnectionManager;
import com.restaurante.financeiro.service.RestauranteFinanceiroService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RestauranteFinanceiroSimulator {
    // Utilitário para cor (ANSI)
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Conecta e inicializa tabelas
            OracleConnectionManager manager = OracleConnectionManager.getInstance();
            connection = manager.getConnection();
            new DatabaseInitializer().initializeTables(connection);
            // Reset tables before running tests
            new DatabaseInitializer().resetTables(connection);

            // Instancia DAOs
            FinancialTransactionDAO financialDAO = new FinancialTransactionDAOimpl();
            SalesItemDAOimpl salesDAO = new SalesItemDAOimpl();

            // Cria e salva transações financeiras
            FinancialTransaction trans1 = new FinancialTransaction(FinancialTransaction.TransactionType.INCOME, 500.0, "Venda almoço", FinancialTransaction.PaymentMethod.CASH, FinancialTransaction.TransactionCategory.FOOD);
            FinancialTransaction trans2 = new FinancialTransaction(FinancialTransaction.TransactionType.EXPENSE, 200.0, "Compra ingredientes", FinancialTransaction.PaymentMethod.DEBIT_CARD, FinancialTransaction.TransactionCategory.SUPPLIES);
            trans1 = financialDAO.save(trans1);
            trans2 = financialDAO.save(trans2);
            System.out.println("\n" + CYAN + BOLD + "==== TRANSAÇÕES FINANCEIRAS SALVAS ====" + RESET);
            System.out.println(GREEN + "Receita (ID): " + trans1.getId() + RESET);
            System.out.println(RED + "Despesa (ID): " + trans2.getId() + RESET);

            // Cria e salva itens de venda
            SalesItem item1 = new SalesItem("Coxinha", FinancialTransaction.TransactionCategory.FOOD, 10.5, 100);
            item1.setFinancialTransactionId(trans1);
            SalesItem item2 = new SalesItem("Coca-Cola", FinancialTransaction.TransactionCategory.BEVERAGE, 8.0, 75);
            item2.setFinancialTransactionId(trans1);
            SalesItem item3 = new SalesItem("Guaraná", FinancialTransaction.TransactionCategory.BEVERAGE, 5.0, 50);
            item3.setFinancialTransactionId(trans1);
            item1 = salesDAO.save(item1);
            item2 = salesDAO.save(item2);
            item3 = salesDAO.save(item3);
            List<SalesItem> itensCriados = List.of(item1, item2, item3);
            System.out.println("\n" + CYAN + BOLD + "==== ITENS DE VENDA CRIADOS ====" + RESET);
            for (SalesItem item : itensCriados) {
                System.out.println(GREEN + "Produto: " + item.getName() + " | Categoria: " + item.getCategory() + " | Preço: R$ " + item.getPrice() + RESET);
            }

            // Testa métodos criativos
            System.out.println("\n" + CYAN + BOLD + "==== SIMULAÇÃO DE VENDA ====" + RESET);
            FinancialTransaction vendaTransacao1 = RestauranteFinanceiroService.simularVenda(salesDAO, financialDAO, item1, 10); // Vende 10 coxinhas
            if (vendaTransacao1 != null) {
                double valorTotal = 10 * item1.getPrice();
                String hash = "@" + Integer.toHexString(System.identityHashCode(vendaTransacao1));
                System.out.println(GREEN + "Venda registrada: " + BOLD + "10x " + item1.getName() + RESET +
                        " | Valor total: R$ " + String.format("%.2f", valorTotal) +
                        " | Transação ID: " + YELLOW + hash + RESET);
            }
            FinancialTransaction vendaTransacao2 = RestauranteFinanceiroService.simularVenda(salesDAO, financialDAO, item2, 5); // Vende 5 Coca-Cola
            if (vendaTransacao2 != null) {
                double valorTotal = 5 * item2.getPrice();
                String hash = "@" + Integer.toHexString(System.identityHashCode(vendaTransacao2));
                System.out.println(GREEN + "Venda registrada: " + BOLD + "5x " + item2.getName() + RESET +
                        " | Valor total: R$ " + String.format("%.2f", valorTotal) +
                        " | Transação ID: " + YELLOW + hash + RESET);
            }
            System.out.println("\n" + CYAN + BOLD + "==== SIMULAÇÃO DE COMPRA ====" + RESET);
            FinancialTransaction compraTransacao = RestauranteFinanceiroService.simularCompra(salesDAO, financialDAO, item1, 50, 6.0); // Compra 50 coxinhas a R$6,00 cada
            if (compraTransacao != null) {
                double valorTotal = 50 * 6.0;
                String hash = "@" + Integer.toHexString(System.identityHashCode(compraTransacao));
                System.out.println(GREEN + "Compra registrada: " + BOLD + "50x " + item1.getName() + RESET +
                        " | Valor total: R$ " + String.format("%.2f", valorTotal) +
                        " | Transação ID: " + YELLOW + hash + RESET);
            }
            System.out.println("\n" + CYAN + BOLD + "==== RELATÓRIO FINANCEIRO ====" + RESET);
            RestauranteFinanceiroService.relatorioSaldo(financialDAO); // Mostra saldo financeiro
            System.out.println("\n" + CYAN + BOLD + "==== VENDAS POR CATEGORIA ====" + RESET);
            RestauranteFinanceiroService.relatorioVendasPorCategoria(financialDAO); // Mostra vendas por categoria

        } catch (SQLException | FinancialException e) {
            System.err.println(RED + "Erro na simulação: " + e.getMessage() + RESET);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(RED + "Erro ao fechar conexão." + RESET);
                }
            }
        }
    }
}