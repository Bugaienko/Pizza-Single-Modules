package balu.pizza.webapp.util;

import balu.pizza.webapp.models.TypeIngredient;
import balu.pizza.webapp.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Component
public class TypeValidator implements Validator {


    private final TypeService typeService;

    @Autowired
    public TypeValidator(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TypeIngredient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TypeIngredient newType = (TypeIngredient) target;

        Optional<TypeIngredient> resultByName = typeService.findByName(newType.getName());


        if (resultByName.isPresent()) {
            errors.rejectValue("name", "", "This name is already in use. Choose another");
        }

    }
}
