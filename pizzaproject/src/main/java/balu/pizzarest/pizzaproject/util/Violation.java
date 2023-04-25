package balu.pizzarest.pizzaproject.util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Sergii Bugaienko
 */


@Getter
@RequiredArgsConstructor
public class Violation {

    private final String fieldName;
    private final String message;
}
