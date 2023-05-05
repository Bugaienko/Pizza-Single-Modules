package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.util.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    private final PersonService personService;
    private final PersonDetailService personDetailService;
    private static List<Person> persons;

    @Autowired
    PersonServiceTest(PersonService personService, PersonDetailService personDetailService) {
        this.personService = personService;
        this.personDetailService = personDetailService;
    }

    @BeforeAll
    static void initPersonsDb() {
        persons = new ArrayList<>();
        Person person1 = new Person("User1", "password", "user1@mail.com");
        Person person2 = new Person("User2", "password", "user2@mail.com");
        Person person3 = new Person("User3", "password", "user3@mail.com");
        Person person4 = new Person("User4", "password", "user4@mail.com");
        Person person5 = new Person("User5", "password", "user5@mail.com");
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        persons.add(person5);

    }

    @Test
    @Order(1)
    void addPersonsToBd() {
        for (Person person : persons) {
            personService.register(person);
        }
    }

    @Test
    @Order(2)
    void personServiceFindAllAndLoadUserByUsernameTest() {
        List<Person> people = personService.findAll();
        String userName = "User6";
        Person person6 = new Person(userName, "password", "user6@mail.com");
        person6 = personService.register(person6);
        List<Person> people1 = personService.findAll();

        assertEquals(people.size() + 1, people1.size());
        assertTrue(people1.contains(person6));

        UserDetails userByUsername = personDetailService.loadUserByUsername(userName);

        assertEquals(userName, userByUsername.getUsername());
        assertEquals("ROLE_USER", person6.getRole());

        assertThrows(UsernameNotFoundException.class, () -> {
            personDetailService.loadUserByUsername("Some wrong name");
        });

        Optional<Person> personByUsername = personService.findUserByUsername(userName);
        assertTrue(personByUsername.isPresent());
        assertEquals(userName,personByUsername.get().getUsername());


    }

    @Test
    @Order(3)
    void personServiceUpdateTest() {
        Person personTest = new Person("Person test", "password", "usertest@mail.com");
        personService.register(personTest);

        String userName = "User7";
        String email = "user7@mail.com";
        int searchById = 1;
        Person person7 = new Person(userName, "password", email);
        Person foundedPerson = personService.findOne(searchById);
        personService.update(foundedPerson, person7);
        Person savedPerson = personService.findOne(searchById);

        assertEquals(savedPerson.getUsername(), person7.getUsername());
        assertEquals(savedPerson.getEmail(), person7.getEmail());
        assertThrows(NotFoundException.class, () -> {
           personService.findOne(111199919);
        });

        Optional<Person> foundedPersonByEmail = personService.findUserByEmail(email);
        assertTrue(foundedPersonByEmail.isPresent());
        assertEquals(email, foundedPersonByEmail.get().getEmail());

        String newUserName = "New username";
        Person personId1 = personService.findOne(searchById);
        personId1.setUsername(newUserName);
        personService.save(personId1);
        personId1 = personService.findOne(searchById);
        assertEquals(newUserName, personId1.getUsername());

    }


}