package com.restaurante.financeiro.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class SalesItem {

    private Long id;
    private String name;
    private FinancialTransaction.TransactionCategory category;
    private double price;
    private int amount;
    private boolean active;
    private LocalDateTime createdAt;
    private FinancialTransaction financialTransactionId; // For future use

    public SalesItem() {
        this.active = true;
        this.amount = 0;
    }

    public SalesItem(Long id, String name,
                     FinancialTransaction.TransactionCategory category,
                     double price, int amount, boolean active,
                     LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.active = active;
        this.createdAt = createdAt;
    }

    public SalesItem(String name,
                     FinancialTransaction.TransactionCategory category,
                     double price,
                     int amount) {
        this(null, name, category, price, amount, true, null);
    }

    public void validate() {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name required");
        if (price <= 0)
            throw new IllegalArgumentException("Price must be > 0");
        if (amount < 0)
            throw new IllegalArgumentException("Amount must be >= 0");
        if (category == null)
            throw new IllegalArgumentException("Category required");
        if (!(category == FinancialTransaction.TransactionCategory.FOOD
                || category == FinancialTransaction.TransactionCategory.BEVERAGE
                || category == FinancialTransaction.TransactionCategory.OTHER)) {
            throw new IllegalArgumentException("Category must be FOOD, BEVERAGE or OTHER");
        }
    }

    // Domain behavior (no DB access)
    public void changePrice(double newPrice) {
        if (newPrice <= 0) throw new IllegalArgumentException("New price must be > 0");
        this.price = newPrice;
    }

    public void incrementAmount(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("Delta must be > 0");
        this.amount += delta;
    }

    public void setAmount(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be >= 0");
        this.amount = amount;
    }

    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }

    // Getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public FinancialTransaction.TransactionCategory getCategory() { return category; }
    public void setCategory(FinancialTransaction.TransactionCategory category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getAmount() { return amount; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public FinancialTransaction getFinancialTransactionId() { return financialTransactionId; }
    public void setFinancialTransactionId(FinancialTransaction financialTransactionId) { this.financialTransactionId = financialTransactionId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesItem that)) return false;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "SalesItem{id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", amount=" + amount +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}