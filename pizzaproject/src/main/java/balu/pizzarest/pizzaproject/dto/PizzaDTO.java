package balu.pizzarest.pizzaproject.dto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

public class PizzaDTO {


    private int id;

    @NotEmpty(message = "Name should be not empty")
    private String name;

    private double price;

    private String image;

    private List<IngredientDTO> ingredients;

    private BaseDTO base;

    public PizzaDTO() {
    }

    public PizzaDTO(int id, String name, double price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public BaseDTO getBase() {
        return base;
    }

    public void setBase(BaseDTO base) {
        this.base = base;
    }
}
