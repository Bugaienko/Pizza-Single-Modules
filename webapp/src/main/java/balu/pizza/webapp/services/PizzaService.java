package balu.pizza.webapp.services;

import balu.pizza.webapp.models.*;
import balu.pizza.webapp.repositiries.PizzaRepository;
import balu.pizza.webapp.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Pizza service
 *
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientService ingredientService;
    private final Logger logger = LoggerFactory.getLogger(PizzaService.class);


    /**
     * Implementing dependencies
     * @param pizzaRepository Pizza Repository
     * @param ingredientService Ingredient Service
     */
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, IngredientService ingredientService) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientService = ingredientService;
    }

    /**
     * Searches all pizzas
     * @return Sorted by name list of all pizzas from DB
     */
    public List<Pizza> findAll() {
        return pizzaRepository.findAll(Sort.by(Sort.Order.by("name")).ascending());
    }

    /**
     * Searches all pizzas by base size
     * @param size Base size
     * @return Sorted by name list of all pizzas with size 'size'
     */
    public List<Pizza> findByPizzaSize(String size) {
        return pizzaRepository.findDistinctPizzaByBase_SizeLikeIgnoreCase(size, Sort.by("name").ascending());
    }

    /**
     * Searches pizza by id
     * @param id
     * @return if successful: Pizza
     * @throws NotFoundException if pizza was not found
     */
    public Pizza findById(int id) {
        return pizzaRepository.findById(id).orElseThrow(NotFoundException::new);
    }


    /**
     * The method calculates the cost of pizza
     * the size of the pizza and all of its ingredients are taken into account
     * @param pizza
     * @return Calculated price
     */
    public double getCalculatedPrice(Pizza pizza) {
        List<Ingredient> ingredients = ingredientService.findByPizza(pizza);
//        List<Ingredient> ingredients = pizza.getIngredients();
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

    /**
     * Method for changing the price of a pizza
     * @param pizza Pizza
     * @param newPrice new Price
     */
    @Transactional
    public void setNewPrice(Pizza pizza, double newPrice) {
        pizza.setPrice(newPrice);
        pizzaRepository.save(pizza);
        logger.info("Set new price {} for Pizza id={}", newPrice, pizza.getId());
    }

    /**
     * saves new pizza to DB
     * @param pizza
     * @return saved pizza
     */
    @Transactional
    public Pizza create(Pizza pizza) {
        logger.info("Create new pizza {}", pizza.getName());
        return pizzaRepository.save(pizza);
    }

    /**
     * Searches pizza by name
     * @param name pizza name
     * @return An object that may contain a Pizza or be empty
     */
    public Optional<Pizza> findByName(String name) {
        return pizzaRepository.findByName(name);
    }

    /**
     * Searches all pizzas associated with the user
     * @param person Entity Person
     * @return list of all pizzas associated with the user
     */
    public List<Pizza> findByPerson(Person person) {
        return pizzaRepository.findByPersons(person);
    }

    /**
     * Searches all pizza in DB
     * @param param Pizza sorting parameter
     * @return Sorted by param list of all pizzas from BD
     */
    public List<Pizza> findAllSortedBy(String param) {
        return pizzaRepository.findAll(Sort.by(param));
    }

    /**
     * Method for updating pizza fields with data from a form
     * @param pizzaData Data from form
     * @return updated Pizza
     */
    @Transactional
    public Pizza update(Pizza pizzaData) {
        Pizza pizza = findById(pizzaData.getId());
        pizza.setName(pizzaData.getName());
        pizza.setBase(pizzaData.getBase());
        pizza.setIngredients(pizzaData.getIngredients());
        pizza.setImage(pizzaData.getImage());
        pizza.setPrice(pizzaData.getPrice());

        logger.info("Update pizza {}/{}", pizzaData.getId(), pizzaData.getName());
        return pizzaRepository.save(pizza);
    }

    /**
     * Searches of all pizzas associated with the cafe
     * @param cafe Entity Cafe
     * @return List of all pizzas associated with the cafe
     */
    public List<Pizza> findByCafe(Cafe cafe) {
        return pizzaRepository.findByCafes(cafe, Sort.by("name").ascending());
    }
}
