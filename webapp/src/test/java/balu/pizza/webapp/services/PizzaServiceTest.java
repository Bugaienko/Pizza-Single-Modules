package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Base;
import balu.pizza.webapp.models.Pizza;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PizzaServiceTest {
    private final PizzaService pizzaService;
    private static List<Pizza> pizzas;

    @Autowired
    PizzaServiceTest(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @BeforeAll
    static void initPizzas() {
        pizzas = new ArrayList<>();
        Pizza pizza = new Pizza("Pizza1", 25);
        Base base = new Base("Small","Base1", 5);
        pizza.setBase(base);
        pizzas.add(pizza);

        Pizza pizza1 = new Pizza("Pizza2", 30);
        Base base1 = new Base("Small","Base2", 4);
        pizza1.setBase(base1);
        pizzas.add(pizza1);

        Pizza pizza2 = new Pizza("Pizza3", 35);
        Base base2 = new Base("Medium","Base3", 6);
        pizza2.setBase(base2);
        pizzas.add(pizza2);

        Pizza pizza3 = new Pizza("Pizza4", 35);
        pizza3.setBase(base2);
        pizzas.add(pizza3);

        Pizza pizza4 = new Pizza("Pizza5", 40);
        Base base4 = new Base("Large","Base4", 7);
        pizza4.setBase(base4);
        pizzas.add(pizza4);

        Pizza pizza5 = new Pizza("Pizza6", 41);
        pizza5.setBase(base4);
        pizzas.add(pizza5);

    }



    @Test
    void findAll() {
    }

    @Test
    void findByPizzaSize() {
    }

    @Test
    void findById() {
    }
}