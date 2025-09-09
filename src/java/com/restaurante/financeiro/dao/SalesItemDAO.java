package java.com.restaurante.financeiro.dao;

import java.com.restaurante.financeiro.entities.FinancialTransaction;
import java.com.restaurante.financeiro.entities.SalesItem;

import java.util.List;
import java.util.Optional;

public interface SalesItemDAO {

    SalesItem insert(SalesItem item);

    boolean update(SalesItem item);

    boolean updatePrice(long id, double newPrice);

    boolean incrementAmount(long id, int delta);

    boolean setAmount(long id, int amount);

    boolean softDelete(long id);

    boolean delete(long id);

    Optional<SalesItem> findById(long id);

    List<SalesItem> findAll();

    List<SalesItem> findAllActive();

    List<SalesItem> findByCategory(FinancialTransaction.TransactionCategory category);

    List<SalesItem> searchByName(String nameLike);

    boolean exists(long id);

    long count();
}