package dto;

import util.Validator;

public class CreateAdminRequest {
    private String username;
    private String password;

    public CreateAdminRequest(String username, String password, String confirmPassword) {
        setUsername(username);
        setPassword(password, confirmPassword);
    }

    public void setUsername(String username) {
        if(Validator.isNotBlank(username)) {
            this.username = username;
            return;
        }
        throw new IllegalArgumentException("Username must not be empty or blank");
    }

    public void setPassword(String password, String confirmPassword) {
        if(Validator.isStrongPassword(password)) {
            if(password.equals(confirmPassword)) {
                this.password = password;
                return;
            }
            throw new IllegalArgumentException("Password and Confirm Password must be equals");
        }
        throw new IllegalArgumentException("Password must have at least one uppercase,"
                + "lowercase, and special char with letter length at least 8");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
