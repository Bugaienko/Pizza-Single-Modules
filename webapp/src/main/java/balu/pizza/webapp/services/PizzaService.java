package balu.pizza.webapp.services;

import balu.pizza.webapp.models.*;
import balu.pizza.webapp.repositiries.PizzaRepository;
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
    private final Logger logger = LoggerFactory.getLogger(PizzaService.class);


    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> findAll() {
        return pizzaRepository.findAll(Sort.by(Sort.Order.by("name")).ascending());
    }

    public List<Pizza> findByPizzaSize(String size) {
        return pizzaRepository.findDistinctPizzaByBase_SizeLikeIgnoreCase(size, Sort.by("name").ascending());
    }

    public Pizza findById(int id) {
        return pizzaRepository.findById(id).get();
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

    public List<Pizza> findByCafe(Cafe cafe) {
        return pizzaRepository.findByCafes(cafe, Sort.by("name").ascending());
    }
}
