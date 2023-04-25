package balu.pizza.webapp.util;

import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.security.PersonDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Sergii Bugaienko
 */

@Component
public class UserUtil {

    public Person getActiveUser(){
        Person user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            user = personDetails.getPerson();
//            System.out.println(user.getUsername());
        }
        return user;
    }
}
