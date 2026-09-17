package exceptions;

public class AdminNotFoundException extends BusinessException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
