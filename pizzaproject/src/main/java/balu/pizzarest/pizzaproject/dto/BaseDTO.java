package balu.pizzarest.pizzaproject.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sergii Bugaienko
 */

public class BaseDTO {

    private int id;
    @NotEmpty(message = "Size should be not empty")
    private String size;
    @NotEmpty(message = "Name should be not empty")
    private String name;
    private double price;

    public BaseDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
