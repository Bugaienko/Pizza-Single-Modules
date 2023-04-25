package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.TypeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypesRepository extends JpaRepository<TypeIngredient, Integer> {

    Optional<TypeIngredient> findByName(String name);
}
