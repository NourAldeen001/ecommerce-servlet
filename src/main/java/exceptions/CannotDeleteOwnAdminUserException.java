package exceptions;

public class CannotDeleteOwnAdminUserException extends BusinessException {
    public CannotDeleteOwnAdminUserException() {
        super("Forbidden operation. Could not delete own admin user");
    }
}
