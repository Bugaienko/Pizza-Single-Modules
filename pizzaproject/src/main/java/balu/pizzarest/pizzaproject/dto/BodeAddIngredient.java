package balu.pizzarest.pizzaproject.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/** * @author Sergii Bugaienko
 */

public class BodeAddIngredient {


    @NotEmpty(message = "Name should be not empty")
    @ApiModelProperty(example = "name")
    private String name;

    @ApiModelProperty(example = "4.50")
    private Double price;


    @ApiModelProperty(example = "1")
    private int type_id;

    @ApiModelProperty(example = "image.jpg")
    private String image;

    public BodeAddIngredient() {
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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
