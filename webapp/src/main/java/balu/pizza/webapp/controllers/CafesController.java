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
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/cafe")
public class CafesController {

    private final PizzaService pizzaService;
    private final CafeService cafeService;
    private final UserUtil userUtil;

    @Autowired
    public CafesController(PizzaService pizzaService, CafeService cafeService, UserUtil userUtil) {
        this.pizzaService = pizzaService;
        this.cafeService = cafeService;
        this.userUtil = userUtil;
    }

    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("cafes", cafeService.findAllSorted());
        return "cafe/cafes";
    }

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
