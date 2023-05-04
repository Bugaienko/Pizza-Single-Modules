package balu.pizza.webapp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonServiceTest {

    private final PersonService personService;
    private final PersonDetailService personDetailService;

    @Autowired
    PersonServiceTest(PersonService personService, PersonDetailService personDetailService) {
        this.personService = personService;
        this.personDetailService = personDetailService;
    }

    @Test
    void findAll() {
    }
}