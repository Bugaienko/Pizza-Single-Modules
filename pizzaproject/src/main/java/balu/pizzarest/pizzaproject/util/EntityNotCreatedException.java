package balu.pizzarest.pizzaproject.util;

/**
 * @author Sergii Bugaienko
 */

public class EntityNotCreatedException extends RuntimeException {
    public EntityNotCreatedException(String msg){
        super(msg);
    }
}
