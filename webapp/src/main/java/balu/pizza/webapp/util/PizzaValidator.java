package balu.pizza.webapp.util;

import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Component
public class PizzaValidator implements Validator {


    private final PizzaService pizzaService;

    @Autowired
    public PizzaValidator(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Pizza.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pizza newPizza = (Pizza) target;

        Optional<Pizza> resultByName = pizzaService.findByName(newPizza.getName());

        if (resultByName.isPresent()) {
            errors.rejectValue("name", "", "This name is already in use. Choose another");
        }

    }

    public void validate(Object target, int pizzaId, Errors errors) {
        Pizza newPizza = (Pizza) target;

        Optional<Pizza> resultByName = pizzaService.findByName(newPizza.getName());

        if (resultByName.isPresent()) {
            List<Pizza> pizzas = Collections.singletonList(resultByName.get());
            for (Pizza pizza : pizzas) {
                if (pizza.getId() != pizzaId) {
                    errors.rejectValue("name", "", "This name is already in use. Choose another");
                }
            }
        }
    }
}
