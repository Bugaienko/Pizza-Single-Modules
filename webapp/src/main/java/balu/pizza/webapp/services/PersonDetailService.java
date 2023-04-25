package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.repositiries.PersonDetailRepository;
import balu.pizza.webapp.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class PersonDetailService implements UserDetailsService {

    private final PersonDetailRepository personDetailRepository;

    @Autowired
    public PersonDetailService(PersonDetailRepository personDetailRepository) {
        this.personDetailRepository = personDetailRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personDetailRepository.findByUsername(username);

        if (!person.isPresent()) {
            throw  new UsernameNotFoundException("User not found!");
        }

        return new PersonDetails(person.get());
    }
}
