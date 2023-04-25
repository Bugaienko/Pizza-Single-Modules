package balu.pizzarest.pizzaproject.repositiries;

import balu.pizzarest.pizzaproject.models.Cafe;
import balu.pizzarest.pizzaproject.models.Person;
import balu.pizzarest.pizzaproject.models.Pizza;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findDistinctPizzaByBase_SizeLikeIgnoreCase(String size, Sort name);
    List<Pizza> findByPersons(Person person);
    List<Pizza> findByCafes(Cafe cafe, Sort sort);

    Optional<Pizza> findByName(String name);
}
