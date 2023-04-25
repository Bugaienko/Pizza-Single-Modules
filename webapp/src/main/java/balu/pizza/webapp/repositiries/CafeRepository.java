package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CafeRepository extends JpaRepository<Cafe, Integer> {

}
