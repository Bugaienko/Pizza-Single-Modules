package balu.pizzarest.pizzaproject.services;

import balu.pizzarest.pizzaproject.models.*;
import balu.pizzarest.pizzaproject.repositiries.PizzaRepository;
import balu.pizzarest.pizzaproject.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientService ingredientService;
    private final BaseService baseService;
    private final Logger logger = LoggerFactory.getLogger(PizzaService.class);


    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, IngredientService ingredientService, BaseService baseService) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientService = ingredientService;
        this.baseService = baseService;
    }

    public List<Pizza> findAll() {
        return pizzaRepository.findAll(Sort.by(Sort.Order.by("name")).ascending());
    }

    public List<Pizza> findByPizzaSize(String size) {
        return pizzaRepository.findDistinctPizzaByBase_SizeLikeIgnoreCase(size, Sort.by("name").ascending());
    }

    public Pizza findById(int id) {
        return pizzaRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Cafe> findCafesByPizzaId(int pizzaId) {
        Pizza pizza;
        List<Cafe> cafes = new ArrayList<>();
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(pizzaId);
        if (pizzaOpt.isPresent()) {
            pizza = pizzaOpt.get();
            cafes = pizza.getCafes();
        }
        return cafes;
    }

    public double getCalculatedPrice(Pizza pizza) {
        List<Ingredient> ingredients = pizza.getIngredients();
        Base base = pizza.getBase();
        double multiplier = 1;
        if (base.getSize().equalsIgnoreCase("medium")) {
            multiplier = 1.3;
        } else if (base.getSize().equalsIgnoreCase("large")) {
            multiplier = 1.6;
        }
        double calcPrice = base.getPrice();
        for (Ingredient ing : ingredients) {
            calcPrice += ing.getPrice() * multiplier;
        }
        return calcPrice;
    }

    @Transactional
    public void setNewPrice(Pizza pizza, double newPrice) {
        pizza.setPrice(newPrice);
        pizzaRepository.save(pizza);
        logger.info("Set new price {} for Pizza id={}", newPrice, pizza.getId());
    }

    @Transactional
    public Pizza create(Pizza pizza) {
        logger.info("Create new pizza {}", pizza.getName());
        return pizzaRepository.save(pizza);
    }

    public Optional<Pizza> findByName(String name) {
        return pizzaRepository.findByName(name);
    }

    public List<Pizza> findByPerson(Person person) {
        return pizzaRepository.findByPersons(person);
    }

    public List<Pizza> findAllSortedBy(String param) {
        return pizzaRepository.findAll(Sort.by(param));
    }

    @Transactional
    public Pizza update(int pizzaId, Pizza pizzaData) {
//        Pizza pizza = pizzaRepository.findById(pizzaId).get();

        //TODO save to BD
//        pizza.setBase(pizzaData.getBase());
//        pizza.setName(pizzaData.getName());
//        pizza.setPrice(pizzaData.getPrice());
//        pizza.setIngredients(pizzaData.getIngredients());
//        pizza.setImage(pizzaData.getImage());

        logger.info("Update pizza {}/{}", pizzaData.getId(), pizzaData.getName());
        return pizzaRepository.save(pizzaData);
    }

    public List<Pizza> findByCafes(Cafe cafe) {
        return pizzaRepository.findByCafes(cafe, Sort.by("name"));
    }

    @Transactional
    public Pizza addIngredientsList(Pizza pizza, List<Integer> ingredintsIdList) {
        List<Ingredient> ingredients = pizza.getIngredients();
        for (Integer ing_id: ingredintsIdList){
            Ingredient ingredient = ingredientService.findById(ing_id);
            if (!ingredients.contains(ingredient)){
                ingredients.add(ingredient);
            }
        }
        pizza.setIngredients(ingredients);
        logger.info("Add list of ingredients to pizza id:{}", pizza.getId());
        return pizzaRepository.save(pizza);
    }

    @Transactional
    public Pizza removeIngredientFromPizza(Pizza pizza, int ingredientId) {
        List<Ingredient> ingredients = pizza.getIngredients();
        Ingredient ingredient = ingredientService.findById(ingredientId);
        if (!ingredients.contains(ingredient)){
            logger.info("trying to remove an ingredient id:{} that is not in the pizza id:{}", ingredientId, pizza.getId());
        } else {
            ingredients.remove(ingredient);
            logger.info("remove ingredient id:{} from pizza id:{}", ingredientId, pizza.getId());
            pizza.setIngredients(ingredients);
            pizza = pizzaRepository.save(pizza);
        }
        return pizza;
    }

    @Transactional
    public Pizza changeBase(Pizza pizza, int baseId) {
        Base base = baseService.findById(baseId);
        if (pizza.getBase().getId() != baseId){
            pizza.setBase(base);
            pizza = pizzaRepository.save(pizza);
            logger.info("Change base id{} of pizza id{}", baseId, pizza.getId());
        }
        return pizza;
    }

    @Transactional
    public Pizza createNewFromBody(Pizza pizza, int baseId) {
        Base base = baseService.findById(baseId);
        pizza.setBase(base);
        pizza = pizzaRepository.save(pizza);
        logger.info("Create new pizza id:{} from rest api", pizza.getId());
        return pizza;
    }
}
