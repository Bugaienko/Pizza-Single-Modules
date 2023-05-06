package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.Cafe;
import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.services.CafeService;
import balu.pizza.webapp.services.PizzaService;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Cafe Controller
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/cafe")
public class CafesController {

    private final PizzaService pizzaService;
    private final CafeService cafeService;
    private final UserUtil userUtil;

    /**
     *
     * @param pizzaService Pizza service
     * @param cafeService Cafe service
     * @param userUtil Set users utils
     */
    @Autowired
    public CafesController(PizzaService pizzaService, CafeService cafeService, UserUtil userUtil) {
        this.pizzaService = pizzaService;
        this.cafeService = cafeService;
        this.userUtil = userUtil;
    }

    /**
     * Main page cafe section
     * <p style="text-align:left;">
     * <img src="doc-files/cafe.png" style="max-width: 50%;" alt="admin panel">
     * </p>
     *
     * @param model
     * @return Generates a page for the route /cafe
     */
    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("cafes", cafeService.findAllSorted());
        return "cafe/cafes";
    }

    /**
     * The page displays the presence of pizza in the cafe menu
     * <p style="text-align:left;">
     * <img src="doc-files/cafe_pizza.png" style="max-width: 50%;" alt="admin panel">
     * </p>
     * @param pizzaId
     * @param model
     * @return Generates a page for the route /cafe/pizza/id
     */
    @GetMapping("/pizza/{pizzaId}")
    public String pizzaAvailability(@PathVariable("pizzaId") int pizzaId, Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (user == null) {
            return "auth/needLogin";
        }
        Pizza pizza = pizzaService.findById(pizzaId);
        model.addAttribute("pizza", pizza);
        return "cafe/pizzaSearch";
    }

    /**
     * Page displaying information about the cafe and its menu
     * <p style="text-align:left;">
     * <img src="doc-files/add_base.jpg" style="max-width: 50%;" alt="admin panel">
     * </p>
     * @param cafeId cafe identifier
     * @param model
     * @return Generates a page for the route /cafe/id
     */
    @GetMapping("/{id}")
    public String showCafe(@PathVariable("id") int cafeId, Model model){
        model.addAttribute("user", userUtil.getActiveUser());
        Cafe cafe = cafeService.findById(cafeId);

        List<Pizza> pizzas = cafe.getSortedPizza();

        model.addAttribute("cafe", cafe);
        model.addAttribute("pizzas", pizzas);
        return "cafe/showCafe";
    }
}
