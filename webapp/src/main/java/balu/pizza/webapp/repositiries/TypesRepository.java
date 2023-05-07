package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.TypeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Type of Ingredient Repository
 */

@Repository
public interface TypesRepository extends JpaRepository<TypeIngredient, Integer> {

    /**
     * Search for a Type of Ingredient by name
     * @param name Ingredient name
     * @return An object that may contain a Type of Ingredient or be empty
     */
    Optional<TypeIngredient> findByName(String name);
}
