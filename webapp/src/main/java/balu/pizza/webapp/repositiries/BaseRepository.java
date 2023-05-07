package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Base;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Entity Base
 */

public interface BaseRepository extends JpaRepository<Base, Integer> {
    /**
     * Search Base by Name
     * @param name
     * @return An object that may contain a Base or be empty
     */
    Optional<Base> findByName(String name);
    List<Base> findAll();
}
