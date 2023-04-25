package balu.pizza.webapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Sergii Bugaienko
 */

public class AuthenticationDTO {
    @Pattern(regexp = "([a-zA-Z]+[\\w\\s]*){3,}", message = "The username must contain only letters, numbers, underscores. Not shorter than 3 characters.")
    private String username;
    @NotEmpty(message = "Password should be not empty")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
