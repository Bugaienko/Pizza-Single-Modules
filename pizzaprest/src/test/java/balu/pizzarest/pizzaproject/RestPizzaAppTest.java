package balu.pizzarest.pizzaproject;

import balu.pizzarest.pizzaproject.models.Person;
import balu.pizzarest.pizzaproject.repositiries.PersonRepository;
import balu.pizzarest.pizzaproject.services.PersonService;
import balu.pizzarest.pizzaproject.util.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RestPizzaAppTest {

    private final PersonRepository personRepository;
    private final PersonService personService;

    private static List<Person> initList = new ArrayList<>();

    @Autowired
    RestPizzaAppTest(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @BeforeAll
    static void addPersons() {
        Person person = new Person("test1", "test1", "email1@email.com");
        Person person1 = new Person("test2", "test2", "email2@email.com");
        Person person2 = new Person("test3", "test3", "email3@email.com");
        Person person3 = new Person("test4", "test4", "email4@email.com");
        initList.add(person);
        initList.add(person1);
        initList.add(person2);
        initList.add(person3);
    }

    @Test
    void personRegisterServiceTest() {
        int size = personRepository.findAll().size();
        for (Person person : initList) {
            personService.register(person);
        }
        Assertions.assertEquals(size + 4, personService.findAll().size());


    }

    @Test
    void findByUsernameServiceTest() {
        String personName = "New name";
        Person person = new Person(personName, "test6", "email6@email.com");
        personService.register(person);
        Person person1 = personService.findUserByUsername(personName).orElseThrow(NotFoundException::new);
        Assertions.assertEquals(personName, person1.getUsername());
    }

}