package balu.pizzarest.pizzaproject.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Sergii Bugaienko
 */

public class TypeDTO {


    private int id;
    @NotEmpty(message = "Name should be not empty")
    private String name;

    public TypeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
