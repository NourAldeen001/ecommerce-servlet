package exceptions;

public class UncancelOrderException extends BusinessException {
    public UncancelOrderException() {
        super("Cannot change the order's status after it has been cancelled.");
    }
}
