package balu.pizzarest.pizzaproject.util;

import balu.pizzarest.pizzaproject.models.Base;
import balu.pizzarest.pizzaproject.services.BaseService;
import balu.pizzarest.pizzaproject.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Component
public class BaseValidator implements Validator {


    private final BaseService baseService;

    @Autowired
    public BaseValidator(PizzaService pizzaService, BaseService baseService) {

        this.baseService = baseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Base.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Base base = (Base) target;

//        Optional<Base> resultByName = baseService.findByName(base.getName());

//        if (resultByName.isPresent()) {
//            errors.rejectValue("name", "", "This name is already in use. Choose another");
//        }
        if ((base.getSize() == null) || !(base.getSize().equals("Small") || base.getSize().equals("Medium") || base.getSize().equals("Large"))){
            errors.rejectValue("size", "", "The size must have one of the values: Small, Medium, Large");
        }

    }

    public void validate(Object target, int baseId, Errors errors) {
        Base base = (Base) target;

        Optional<Base> resultByName = baseService.findByName(base.getName());

        if (resultByName.isPresent()) {
            List<Base> bases = new ArrayList<>();
            resultByName.ifPresent(bases::add);
            for (Base base1 : bases) {

                if (base1.getId() != baseId) {
                    errors.rejectValue("name", "", "This name is already in use. Choose another");
                }
            }
        }
    }
}
