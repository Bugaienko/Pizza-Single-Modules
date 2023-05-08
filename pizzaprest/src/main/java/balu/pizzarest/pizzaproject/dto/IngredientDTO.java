package balu.pizzarest.pizzaproject.dto;


import balu.pizzarest.pizzaproject.models.Ingredient;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @author Sergii Bugaienko
 */

public class IngredientDTO {


    private int id;
    @NotEmpty(message = "Name should be not empty")
    private String name;
    private Double price;

    private TypeDTO type;
    private String image;

    public IngredientDTO() {
    }

    public IngredientDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.price = ingredient.getPrice();
        this.image = ingredient.getImage();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TypeDTO getType() {
        return type;
    }

    public void setType(TypeDTO type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
