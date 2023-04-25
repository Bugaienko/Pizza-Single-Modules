package balu.pizza.webapp.models;

import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author Sergii Bugaienko
 */

@Component
public class Price {
    @Valid
    @Min(value = 0, message = "Price should be greater that 0")
    private double price;

    public Price() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
