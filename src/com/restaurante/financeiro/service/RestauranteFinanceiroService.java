package com.restaurante.financeiro.service;

import com.restaurante.financeiro.dao.FinancialTransactionDAO;
import com.restaurante.financeiro.dao.SalesItemDAOimpl;
import com.restaurante.financeiro.entities.FinancialTransaction;
import com.restaurante.financeiro.entities.SalesItem;
import com.restaurante.financeiro.exceptions.FinancialException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RestauranteFinanceiroService {
    // Simula uma venda de item: atualiza estoque e registra receita
    public static FinancialTransaction simularVenda(SalesItemDAOimpl salesDAO, FinancialTransactionDAO financialDAO, SalesItem item, int quantidadeVendida) throws FinancialException {
        if (item.getAmount() < quantidadeVendida) {
            System.out.println("Estoque insuficiente para venda de " + item.getName());
            return null;
        }
        item.setAmount(item.getAmount() - quantidadeVendida);
        salesDAO.update(item);
        double valorTotal = quantidadeVendida * item.getPrice();
        FinancialTransaction transacaoVenda = new FinancialTransaction(
            FinancialTransaction.TransactionType.INCOME,
            valorTotal,
            "Venda de " + quantidadeVendida + "x " + item.getName(),
            FinancialTransaction.PaymentMethod.CASH,
            item.getCategory()
        );
        financialDAO.save(transacaoVenda);
        return transacaoVenda;
    }

    // Simula compra de suprimentos: aumenta estoque e registra despesa
    public static FinancialTransaction simularCompra(SalesItemDAOimpl salesDAO, FinancialTransactionDAO financialDAO, SalesItem item, int quantidadeComprada, double precoUnitario) throws FinancialException {
        item.setAmount(item.getAmount() + quantidadeComprada);
        item.setPrice(precoUnitario); // Atualiza preço de custo
        salesDAO.update(item);
        double valorTotal = quantidadeComprada * precoUnitario;
        FinancialTransaction transacaoCompra = new FinancialTransaction(
            FinancialTransaction.TransactionType.EXPENSE,
            valorTotal,
            "Compra de " + quantidadeComprada + "x " + item.getName(),
            FinancialTransaction.PaymentMethod.DEBIT_CARD,
            FinancialTransaction.TransactionCategory.SUPPLIES
        );
        financialDAO.save(transacaoCompra);
        return transacaoCompra;
    }

    // Lista itens com estoque abaixo do mínimo
    public static void listarItensBaixoEstoque(SalesItemDAOimpl salesDAO, int minimo) throws FinancialException {
        List<SalesItem> itens = salesDAO.findAll();
        System.out.println("\nItens com estoque abaixo de " + minimo + ":");
        boolean encontrou = false;
        for (SalesItem item : itens) {
            if (item.getAmount() < minimo) {
                System.out.println(item);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum item com estoque abaixo de " + minimo + ".");
        }
    }

    // Relatório de saldo financeiro
    public static void relatorioSaldo(FinancialTransactionDAO financialDAO) throws FinancialException {
        List<FinancialTransaction> transacoes = financialDAO.findAll();
        double receitas = 0, despesas = 0;
        for (FinancialTransaction t : transacoes) {
            if (t.getType() == FinancialTransaction.TransactionType.INCOME) receitas += t.getValue();
            else despesas += t.getValue();
        }
        System.out.println("\nReceitas: R$ " + receitas);
        System.out.println("Despesas: R$ " + despesas);
        System.out.println("Saldo: R$ " + (receitas - despesas));
    }

    // Relatório de vendas por categoria
    public static void relatorioVendasPorCategoria(FinancialTransactionDAO financialDAO) throws FinancialException {
        List<FinancialTransaction> transacoes = financialDAO.findAll();
        Map<FinancialTransaction.TransactionCategory, Double> vendasPorCategoria = new HashMap<>();
        for (FinancialTransaction t : transacoes) {
            if (t.getType() == FinancialTransaction.TransactionType.INCOME) {
                vendasPorCategoria.put(t.getCategory(), vendasPorCategoria.getOrDefault(t.getCategory(), 0.0) + t.getValue());
            }
        }
        System.out.println("\nVendas por categoria:");
        for (var entry : vendasPorCategoria.entrySet()) {
            System.out.println(entry.getKey() + ": R$ " + entry.getValue());
        }
    }
}
