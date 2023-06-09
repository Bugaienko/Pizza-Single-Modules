package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.services.PersonService;
import balu.pizza.webapp.services.PizzaService;
import balu.pizza.webapp.util.PersonValidator;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Users controller
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/user") public class PersonController {

    private final UserUtil userUtil;
    private final PizzaService pizzaService;
    private final PersonValidator personValidator;
    private final PersonService personService;

    /**
     *
     * @param userUtil Set users utils
     * @param pizzaService Pizza service
     * @param personValidator User data validation
     * @param personService User service
     */
    @Autowired
    public PersonController(UserUtil userUtil, PizzaService pizzaService, PersonValidator personValidator, PersonService personService) {
        this.userUtil = userUtil;
        this.pizzaService = pizzaService;
        this.personValidator = personValidator;
        this.personService = personService;
    }

    /**
     * Personal user area
     * <p style="text-align:left;">
     * <img src="doc-files/user.png" style="max-width: 50%;" alt="admin panel">
     * </p>
     * @param model
     * @return Generates a page for the route /user/myPage
     */
    @GetMapping("/myPage")
    public String userPage(Model model){
        Person user = userUtil.getActiveUser();
        List<Pizza> pizzas = pizzaService.findByPerson(user);
        user.setFavorites(pizzas);

        model.addAttribute("user", user);
        model.addAttribute("pizzas", user.getSortedPizza());
        return "person/user";

    }

    /**
     * Edit user information page
     * @param person
     * @param model
     * @return Generates a page for the route /user/edit
     */
    @GetMapping("/edit")
    public String changeUserInfo(@ModelAttribute("person") Person person, Model model){
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        model.addAttribute("person", user);
        return "auth/edit";
    }

    /**
     * Method for validating the information entered by the user
     *
     * @param person
     * @param bindingResult
     * @param rePassword
     * @param model
     * @return In case of successful validation - update the user information in the database
     */
    @PatchMapping("/edit")
    public String updateUserInfo(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, String rePassword, Model model){
        Person user = userUtil.getActiveUser();
        personValidator.validate(person, user.getId(), bindingResult);
        personValidator.validate(person, rePassword, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/edit";
        }
        model.addAttribute("user", user);
        System.out.println("person upd -> " + person);
        personService.update(user, person);

        return "redirect:/user/myPage";

    }



}
