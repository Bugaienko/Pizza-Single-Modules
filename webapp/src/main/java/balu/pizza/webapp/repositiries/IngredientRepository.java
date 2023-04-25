package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.models.Pizza;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    List<Ingredient> findByPizzas(Pizza pizza, Sort type);

    Optional<Ingredient> findByName(String name);
}
