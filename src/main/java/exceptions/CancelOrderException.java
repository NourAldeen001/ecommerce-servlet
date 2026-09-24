package exceptions;

public class CancelOrderException extends BusinessException {
    public CancelOrderException() {
        super("The orders's current status does not allow you to cancel the order.");
    }
}