package java.com.restaurante.financeiro.dao;

import java.com.restaurante.financeiro.entities.FinancialTransaction;
import java.time.LocalDateTime;

public class FinancialTransactionFilter {
    private FinancialTransaction.TransactionType type;
    private FinancialTransaction.TransactionCategory category;
    private FinancialTransaction.PaymentMethod paymentMethod;
    private Double minValue;
    private Double maxValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public FinancialTransaction.TransactionType getType() {
        return type;
    }

    public void setType(FinancialTransaction.TransactionType type) {
        this.type = type;
    }

    public FinancialTransaction.TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(FinancialTransaction.TransactionCategory category) {
        this.category = category;
    }

    public FinancialTransaction.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(FinancialTransaction.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public java.time.LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(java.time.LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public java.time.LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(java.time.LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
