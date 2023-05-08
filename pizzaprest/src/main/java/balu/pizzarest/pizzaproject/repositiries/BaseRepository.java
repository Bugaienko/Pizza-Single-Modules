package balu.pizzarest.pizzaproject.repositiries;

import balu.pizzarest.pizzaproject.models.Base;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface BaseRepository extends JpaRepository<Base, Integer> {
    Optional<Base> findByName(String name);
    List<Base> findAll();
}
