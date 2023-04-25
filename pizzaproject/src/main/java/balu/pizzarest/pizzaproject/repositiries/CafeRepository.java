package balu.pizzarest.pizzaproject.repositiries;

import balu.pizzarest.pizzaproject.models.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CafeRepository extends JpaRepository<Cafe, Integer> {

}
