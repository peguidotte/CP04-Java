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

    public FinancialTransaction(long id, TransactionType type, double value, LocalDateTime date,
            PaymentMethod paymentMethod, TransactionCategory category) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.date = date;
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



}
