package balu.pizzarest.pizzaproject.dto;





import balu.pizzarest.pizzaproject.models.Person;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Sergii Bugaienko
 */

public class PersonDTO {
    private int id;

    @Pattern(regexp = "([a-zA-Z]+[\\w\\s]*){3,}", message = "The username must contain only letters, numbers, underscores. Not shorter than 3 characters.")
    @Schema(example = "username", description = "")
    private String username;
    @NotEmpty(message = "Password should be not empty")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    @Schema(example = "password", description = "")
    private String password;
    @Email
    @NotEmpty(message = "Email should be not empty")
    @Schema(example = "user@mail.com", description = "")
    private String email;
    private String role;
    private String avatar;

    public PersonDTO(int id, String username, String password, String email, String role, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
    }

    public PersonDTO() {
    }

    public static PersonDTO valueOf(Person person){
        return new PersonDTO(
                person.getId(),
                person.getUsername(),
                person.getPassword(),
                person.getEmail(),
                person.getRole(),
                person.getAvatar()
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
