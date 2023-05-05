package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Cafe;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.util.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CafeServiceTest {

    private final CafeService cafeService;
    private final PizzaService pizzaService;
    private static List<Cafe> cafesTestList;

    @Autowired
    CafeServiceTest(CafeService cafeService, PizzaService pizzaService) {
        this.cafeService = cafeService;
        this.pizzaService = pizzaService;
    }

    static {
        cafesTestList = new ArrayList<>();
        cafesTestList.add(new Cafe("Test title2", "test city2", "cafemail2@mail.com", "+49-111-222-333-44", "08:00", "20:00"));
        cafesTestList.add(new Cafe("Test title3", "test city3", "cafemail3@mail.com", "+49-222-333-444-55", "09:00", "22:00"));
        cafesTestList.add(new Cafe("Test title4", "test city4", "cafemail4@mail.com", "+49-333-444-555-66", "09:00", "22:00"));
    }


    @Test
    void initCafesList() {
        for (Cafe cafe : cafesTestList) {
            cafeService.create(cafe);
        }
    }

    @Test
    void cafeServiceFindByIdTest() {
        Cafe cafe = new Cafe("Test title5", "test city5", "cafemail5@mail.com", "+49-111-222-333-44", "09:00", "21:00");
        Cafe created = cafeService.create(cafe);
        Cafe foundCafe = cafeService.findById(created.getId());
        assertEquals(created.getId(), foundCafe.getId());
        assertEquals(created.getTitle(), foundCafe.getTitle());
        assertThrows(NotFoundException.class, () -> {
            cafeService.findById(1111000111);
        });
    }

    @Test
    void cafeServiceFindAllTest() {
        int size = cafeService.findAll().size();
        Cafe cafe = new Cafe("Test title6", "test city6", "cafemail6@mail.com", "+49-111-222-333-44", "08:00", "20:00");
        cafeService.create(cafe);
        assertEquals(size + 1, cafeService.findAll().size());
    }

    @Test
    void cafeServiceUpdateTest() {
        Cafe cafe = new Cafe("Test title7", "test city7", "cafemail7@mail.com", "+49-111-222-333-44", "09:00", "21:00");
        Cafe created = cafeService.create(cafe);
        Cafe foundCafe = cafeService.findById(created.getId());

        String checkedTitle = "Check Title";
        foundCafe.setTitle(checkedTitle);
        cafeService.update(foundCafe);
        Cafe checkMeCafe = cafeService.findById(foundCafe.getId());

        assertEquals(foundCafe.getId(), checkMeCafe.getId());
        assertEquals(foundCafe.getCity(), checkMeCafe.getCity());
        assertEquals(checkedTitle, checkMeCafe.getTitle());
    }

    @Test
    void cafeServiceFindAllSortedTest() {
        List<Cafe> cafes = cafeService.findAll();
        List<Cafe> sortedCafes = cafeService.findAllSorted();
        System.out.println(cafes.size());

        assertEquals(cafes.size(), sortedCafes.size());
    }

    @Test
    @DisplayName("Test Add and Del Pizza to/from Cafe")
    void cafeServiceAddPizzaToCafeAndDelPizzaFromTest() {
        Cafe cafeNew = new Cafe("Test title8", "test city8", "cafemail8@mail.com", "+49-111-222-333-44", "09:00", "21:00");
        Cafe created = cafeService.create(cafeNew);


        int cafeId = created.getId();
        Cafe cafe = cafeService.findById(cafeId);

        List<Pizza> pizzaCafeList = pizzaService.findByCafe(cafe);
        Pizza pizza = new Pizza("New pizza");
        pizza = pizzaService.create(pizza);
        cafeService.addPizzaToCafe(cafeId, pizza.getId());
        Cafe cafeSaved = cafeService.findById(cafeId);
        List<Pizza> updatedPizzasList = pizzaService.findByCafe(cafeSaved);

        assertEquals(updatedPizzasList.size(), pizzaCafeList.size() + 1);
        assertTrue(updatedPizzasList.contains(pizza));
        assertThrows(NotFoundException.class, () -> {
            cafeService.addPizzaToCafe(1,1111000111);
        });
        assertThrows(NotFoundException.class, () -> {
            cafeService.addPizzaToCafe(1111100011, 1);
        });

        cafeService.delPizzaFromCafe(cafeId, pizza.getId());
        List<Pizza> afterDelPizzasList = pizzaService.findByCafe(cafeSaved);

        assertFalse(afterDelPizzasList.contains(pizza));
        assertEquals(updatedPizzasList.size() -1, afterDelPizzasList.size());
        assertThrows(NotFoundException.class, () -> {
            cafeService.delPizzaFromCafe(1,1111000111);
        });
        assertThrows(NotFoundException.class, () -> {
            cafeService.delPizzaFromCafe(1111100011, 1);
        });

    }


}