package balu.pizza.webapp.controllers;

import balu.pizza.webapp.models.Person;
import balu.pizza.webapp.services.PersonService;
import balu.pizza.webapp.util.PersonValidator;
import balu.pizza.webapp.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Public route controller for user authentication and authorization
 *
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final UserUtil userUtil;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Value("${upload.path}")
    private String uploadPath;

    /**
     *
     * @param personValidator Validator for user data entry
     * @param personService Users service
     * @param userUtil Set of utilities
     */
    @Autowired
    public AuthController(PersonValidator personValidator, PersonService personService, UserUtil userUtil) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.userUtil = userUtil;
    }


    /**
     * Page with user authorization form
     *
     * @return generates a page for the route /auth/login
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Page with user registration form <strong>without</strong> user avatar uploading
     * @param person user entity
     * @return generates a page for the route /auth/registration
     */
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {

        return "auth/registration";
    }

    /**
     * Validation user data. If successful save user to database
     * @param person
     * @param bindingResult
     * @param rePassword
     * @return If successful redirect to login page
     */
    @PostMapping("/registration")
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, String rePassword) {

            personValidator.validate(person, bindingResult);
            personValidator.validate(person, rePassword, bindingResult);

            if (bindingResult.hasErrors()) {
                return "auth/registration";
            }

            Person person1 = personService.register(person);
            logger.info("New user created {} : id={}", person1.getUsername(), person1.getId());
//        }

        return "redirect:/auth/login";
    }


    /**
     * Generating the unlogin page
     * @param model
     * @return generates a page for the route /auth/exit
     */
    @GetMapping("/exit")
    public String confirmLogout(Model model) {

        return "auth/exit";
    }

    /**
     * Page with user registration form <strong>with</strong> user avatar uploading
     * @param person
     * @return generates a page for the route /auth/signupImg
     */
    @GetMapping("/signupImg")
    public String registrationWithImage(@ModelAttribute("person") Person person){
        return "auth/loadImage";
    }

    /**
     * Validation user data. If successful save user to database
     * @param person
     * @param bindingResult
     * @param rePassword
     * @param avatar
     * @return If successful redirect to login page
     */
    @PostMapping("/signupImg")
    public String createUserWithAvatar(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, String rePassword, @RequestParam("file") MultipartFile avatar){
        System.out.println("Auth contr -> post signup");
        personValidator.validate(person, bindingResult);
        personValidator.validate(person, rePassword, bindingResult);

        if (avatar == null){
            bindingResult.rejectValue("avatar", "", "No avatar added");
        }

        if (bindingResult.hasErrors()) {
            return "auth/loadImage";
        }

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile =  UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + avatar.getOriginalFilename();
        System.out.println(resultFileName);

        try {
            avatar.transferTo(new File(uploadPath + "/" + resultFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        person.setAvatar(resultFileName);

        Person person1 = personService.register(person);
        logger.info("New user created {} : id={}", person1.getUsername(), person1.getId());

        return "redirect:/auth/login";
    }

}
