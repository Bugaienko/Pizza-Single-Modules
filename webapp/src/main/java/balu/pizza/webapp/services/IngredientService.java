package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.models.TypeIngredient;
import balu.pizza.webapp.repositiries.IngredientRepository;
import balu.pizza.webapp.repositiries.TypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class IngredientService {

    private final Logger logger = LoggerFactory.getLogger(IngredientService.class);
    private final IngredientRepository ingredientRepository;
    private final TypesRepository typesRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, TypesRepository typesRepository) {
        this.ingredientRepository = ingredientRepository;
        this.typesRepository = typesRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public List<Ingredient> findByPizza(Pizza pizza) {
        return ingredientRepository.findByPizzas(pizza, Sort.by("type"));
    }

    public List<Ingredient> findAllSort() {
        return ingredientRepository.findAll(Sort.by("type"));
    }


    public Optional<Ingredient> findIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    @Transactional
    public void create(Ingredient ingredient, TypeIngredient typeIngredient) {
        TypeIngredient type = typesRepository.getById(ingredient.getId());

        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(ingredient.getName());
        newIngredient.setPrice(ingredient.getPrice());
        newIngredient.setImage(ingredient.getImage());
        newIngredient.setType(type);

        ingredientRepository.save(newIngredient);
        logger.info("Create new ingredient {}", ingredient.getName());
    }

    public Ingredient findById(int ingrId) {
        Optional<Ingredient> ingredient =  ingredientRepository.findById(ingrId);
        if (ingredient.isPresent()) {
            return ingredient.get();
        }
        else return null;
    }

    @Transactional
    public Ingredient update(Ingredient ingredient) {
        logger.info("Update ingredient id={}", ingredient.getId());
        return ingredientRepository.save(ingredient);
    }
}
