package exceptions;

public class CustomerNotFoundException extends BusinessException {
    public CustomerNotFoundException() {
        super("ERROR Customer was not found");
    }
}
