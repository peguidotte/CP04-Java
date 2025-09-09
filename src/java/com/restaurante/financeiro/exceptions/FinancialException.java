package java.com.restaurante.financeiro.exceptions;

public class FinancialException extends Exception{
    public FinancialException(String message) {
        super(message);
    }

    public FinancialException(String message, Throwable cause) {
        super(message, cause);
    }
}
