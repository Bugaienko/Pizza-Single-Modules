package balu.pizzarest.pizzaproject.dto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Sergii Bugaienko
 */

public class CafeDTO {


    private int id;
    @NotEmpty(message = "Title should be not empty")
    private String title;

    @NotEmpty(message = "City should be not empty")
    private String city;
    @NotEmpty(message = "Email should be not empty")
    @Email
    private String email;
    @NotEmpty(message = "Phone should be not empty")
    private String phone;
    private String openAt;
    private String closeAt;

    private String image;

    public CafeDTO() {
    }

    public CafeDTO(String title, String city, String email, String phone, String openAt, String closeAt) {
        this.title = title;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
