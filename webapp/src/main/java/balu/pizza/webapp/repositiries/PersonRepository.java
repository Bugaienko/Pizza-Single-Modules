package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Search for a user by name
     * @param username Username for searching
     * @return An object that may contain a user or be empty
     */
    Optional<Person> findByUsername(String username);

    /**
     * Search for a user by email
     * @param email email for searching
     * @return An object that may contain a user or be empty
     */
    Optional<Person> findByEmail(String email);
}
