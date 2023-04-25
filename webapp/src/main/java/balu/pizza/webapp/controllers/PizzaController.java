package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.*;
import balu.pizza.webapp.services.CafeService;
import balu.pizza.webapp.services.IngredientService;
import balu.pizza.webapp.services.PersonService;
import balu.pizza.webapp.services.PizzaService;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    private final PizzaService pizzaService;
    private final PersonService personService;
    private final CafeService cafeService;
    private final UserUtil userUtil;
    private final IngredientService ingredientService;

    @Autowired
    public PizzaController(PizzaService pizzaService, PersonService personService, CafeService cafeService, UserUtil userUtil, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.personService = personService;
        this.cafeService = cafeService;
        this.userUtil = userUtil;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/add_to_cafe/{id}")
    public String addPizzaToCafeMenu(@PathVariable("id") int cafeId, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        Cafe cafe = cafeService.findById(cafeId);
//        List<Pizza> cafPizzas = pizzaService.findByCafe(cafe);
        model.addAttribute("cafe", cafe);
//        model.addAttribute("cafePizzas", cafPizzas);
        model.addAttribute("pizzas", pizzaService.findAllSortedBy("name"));
        return "pizza/addToCafe";
    }

    @GetMapping("/add_to_menu/{cafe}/{pizza}")
    public String updateMenu(@PathVariable("cafe") int cafeId, @PathVariable("pizza") int pizzaId,
                             Model model) {
        model.addAttribute("user", userUtil.getActiveUser());

        cafeService.addPizzaToCafe(cafeId, pizzaId);

        Cafe cafe = cafeService.findById(cafeId);
        model.addAttribute("cafe", cafe);
        model.addAttribute("pizzas", pizzaService.findAllSortedBy("name"));
        return "redirect:/pizza/add_to_cafe/" + cafeId;
    }

    @GetMapping("/del_from_menu/{cafe}/{pizza}")
    public String deletePizzaFromMenu(@PathVariable("cafe") int cafeId, @PathVariable("pizza") int pizzaId,
                             Model model) {
        model.addAttribute("user", userUtil.getActiveUser());

        cafeService.delPizzaFromCafe(cafeId, pizzaId);

        Cafe cafe = cafeService.findById(cafeId);
        model.addAttribute("cafe", cafe);
        model.addAttribute("pizzas", pizzaService.findAllSortedBy("name"));
        return "redirect:/pizza/add_to_cafe/" + cafeId;
    }

    @GetMapping("/addToFav/{Id}")
    public String addPizzaToPersonFav(@PathVariable("Id") int pizzaId, Model model) {
        Person person = userUtil.getActiveUser();
        if (person == null) {
            return "auth/needLogin";
        }
        model.addAttribute("user", person);
        personService.addPizzaToFav(person, pizzaService.findById(pizzaId));
        return "redirect:/menu";
    }

    @GetMapping("/removeFromFav/{id}")
    public String removeFromPersonFav(@PathVariable("id") int pizzaId) {
        Person person = userUtil.getActiveUser();
        personService.removePizzaFromFav(person, pizzaService.findById(pizzaId));
        return "redirect:/user/myPage";

    }

    @GetMapping("/{id}")
    public String pizzaPage(@PathVariable("id") int pizzaId, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        Pizza pizza = pizzaService.findById(pizzaId);
        model.addAttribute("pizza", pizza);
        List<Ingredient> ing = ingredientService.findByPizza(pizza);


        model.addAttribute("ingredients", ing);
        return "pizza/detail";
    }

    @GetMapping("/checkPrice/{id}")
    public String checkPricePage(@PathVariable("id") int pizzaId, Model model, @ModelAttribute("price") Price price) {
        model.addAttribute("user", userUtil.getActiveUser());

        Pizza pizza = pizzaService.findById(pizzaId);
        model.addAttribute("pizza", pizza);

        List<Ingredient> ing = ingredientService.findByPizza(pizza);
        model.addAttribute("ingredients", ing);

        double calcPrice = pizzaService.getCalculatedPrice(pizza);
        model.addAttribute("recPrice", calcPrice);

        return "pizza/checkPrice";
    }

    @PostMapping("/setPrice/{id}")
    public String setNewPrice(@PathVariable("id") int pizzaId, @ModelAttribute("price") @Valid Price price) {
        Pizza pizza = pizzaService.findById(pizzaId);
        double newPrice = price.getPrice();
        pizzaService.setNewPrice(pizza, newPrice);
        return "redirect:/menu";
    }
}
