package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.services.CafeService;
import balu.pizza.webapp.services.PizzaService;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller
 *
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/")
public class MainController {

    private final CafeService cafeService;
    private final PizzaService pizzaService;
    private final UserUtil userUtil;


    /**
     * @param cafeService  Cafes service
     * @param pizzaService Pizzas service
     * @param userUtil     Set users utils
     */
    @Autowired
    public MainController(CafeService cafeService, PizzaService pizzaService, UserUtil userUtil) {
        this.cafeService = cafeService;
        this.pizzaService = pizzaService;
        this.userUtil = userUtil;
    }

    /**
     * Home page
     *
     * @param model
     * @return Generates a page for the route /
     */
    @GetMapping("/")
    public String mainPage(Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        model.addAttribute("cafe", cafeService.findById(1));
        return "main/index";
    }

    /**
     * Menu page
     * <p style="text-align:left;">
     * <img src="doc-files/page_menu.jpg" style="max-width: 50%;" alt="admin panel">
     * </p>
     *
     * @param model
     * @return Generates a page for the route /menu
     */
    @GetMapping("/menu")
    public String menuPage(Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        model.addAttribute("pizzas", pizzaService.findAll());
        model.addAttribute("pizzas_s", pizzaService.findByPizzaSize("small"));
        model.addAttribute("pizzas_M", pizzaService.findByPizzaSize("medium"));
        model.addAttribute("pizzas_Xl", pizzaService.findByPizzaSize("large"));
        return "main/menu";
    }

    @GetMapping("/about")
    private String aboutPage(Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        return "main/about";
    }

    @GetMapping("/contact")
    private String contactPage(Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        return "main/contact";
    }


}
