package balu.pizzarest.pizzaproject.util;

import balu.pizzarest.pizzaproject.models.Person;
import balu.pizzarest.pizzaproject.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * @author Sergii Bugaienko
 */

@Component
public class AuthUtil {
    public Person getActive(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) (authentication.getPrincipal());
        Person person = personDetails.getPerson();
        System.out.println("Auth util -> person -> " + person);
        return person;


    }
}
