package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Cafe;
import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.models.Pizza;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Entity Base
 */

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    /**
     * Search for pizza by field size in entity base
     * @param size Size
     * @param name the list will be sorted by pizza name
     * @return Sorted by name, the list of pizzas that have the size 'size'
     */
    List<Pizza> findDistinctPizzaByBase_SizeLikeIgnoreCase(String size, Sort name);

    /**
     * Search for pizza by entity Person
     * @param person Entity Person
     * @return list of pizzas that are associated with the user
     */
    List<Pizza> findByPersons(Person person);

    /**
     * Search pizza by name
     * @param name
     * @return An object that may contain a Pizza or be empty
     */
    Optional<Pizza> findByName(String name);

    /**
     * Search for pizza by entity Cafe
     * @param cafe
     * @param name The list will be sorted by name
     * @return list of pizzas that are associated with the cafe
     */
    List<Pizza> findByCafes(Cafe cafe, Sort name);
}
