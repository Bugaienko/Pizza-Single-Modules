package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Cafe;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.repositiries.CafeRepository;
import balu.pizza.webapp.repositiries.PizzaRepository;
import balu.pizza.webapp.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class CafeService {
    private final CafeRepository cafeRepository;
    private final PizzaRepository pizzaRepository;

    private final Logger logger = LoggerFactory.getLogger(CafeService.class);

    @Autowired
    public CafeService(CafeRepository cafeRepository, PizzaRepository pizzaRepository) {
        this.cafeRepository = cafeRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public Cafe findById(int id){
        return cafeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Cafe> findAll(){
        return cafeRepository.findAll();
    }


    @Transactional
    public Cafe create(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public List<Cafe> findAllSorted() {
        return cafeRepository.findAll(Sort.by("city").and(Sort.by("title")));
    }

    @Transactional
    public Cafe update(Cafe cafe) {
        Cafe cafe1 = cafeRepository.save(cafe);
        logger.info("Update cafe id={} info", cafe1.getId());
        return cafe1;
    }

    @Transactional
    public void addPizzaToCafe(int cafeId, int pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(NotFoundException::new);
        Cafe cafe1 = cafeRepository.getReferenceById(cafeId);
        Cafe cafe = findById(cafeId);
        List<Pizza> pizzas = cafe.getPizzas();

        if (!pizzas.contains(pizza)){
            pizzas.add(pizza);
        }
        cafe.setPizzas(pizzas);

        cafeRepository.save(cafe);
        logger.info("Add pizza id{} to Cafe id{}", pizza.getId(), cafe.getId());
    }

    @Transactional
    public void delPizzaFromCafe(int cafeId, int pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(NotFoundException::new);
        Cafe cafe =findById(cafeId);
        List<Pizza> pizzas = cafe.getPizzas();

        pizzas.remove(pizza);
        cafe.setPizzas(pizzas);

        cafeRepository.save(cafe);
        logger.info("Remove pizza id{} from Cafe id{}", pizza.getId(), cafe.getId());
    }
}
