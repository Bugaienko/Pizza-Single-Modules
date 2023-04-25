package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);
    Optional<Person> findByEmail(String email);
}
