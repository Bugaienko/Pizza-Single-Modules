package balu.pizza.webapp.services;

import balu.pizza.webapp.models.*;
import balu.pizza.webapp.util.NotFoundException;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaServiceTest {
    private final PizzaService pizzaService;
    private final BaseService baseService;
    private final IngredientService ingredientService;
    private final TypeService typeService;
    private final PersonService personService;
    private static List<Pizza> pizzas;

    @Autowired
    PizzaServiceTest(PizzaService pizzaService, BaseService baseService, IngredientService ingredientService, TypeService typeService, PersonService personService) {
        this.pizzaService = pizzaService;
        this.baseService = baseService;
        this.ingredientService = ingredientService;
        this.typeService = typeService;
        this.personService = personService;
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
    void pizzaServiceUpdateTest() {
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
    void pizzaServiceSetNewPriceTest() {
        int id = 1;
        Pizza pizza = pizzaService.findById(id);
        double newPrice = 22.5;
        pizzaService.setNewPrice(pizza, newPrice);
        pizza = pizzaService.findById(id);
        assertEquals(newPrice, pizza.getPrice());
    }

    @Test
    @Order(8)
    void pizzaServiceGetCalculatedPriceTest() {

        Pizza pizza10 = createAndSaveNewPizza("TestPizza");

        double calculatedPrice = pizzaService.getCalculatedPrice(pizza10);

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

    @Test
    void personServiceAddPizzaToFavAndDelPizzaFromFavTest() {
        String userName = "User11";
        String email = "user11@mail.com";
        Person person = new Person(userName, "password", email);
        person = personService.register(person);
        Pizza pizza = createAndSaveNewPizza("TestPizza");
        List<Pizza> usersPizzaList = pizzaService.findByPerson(person);
        personService.addPizzaToFav(person, pizza);
        List<Pizza> newUserPizzaList = pizzaService.findByPerson(person);

        assertEquals(usersPizzaList.size() + 1, newUserPizzaList.size());
        assertTrue(newUserPizzaList.contains(pizza));

        personService.removePizzaFromFav(person, pizza);
        List<Pizza> afterRemovePizzaList = pizzaService.findByPerson(person);
        assertFalse(afterRemovePizzaList.contains(pizza));
        assertEquals(newUserPizzaList.size() - 1, afterRemovePizzaList.size());

    }

    @Test
    void typeServiceFindByIdTest() {
        Pizza pizza = createAndSaveNewPizza("TestPizza");
        List<Ingredient> ingredients = ingredientService.findByPizza(pizza);
        for (Ingredient ingredient : ingredients) {
            TypeIngredient type = ingredient.getType();
            TypeIngredient searchedType = typeService.findById(type.getId());
            assertEquals(searchedType.getId(), type.getId());
        }

        List<TypeIngredient> typeIngredientList = typeService.findAll();

        String testTypeName = "Test new type";
        TypeIngredient type = new TypeIngredient(testTypeName);
        TypeIngredient addedType = typeService.create(type);
        Optional<TypeIngredient> searchedByName = typeService.findByName(testTypeName);
        assertTrue(searchedByName.isPresent());
        assertEquals(testTypeName, searchedByName.get().getName());

        List<TypeIngredient> afterAddTypesList = typeService.findAll();
        List<TypeIngredient> sortedAfterAddTypesList = typeService.findAllSorted();
        assertEquals(typeIngredientList.size() + 1, afterAddTypesList.size());
        assertEquals(typeIngredientList.size() + 1, sortedAfterAddTypesList.size());
        assertTrue(sortedAfterAddTypesList.contains(addedType));

        String newTypeName = "new Type Name";
        TypeIngredient typeDataForChangeName = new TypeIngredient(newTypeName);
        int idForUpdateName = addedType.getId();
        typeDataForChangeName.setId(idForUpdateName);

        typeService.updateName(typeDataForChangeName);
        TypeIngredient afterNameUpdateType = typeService.findById(idForUpdateName);

        assertEquals(newTypeName, afterNameUpdateType.getName());

    }

    @Test
    void ingredientServiceTest() {
        Pizza pizza = createAndSaveNewPizza("TestPizza");
        List<Ingredient> ingredients = ingredientService.findByPizza(pizza);
        assertEquals(2, ingredients.size());

        List<Ingredient> ingredientList = ingredientService.findAll();
        TypeIngredient type = new TypeIngredient("Test type2");
        type = typeService.create(type);

        String ingredientName = "Ingredient4";
        Ingredient ingredient = new Ingredient(ingredientName, 5.0);
        ingredient = ingredientService.create(ingredient, type);


        List<Ingredient> ingredientListNew = ingredientService.findAll();
        List<Ingredient> sortedIngredientsList = ingredientService.findAllSort();
        assertEquals(ingredientList.size() +1, ingredientListNew.size());
        assertEquals(ingredientList.size() + 1, sortedIngredientsList.size());
        assertTrue(sortedIngredientsList.contains(ingredient));

        Ingredient searchedById = ingredientService.findById(ingredient.getId());
        assertEquals(ingredient.getId(), searchedById.getId());
        assertEquals(ingredient, searchedById);
        assertThrows(NotFoundException.class, () -> {
            ingredientService.findById(111002221);
        });

        Optional<Ingredient> foundedByName = ingredientService.findIngredientByName(ingredientName);
        assertTrue(foundedByName.isPresent());
        assertEquals(ingredientName, foundedByName.get().getName());
        Optional<Ingredient> foundedByWrongName = ingredientService.findIngredientByName("Wrong Ing name");
        assertFalse(foundedByWrongName.isPresent());
    }



    private Pizza createAndSaveNewPizza(String pizzaName) {

        Optional<Pizza> pizza = pizzaService.findByName(pizzaName);
        if (pizza.isPresent()) {
            return pizza.get();
        }


        Pizza pizza10 = new Pizza(pizzaName, 30);
        Base base10 = new Base("Small", "Base10", 5);
        base10 = baseService.create(base10);
        pizza10.setBase(base10);
        TypeIngredient type = new TypeIngredient("Test type");
        type = typeService.create(type);

        Ingredient ingredient = new Ingredient("Ingredient1", 5.0);
        Ingredient ingredient2 = new Ingredient("Ingredient2", 4.0);
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

        return pizzaService.create(pizza10);
    }


}