package balu.pizza.webapp.util;

import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.services.IngredientService;
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
public class IngredientValidator implements Validator {


    private final IngredientService ingredientService;

    public IngredientValidator(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingredient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ingredient targetIngr = (Ingredient) target;

        Optional<Ingredient> resultByName = ingredientService.findIngredientByName(targetIngr.getName());


        if (resultByName.isPresent()) {
            errors.rejectValue("name", "", "This title is already in use. Choose another");
        }
    }

    public void validate(Object target, int ingrId, Errors errors) {
        Ingredient targetIngr = (Ingredient) target;

        Optional<Ingredient> resultByName = ingredientService.findIngredientByName(targetIngr.getName());

        if (resultByName.isPresent()) {
            List<Ingredient> ingredients = Collections.singletonList(resultByName.get());
            for (Ingredient ing : ingredients) {
                if (ing.getId() != ingrId) {
                    errors.rejectValue("name", "", "This title is already in use. Choose another");
                }
            }
        }
    }
}
