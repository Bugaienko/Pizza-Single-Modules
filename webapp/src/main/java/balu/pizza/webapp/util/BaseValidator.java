package balu.pizza.webapp.util;

import balu.pizza.webapp.models.Base;
import balu.pizza.webapp.services.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Sergii Bugaienko
 */

@Component
public class BaseValidator implements Validator {


    private final BaseService baseService;

    public BaseValidator(BaseService baseService) {
        this.baseService = baseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Base.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Base newBase = (Base) target;
//        Optional<Base> resultByName = baseService.findByName(newBase.getName());

//        if (resultByName.isPresent()) {
//            errors.rejectValue("name", "", "This name is already in use. Choose another");
//        }

    }
}
