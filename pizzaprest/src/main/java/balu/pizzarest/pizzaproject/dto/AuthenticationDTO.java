package balu.pizzarest.pizzaproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Sergii Bugaienko
 */

public class AuthenticationDTO {
    @ApiModelProperty(notes = "Username", example = "username", required = true)
    @Pattern(regexp = "([a-zA-Z]+[\\w\\s]*){3,}", message = "The username must contain only letters, numbers, underscores. Not shorter than 3 characters.")
    @JsonProperty("username")
    private String username;
    @ApiModelProperty(notes = "password", example = "password", required = true)
    @NotEmpty(message = "Password should be not empty")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    @JsonProperty("password")
    private String password;

    @Schema(example = "Username", description = "")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Schema(example = "password", description = "")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
