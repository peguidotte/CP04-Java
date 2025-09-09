package java.com.restaurante.financeiro.dao;

import java.com.restaurante.financeiro.entities.FinancialTransaction;
import java.com.restaurante.financeiro.exceptions.FinancialException;
import java.time.LocalDateTime;
import java.util.List;

public interface FinancialTransactionDAO {
    // Salva uma nova transação no banco de dados e retorna o objeto com o ID gerado
    FinancialTransaction save(FinancialTransaction transaction) throws FinancialException;

    // Encontra todas as transações filtradas por critérios opcionais
    List<FinancialTransaction> findByFilter(FinancialTransactionFilter filter) throws FinancialException;

    // Encontra todas as transações
    List<FinancialTransaction> findAll() throws FinancialException;

    // Atualiza uma transação existente
    void update(FinancialTransaction transaction) throws FinancialException;

    // Deleta uma transação pelo seu ID
    void delete(long id) throws FinancialException;
}
