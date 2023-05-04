package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CafeRepository extends JpaRepository<Cafe, Integer> {

    Optional<Cafe> findById(int cafeId);

}
