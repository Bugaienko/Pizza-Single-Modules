package balu.pizzarest.pizzaproject.controllers.interfases;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergii Bugaienko
 */

@Controller
public class SwaggerController {
    @GetMapping(value = { "/swagger-ui", "/swagger-ui+html"})
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }
}
