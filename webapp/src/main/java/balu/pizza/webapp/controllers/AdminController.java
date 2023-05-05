package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.*;
import balu.pizza.webapp.services.*;
import balu.pizza.webapp.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserUtil userUtil;
    private final TypeService typeService;
    private final IngredientService ingredientService;
    private final BaseService baseService;
    private final PizzaService pizzaService;
    private final CafeService cafeService;
    private final IngredientValidator ingredientValidator;
    private final TypeValidator typeValidator;
    private final BaseValidator baseValidator;
    private final PizzaValidator pizzaValidator;

    @Autowired
    public AdminController(UserUtil userUtil, TypeService typeService, IngredientService ingredientService, BaseService baseService, PizzaService pizzaService, CafeService cafeService, IngredientValidator ingredientValidator, TypeValidator typeValidator, BaseValidator baseValidator, PizzaValidator pizzaValidator) {
        this.userUtil = userUtil;
        this.typeService = typeService;
        this.ingredientService = ingredientService;
        this.baseService = baseService;
        this.pizzaService = pizzaService;
        this.cafeService = cafeService;
        this.ingredientValidator = ingredientValidator;
        this.typeValidator = typeValidator;
        this.baseValidator = baseValidator;
        this.pizzaValidator = pizzaValidator;
    }

    @GetMapping()
    public String indexAdmin(Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (!user.getRole().toLowerCase().contains("admin")) {
            return "admin/accessDenied";
        }

        return "admin/index";
    }

    @GetMapping("/add/ingredient")
    public String addIngredient(Model model,
                                @ModelAttribute("ingredient") Ingredient ingredient,
                                @ModelAttribute("type") TypeIngredient typeIngredient) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (!user.getRole().toLowerCase().contains("admin")) {
            return "admin/accessDenied";
        }
        List<TypeIngredient> types = typeService.findAllSorted();
        model.addAttribute("types", types);
        return "admin/addIngredient";
    }

    @PostMapping("/add/ingredient")
    public String createIngredient(@ModelAttribute("ingredient") @Valid Ingredient ingredient, BindingResult bindingResult, @ModelAttribute("type") TypeIngredient typeIngredient, Model model) {
        ingredientValidator.validate(ingredient, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userUtil.getActiveUser());
            return "admin/addIngredient";
        }

        ingredientService.create(ingredient, typeIngredient);
        model.addAttribute("user", userUtil.getActiveUser());

        return "redirect:/admin";
    }

    @GetMapping("/add/type_ingredient")
    public String addTypeIngredient(@ModelAttribute("type") TypeIngredient type, Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (!user.getRole().toLowerCase().contains("admin")) {
            return "admin/accessDenied";
        }
        return "admin/addTypeIngredient";
    }

    @PostMapping("/add/type_ingredient")
    public String createType(@ModelAttribute("type") @Valid TypeIngredient type, BindingResult bindingResult, Model model) {
        typeValidator.validate(type, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userUtil.getActiveUser());
            return "admin/addTypeIngredient";
        }
        typeService.create(type);
        return "redirect:/admin";
    }

    @GetMapping("/add/base")
    public String addBase(@ModelAttribute("base") Base base, Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (!user.getRole().toLowerCase().contains("admin")) {
            return "admin/accessDenied";
        }
        return "admin/addBase";
    }

    @PostMapping("add/base")
    public String createBase(@ModelAttribute("base") @Valid Base base, BindingResult bindingResult, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        baseValidator.validate(base, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/addBase";
        }
        baseService.create(base);
        return "redirect:/admin";
    }

    @GetMapping("edit/type")
    public String choiceTypeForEdit(@ModelAttribute("type") TypeIngredient type, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        List<TypeIngredient> types = typeService.findAllSorted();
        model.addAttribute("types", types);
        return "admin/choiceType";
    }

    @PostMapping("edit/type")
    public String editType(@ModelAttribute("type") TypeIngredient type, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        TypeIngredient type1 = typeService.findById(type.getId());
        model.addAttribute("type", type1);
        return "admin/editType";
    }

    @PatchMapping("edit/type/{id}")
    public String updateType(@ModelAttribute("type") @Valid TypeIngredient type, BindingResult bindingResult, Model model,
                             @PathVariable("id") int typeId) {
        model.addAttribute("user", userUtil.getActiveUser());
        typeValidator.validate(type, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/editType";
        }
        typeService.updateName(type);
        return "redirect:/admin";

    }

    @GetMapping("base/edit")
    public String choiceBase(@ModelAttribute("base") Base base, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());

        List<Base> bases = baseService.findAllSorted();
        model.addAttribute("bases", bases);
        return "admin/choiceBase";
    }

    @PostMapping("base/edit")
    public String editBase(@ModelAttribute("base") Base base, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        Base base1 = baseService.findById(base.getId());
        model.addAttribute("base", base1);
        return "admin/editBase";
    }

    @PatchMapping("base/edit/{id}")
    public String updateBase(@ModelAttribute("base") @Valid Base base, BindingResult bindingResult,
                             @PathVariable("id") int baseId, Model model) {

        model.addAttribute("user", userUtil.getActiveUser());

        if (bindingResult.hasErrors()) {
            return "admin/editBase";
        }

        baseService.update(base);
        return "redirect:/admin";

    }

    @GetMapping("/add/pizza")
    public String addPizza(@ModelAttribute("pizza") Pizza pizza,
                           @ModelAttribute("base") Base base,
                           @ModelAttribute("ingredient") Ingredient ingredient,
                           Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (!user.getRole().toLowerCase().contains("admin")) {
            return "admin/accessDenied";
        }
        List<Ingredient> ingredients = ingredientService.findAllSort();
        List<Base> bases = baseService.findAllSorted();
        int ingCount = ingredients.size();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("bases", bases);
        model.addAttribute("ingCount", ingCount);
        return "admin/addPizza";
    }

    @PostMapping("/add/pizza")
    public String creatPizza(@ModelAttribute("pizza") @Valid Pizza pizza, BindingResult bindingResult, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        pizzaValidator.validate(pizza, bindingResult);

        if (bindingResult.hasErrors()) {
            List<Ingredient> ingredients = ingredientService.findAllSort();
            List<Base> bases = baseService.findAllSorted();
            model.addAttribute("ingredients", ingredients);
            model.addAttribute("bases", bases);
            return "admin/addPizza";
        }
        System.out.println(pizza);
        System.out.println(pizza.getIngredients());
        Pizza newPizza = pizzaService.create(pizza);
        if (newPizza != null) {
            return "redirect:/pizza/" + newPizza.getId();
        }
        return "redirect:/admin";
    }

    @GetMapping("edit/pizza")
    public String selectPizzaForEdit(Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("pizzas", pizzaService.findAllSortedBy("name"));
        return "admin/choicePizza";
    }

    @GetMapping("/edit/pizza/{id}")
    public String editPizza(@PathVariable("id") int pizzaId, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        Pizza pizza = pizzaService.findById(pizzaId);
        List<Ingredient> ingredients = ingredientService.findAllSort();
        List<Base> bases = baseService.findAllSorted();
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("bases", bases);
        model.addAttribute("ingCount", ingredients.size());

        return "admin/editPizza";
    }

    @PatchMapping("/edit/pizza/{id}")
    public String updatePizza(@ModelAttribute("pizza") @Valid Pizza pizza, BindingResult bindingResult, Model model,
                              @PathVariable("id") int pizzaId) {
        model.addAttribute("user", userUtil.getActiveUser());
        pizzaValidator.validate(pizza, pizzaId, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Ingredient> ingredients = ingredientService.findAllSort();
            List<Base> bases = baseService.findAllSorted();
            model.addAttribute("ingredients", ingredients);
            model.addAttribute("bases", bases);
            return "admin/editPizza";
        }

//        pizzaService.update(pizzaId, pizza);
        pizzaService.update(pizza);

        return "redirect:/admin";
    }

    @GetMapping("edit/ingredient")
    public String choiceIngredient(Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        List<Ingredient> ingredients = ingredientService.findAllSort();
        model.addAttribute("ingredients", ingredients);
        return "admin/choiceIngr";
    }

    @GetMapping("edit/ingredient/{id}")
    public String editIngredient(@PathVariable("id") int ingrId, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());
        Ingredient ingredient = ingredientService.findById(ingrId);
        List<TypeIngredient> types = typeService.findAllSorted();
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("types", types);
        return "admin/editIngr";
    }

    @PatchMapping("edit/ingredient/{id}")
    public String updateIngredient(@ModelAttribute("ingredient") @Valid Ingredient ingredient, BindingResult bindingResult, Model model,
                                   @PathVariable("id") int ingrId) {
        model.addAttribute("user", userUtil.getActiveUser());
        ingredientValidator.validate(ingredient, ingrId, bindingResult);

        if (bindingResult.hasErrors()) {
            List<TypeIngredient> types = typeService.findAllSorted();
            model.addAttribute("types", types);
            return "admin/editIngr";
        }
        ingredientService.update(ingredient);

        return "redirect:/admin";
    }

    @GetMapping("/add/cafe")
    public String addCafe(@ModelAttribute("cafe") Cafe cafe, Model model) {
        Person user = userUtil.getActiveUser();
        model.addAttribute("user", user);
        if (!user.getRole().toLowerCase().contains("admin")) {
            return "admin/accessDenied";
        }
        return "admin/addCafe";
    }

    @PostMapping("/add/cafe")
    public String createCafe(@ModelAttribute("cafe") @Valid Cafe cafe, BindingResult bindingResult, Model model) {
        model.addAttribute("user", userUtil.getActiveUser());

        if (bindingResult.hasErrors()) {
            return "admin/addCafe";
        }

        Cafe newCafe = cafeService.create(cafe);
        System.out.println(newCafe);
        if (newCafe != null) {
            return "redirect:/cafe/" + newCafe.getId();
        }

        return "redirect:/admin";
    }

    @GetMapping("/edit/cafe")
    public String selectCafeForEdit(Model model){
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("cafes", cafeService.findAllSorted());
        return "admin/choiceCafe";
    }

    @GetMapping("/edit/cafe/{id}")
    public String editCafePage(@PathVariable("id") int cafeId, Model model,
                               @ModelAttribute("cafe") Cafe cafe){
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("cafe", cafeService.findById(cafeId));
        return "admin/editCafe";
    }

    @PatchMapping("/edit/cafe/{id}")
    public String updateCafe(@ModelAttribute("cafe") @Valid Cafe cafe, BindingResult bindingResult, Model model,
                             @PathVariable("id") int cafeId){
        model.addAttribute("user", userUtil.getActiveUser());
        if (bindingResult.hasErrors()){
            return "admin/editCafe";
        }
        System.out.println("Contr admin -> " + cafe);
//        System.out.println("Contr admin -> " + cafe.getSortedPizza());

        cafeService.update(cafe);

        return "redirect:/admin";

    }

}
