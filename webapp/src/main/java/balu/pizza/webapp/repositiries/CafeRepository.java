package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Repository for Entity Cafe
 */

@Service
public interface CafeRepository extends JpaRepository<Cafe, Integer> {

    /**
     * Search cafe by ID
     * @param cafeId
     * @return An object that may contain a Cafe or be empty
     */
    Optional<Cafe> findById(int cafeId);

}
