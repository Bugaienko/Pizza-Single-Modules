package balu.pizzarest.pizzaproject.util;

/**
 * @author Sergii Bugaienko
 */

public class PizzaNotCreatedException extends RuntimeException {
    public PizzaNotCreatedException(String msg){
        super(msg);
    }
}
