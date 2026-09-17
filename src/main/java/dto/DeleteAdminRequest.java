package dto;

import exceptions.CannotDeleteOwnAdminUserException;
import util.Validator;

public class DeleteAdminRequest {

    private String usernameToDelete;


    public DeleteAdminRequest(String usernameToDelete, String currentUsername) {
        setUsernameToDelete(usernameToDelete, currentUsername);
    }


    public String getUsernameToDelete() {
        return usernameToDelete;
    }

    public void setUsernameToDelete(String usernameToDelete, String currentUsername) {
        if (Validator.isNotBlank(usernameToDelete) && Validator.isNotBlank(currentUsername)) {
            if (currentUsername.equals(usernameToDelete)) {
                throw new CannotDeleteOwnAdminUserException();
            }
            this.usernameToDelete = usernameToDelete;
        }

    }

}
