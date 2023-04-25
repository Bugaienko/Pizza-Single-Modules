package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Cafe;
import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.models.Pizza;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    List<Pizza> findDistinctPizzaByBase_SizeLikeIgnoreCase(String size, Sort name);
    List<Pizza> findByPersons(Person person);

    Optional<Pizza> findByName(String name);

    List<Pizza> findByCafes(Cafe cafe, Sort name);
}
