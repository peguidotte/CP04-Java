package com.restaurante.financeiro.dao;

import com.restaurante.financeiro.entities.SalesItem;

import com.restaurante.financeiro.exceptions.FinancialException;

public interface SalesItemDAO {

    // Salva uma nova transação no banco de dados e retorna o objeto com o ID gerado
    SalesItem save(SalesItem item) throws FinancialException;
}