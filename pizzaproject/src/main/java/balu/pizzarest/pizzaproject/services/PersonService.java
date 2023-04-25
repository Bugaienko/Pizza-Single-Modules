package balu.pizzarest.pizzaproject.services;

import balu.pizzarest.pizzaproject.models.Person;
import balu.pizzarest.pizzaproject.models.Pizza;
import balu.pizzarest.pizzaproject.repositiries.PersonRepository;
import balu.pizzarest.pizzaproject.repositiries.PizzaRepository;
import balu.pizzarest.pizzaproject.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class PersonService {
    static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final PizzaRepository pizzaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PizzaRepository pizzaRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.pizzaRepository = pizzaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Person> findUserByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Optional<Person> findUserByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Transactional
    public Person register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        enrichPerson(person);
        logger.info("Add new Person, personId={}", person.getUsername());
        return personRepository.save(person);
    }

    private void enrichPerson(Person person) {
        person.setRole("ROLE_USER");
//        person.setCreatedAt(LocalDateTime.now());
//        person.setUpdatedAt(LocalDateTime.now());
//        person.setCreatedWho("ADMIN");
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public String addPizzaToFav(Person person, Pizza pizza) {
//        List<Pizza> favorites2 = person.getFavorites();
        List<Pizza> favorites = pizzaRepository.findByPersons(person);
        String responseStr;
        responseStr = "Pizza id: " + pizza.getId() + " already in " + person.getUsername() + " fav list";

        if (!favorites.contains(pizza)) {
            favorites.add(pizza);
            logger.info("Add Fav pizza{} to Person, personId={}", pizza.getId(), person.getId());
            responseStr = "Pizza id: " + pizza.getId() + " was added to " + person.getUsername() + " fav list";
            person.setFavorites(favorites);
            personRepository.save(person);
        }
        return responseStr;
    }

    @Transactional
    public String removePizzaFromFav(Person person, Pizza pizza) {
        List<Pizza> favorites = pizzaRepository.findByPersons(person);
        String responseStr;
        responseStr = "Pizza id: " + pizza.getId() + " is not on " + person.getUsername() + " fav list";

        if (favorites != null && favorites.contains(pizza)) {

            favorites.remove(pizza);
            person.setFavorites(favorites);
            personRepository.save(person);
            logger.info("Del pizza{} from Person id={} Fav", pizza.getId(), person.getId());

            responseStr = "Pizza id: " + pizza.getId() + " was removed from " + person.getUsername() + " fav list";
        }
        return responseStr;
    }

    @Transactional
    public boolean save(Person person) {
        System.out.println("PS save");
        personRepository.save(person);
        return true;
    }

    public Person findOne(int personId) {
        return personRepository.findById(personId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Person update(Person activePerson, Person person) {
        activePerson.setUsername(person.getUsername());
        activePerson.setPassword(passwordEncoder.encode(person.getPassword()));
        activePerson.setEmail(person.getEmail());
        logger.info("Update Person id={}", activePerson.getId());
        return personRepository.save(activePerson);
    }
}
