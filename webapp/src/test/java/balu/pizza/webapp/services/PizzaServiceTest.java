package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Base;
import balu.pizza.webapp.models.Ingredient;
import balu.pizza.webapp.models.Pizza;
import balu.pizza.webapp.models.TypeIngredient;
import balu.pizza.webapp.util.NotFoundException;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaServiceTest {
    private final PizzaService pizzaService;
    private final BaseService baseService;
    private final IngredientService ingredientService;
    private final TypeService typeService;
    private static List<Pizza> pizzas;

    @Autowired
    PizzaServiceTest(PizzaService pizzaService, BaseService baseService, IngredientService ingredientService, TypeService typeService) {
        this.pizzaService = pizzaService;
        this.baseService = baseService;
        this.ingredientService = ingredientService;
        this.typeService = typeService;
    }

    @BeforeAll
    static void initPizzas() {
        pizzas = new ArrayList<>();
        Pizza pizza = new Pizza("Pizza1", 25);
        Base base = new Base("Small", "Base1", 5);
        pizza.setBase(base);
        pizzas.add(pizza);

        Pizza pizza1 = new Pizza("Pizza2", 30);
        Base base1 = new Base("Small", "Base2", 4);
        pizza1.setBase(base1);
        pizzas.add(pizza1);

        Pizza pizza2 = new Pizza("Pizza3", 35);
        Base base2 = new Base("Medium", "Base3", 6);
        pizza2.setBase(base2);
        pizzas.add(pizza2);

        Pizza pizza3 = new Pizza("Pizza4", 35);
        pizza3.setBase(base2);
        pizzas.add(pizza3);

        Pizza pizza4 = new Pizza("Pizza5", 40);
        Base base4 = new Base("Large", "Base4", 7);
        pizza4.setBase(base4);
        pizzas.add(pizza4);

        Pizza pizza5 = new Pizza("Pizza6", 41);
        pizza5.setBase(base4);
        pizzas.add(pizza5);

    }


    @Test
    @Order(1)
    void initDbForTests() {
        for (Pizza pizza : pizzas) {
            Base base = pizza.getBase();
            base = baseService.create(base);
            pizza.setBase(base);
            pizzaService.create(pizza);
        }
    }

    @Test
    @Order(2)
    void pizzaServiceFindAllTest() {
        List<Pizza> pizzaList = pizzaService.findAll();
        Pizza pizza7 = new Pizza("Pizza7", 33);
        Base base2 = new Base("Medium", "Base5", 6);
        base2 = baseService.create(base2);
        pizza7.setBase(base2);
        pizzaService.create(pizza7);
        List<Pizza> pizzaList2 = pizzaService.findAll();
        assertEquals(pizzaList.size() + 1, pizzaList2.size());
    }

    @Test
    @Order(3)
    void pizzaServiceFindByPizzaSizeTest() {
        List<Pizza> mediumPizzas = pizzaService.findByPizzaSize("Medium");
        Pizza pizza7 = new Pizza("Pizza8", 33);
        Base base6 = new Base("Medium", "Base6", 6);
        base6 = baseService.create(base6);
        pizza7.setBase(base6);
        pizzaService.create(pizza7);
        List<Pizza> mediumPizzasNew = pizzaService.findByPizzaSize("Medium");
        assertEquals(mediumPizzas.size() + 1, mediumPizzasNew.size());
        assertNotNull(mediumPizzasNew);
    }

    @Test
    @Order(4)
    @DisplayName("Test find by Id and Name")
    void pizzaServiceFindByIdAndFindByNameTest() {
        String pizzaName = "Pizza9";
        Pizza pizza8 = new Pizza(pizzaName, 33);
        Base base7 = new Base("Large", "Base6", 6);
        base7 = baseService.create(base7);
        pizza8.setBase(base7);
        Pizza savedPizza = pizzaService.create(pizza8);
        Pizza pizzaById = pizzaService.findById(savedPizza.getId());
        Pizza pizzaByName = pizzaService.findByName(pizzaName).orElseThrow(NotFoundException::new);
        assertEquals(savedPizza.getId(), pizzaById.getId());
        assertThrows(NotFoundException.class, () -> {
            pizzaService.findById(1111111111);
        });
        assertEquals(pizzaName, pizzaByName.getName());
        assertFalse(pizzaService.findByName("Nonexistent Name").isPresent());

    }

    @Test
    @Order(5)
    void pizzaServiceFindAllSortedBy() {
        List<Pizza> pizzas1 = pizzaService.findAll();
        List<Pizza> sortedPizzas = pizzaService.findAllSortedBy("name");
        assertEquals(pizzas1.size(), sortedPizzas.size());
        if (!pizzas1.isEmpty()) {
            Pizza random = pizzas1.get(0);
            Pizza random1 = pizzas1.get(pizzas1.size() - 1);
            assertTrue(sortedPizzas.contains(random));
            assertTrue(sortedPizzas.contains(random1));
         }

    }

    @Test
    @Order(6)
    void pizzaServiceUpdateTest(){
        String newName = "Some test name";
        int id = 1;
        Pizza pizza = pizzaService.findById(id);
        pizza.setName(newName);
        pizzaService.update(pizza);
        Pizza updatedPizza = pizzaService.findById(id);
        assertEquals(newName, updatedPizza.getName());
    }

    @Test
    @Order(7)
    void pizzaServiceSetNewPriceTest(){
        int id = 1;
        Pizza pizza = pizzaService.findById(id);
        double newPrice = 22.5;
        pizzaService.setNewPrice(pizza, newPrice);
        pizza = pizzaService.findById(id);
        assertEquals(newPrice, pizza.getPrice());
    }

    @Test
    @Order(8)
    void pizzaServiceGetCalculatedPriceTest(){

        Pizza pizza10 = new Pizza("Pizza10", 30);
        Base base10 = new Base("Small", "Base10", 5);
        base10 = baseService.create(base10);
        pizza10.setBase(base10);
        TypeIngredient type = new TypeIngredient("Test type");
        type = typeService.create(type);

        Ingredient ingredient = new Ingredient("Ingredient1", 5.0);
        Ingredient ingredient2 = new Ingredient("Ingredient1", 4.0);
        ingredient = ingredientService.create(ingredient, type);
        ingredient2 = ingredientService.create(ingredient2, type);

        List<Ingredient> ingredients;

        if (pizza10.getIngredients() == null) {
            ingredients = new ArrayList<>();
        } else {
            ingredients = pizza10.getIngredients();
        }

        ingredients.add(ingredient);
        ingredients.add(ingredient2);

        pizza10.setIngredients(ingredients);

        Pizza savedPizza = pizzaService.create(pizza10);
        double calculatedPrice = pizzaService.getCalculatedPrice(savedPizza);

        assertEquals(14, calculatedPrice);

        Base base11 = new Base("Medium", "Base11", 5);
        base11 = baseService.create(base11);
        pizza10.setBase(base11);
        pizzaService.update(pizza10);

        assertEquals(16.7, pizzaService.getCalculatedPrice(pizza10));

        Base base12 = new Base("Large", "Base11", 5);
        base12 = baseService.create(base12);
        pizza10.setBase(base12);
        pizzaService.update(pizza10);
        assertEquals(19.4, pizzaService.getCalculatedPrice(pizza10));


    }




}