package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.models.TypeIngredient;
import balu.pizza.webapp.repositiries.IngredientRepository;
import balu.pizza.webapp.repositiries.TypesRepository;
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
 * Ingredient Service
 *
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class IngredientService {

    private final Logger logger = LoggerFactory.getLogger(IngredientService.class);
    private final IngredientRepository ingredientRepository;
    private final TypesRepository typesRepository;

    /**
     *
     * @param ingredientRepository Ingredient Repository
     * @param typesRepository Type of Ingredient Repository
     */
    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, TypesRepository typesRepository) {
        this.ingredientRepository = ingredientRepository;
        this.typesRepository = typesRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    /**
     * List of all ingredients by Pizza
     * @param pizza
     * @return  Sorted list of ingredients that are part of the pizza
     */
    public List<Ingredient> findByPizza(Pizza pizza) {
        return ingredientRepository.findByPizzas(pizza, Sort.by("type"));
    }

    /**
     *
     * @return List of all ingredients from DB
     */
    public List<Ingredient> findAllSort() {
        return ingredientRepository.findAll(Sort.by("type"));
    }


    /**
     * Searches for an Ingredient by Name
     * @param name
     * @return An object that may contain a Ingredient or be empty
     */
    public Optional<Ingredient> findIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    /**
     * Creates a new ingredient based on the data from the form
     * @param ingredient ingredient
     * @param typeIngredient Type of ingredient
     * @return Saved Ingredient
     */
    @Transactional
    public Ingredient create(Ingredient ingredient, TypeIngredient typeIngredient) {
//        TypeIngredient type = typesRepository.getById(ingredient.getId());
        TypeIngredient type = typesRepository.findById(typeIngredient.getId()).orElseThrow(NotFoundException::new);

        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(ingredient.getName());
        newIngredient.setPrice(ingredient.getPrice());
        newIngredient.setImage(ingredient.getImage());
        newIngredient.setType(type);

        logger.info("Create new ingredient {}", ingredient.getName());
        return ingredientRepository.save(newIngredient);
    }

    /**
     * Searches for an ingredient by ID
     * @param ingrId
     * @return An object that may contain a Ingredient or be empty
     */
    public Ingredient findById(int ingrId) {
        return   ingredientRepository.findById(ingrId).orElseThrow(NotFoundException::new);

    }

    /**
     * Updates Ingredient in DB
     * @param ingredient
     * @return updated Ingredient
     */
    @Transactional
    public Ingredient update(Ingredient ingredient) {
        logger.info("Update ingredient id={}", ingredient.getId());
        return ingredientRepository.save(ingredient);
    }
}
