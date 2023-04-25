package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.security.PersonDetails;
import balu.pizza.webapp.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Controller
public class HelloController {

    private final IngredientService ingredientService;

    @Autowired
    public HelloController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String testPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        List<Ingredient> ingredients = ingredientService.findAll();
        return personDetails.getUsername();



    }

    @GetMapping("/ddd")
    public String debugPage() {
        return "temp/index";
    }
}
