package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.*;
import balu.pizza.webapp.services.CafeService;
import balu.pizza.webapp.services.IngredientService;
import balu.pizza.webapp.services.PersonService;
import balu.pizza.webapp.services.PizzaService;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Pizza controller
 *
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

    /**
     * @param pizzaService      Pizzas service
     * @param personService     Persons service
     * @param cafeService       Cafes service
     * @param userUtil          Set users utils
     * @param ingredientService Ingredient service
     */
    @Autowired
    public PizzaController(PizzaService pizzaService, PersonService personService, CafeService cafeService, UserUtil userUtil, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.personService = personService;
        this.cafeService = cafeService;
        this.userUtil = userUtil;
        this.ingredientService = ingredientService;
    }

    /**
     * Cafe menu editing page
     * Admin only
     * <p style="text-align:left;">
     * <img src="doc-files/cafe_addpizza.jpg" style="max-width: 50%;" alt="admin panel">
     * </p>
     *
     * @param cafeId
     * @param model
     * @return Generates a page for menu editing
     */
    @PreAuthorize("hasRole('ADMIN')")
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

    /**
     * The method adds the selected pizza to the cafe menu
     *
     * @param cafeId  Cafe ID
     * @param pizzaId Pizza ID
     * @param model
     * @return if successful - redirects to the cafe page
     */
    @PreAuthorize("hasRole('ADMIN')")
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

    /**
     * The method deleting the selected pizza from the cafe menu
     *
     * @param cafeId  Cafe ID
     * @param pizzaId Pizza ID
     * @param model
     * @return if successful - redirects to the cafe page
     */
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

    /**
     * The method adds the selected pizza to favorites list active user
     *
     * @param pizzaId Pizza ID
     * @param model
     * @return if successful - redirects to the menu page
     */
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

    /**
     * The method deleting the selected pizza from favorites list active user
     *
     * @param pizzaId Pizza ID
     * @return if successful - redirects to the menu page
     */
    @GetMapping("/removeFromFav/{id}")
    public String removeFromPersonFav(@PathVariable("id") int pizzaId) {
        Person person = userUtil.getActiveUser();
        personService.removePizzaFromFav(person, pizzaService.findById(pizzaId));
        return "redirect:/user/myPage";

    }

    /**
     * Page for displaying detailed pizza information
     *
     * <p style="text-align:left;">
     * <img src="doc-files/pizza_info.jpg" style="max-width: 50%;" alt="admin panel">
     * </p>
     *
     * @param pizzaId Pizza ID
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String pizzaPage(@PathVariable("id") int pizzaId, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        Pizza pizza = pizzaService.findById(pizzaId);
        model.addAttribute("pizza", pizza);
        List<Ingredient> ing = ingredientService.findByPizza(pizza);


        model.addAttribute("ingredients", ing);
        return "pizza/detail";
    }

    /**
     * Control and edit pizza price page
     * Admin only
     * <p>
     * The method calculates and displays the cost of the pizza, taking into account its ingredients and size
     * </p>
     * <p style="text-align:left;">
     * <img src="doc-files/check_price.png" style="max-width: 50%;" alt="admin panel">
     * </p>
     *
     * @param pizzaId Pizza ID
     * @param model
     * @param price Price
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
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

    /**
     * The method updates the pizza price in the database
     * @param pizzaId Pizza ID
     * @param price New price
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/setPrice/{id}")
    public String setNewPrice(@PathVariable("id") int pizzaId, @ModelAttribute("price") @Valid Price price) {
        Pizza pizza = pizzaService.findById(pizzaId);
        double newPrice = price.getPrice();
        pizzaService.setNewPrice(pizza, newPrice);
        return "redirect:/menu";
    }
}
