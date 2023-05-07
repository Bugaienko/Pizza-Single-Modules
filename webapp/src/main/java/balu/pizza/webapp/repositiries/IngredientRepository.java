package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.models.Pizza;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Entity Ingredient
 */

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    /**
     * Selection from the pizza ingredient database
     * @param pizza Pizza
     * @param type The list will be sorted by type of ingredient
     * @return Sorted list of ingredients that are part of the pizza
     */
    List<Ingredient> findByPizzas(Pizza pizza, Sort type);

    /**
     * Search Ingredient by Name
     * @param name
     * @return An object that may contain an Ingredient or be empty
     */
    Optional<Ingredient> findByName(String name);
}
