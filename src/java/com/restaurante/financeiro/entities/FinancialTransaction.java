package java.com.restaurante.financeiro.entities;

import java.time.LocalDateTime;

public class FinancialTransaction {

    public enum TransactionType {
        INCOME,
        EXPENSE
    }

    public enum PaymentMethod {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD,
        PIX,
        OTHER
    }

    public enum TransactionCategory {
        FOOD,
        BEVERAGE,
        SUPPLIES,
        SALARY,
        RENT,
        UTILITIES,
        OTHER
    }

    private long id;
    private TransactionType type;
    private double value;
    private String description;
    private LocalDateTime date;
    private PaymentMethod paymentMethod;
    private TransactionCategory category;

    public FinancialTransaction(TransactionType type, double value, String description,
                                PaymentMethod paymentMethod, TransactionCategory category) {
        this.type = type;
        this.value = value;
        this.date = LocalDateTime.now();
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }

    public FinancialTransaction(long id, TransactionType type, double value, String description, LocalDateTime date,
                                PaymentMethod paymentMethod, TransactionCategory category) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.description = description;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
