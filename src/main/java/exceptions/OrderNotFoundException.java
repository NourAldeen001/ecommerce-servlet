package exceptions;

public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException() {
        super("ERROR Order was not found");
    }
}
