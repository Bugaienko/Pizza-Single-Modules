package balu.pizza.webapp.repositiries;

import balu.pizza.webapp.models.StackItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StacksRepository extends JpaRepository<StackItem, Integer> {

}
