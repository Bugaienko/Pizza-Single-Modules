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

    @Autowired
    public AuthController(PersonValidator personValidator, PersonService personService, UserUtil userUtil) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.userUtil = userUtil;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, String rePassword) {
//    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @RequestParam("file") MultipartFile avatar, String rePassword) {
//        if (avatar != null) {
//
//
////            String uuidFile = UUID.randomUUID().toString();
////            String resultFileName = uuidFile + "-" + avatar.getOriginalFilename();
//            String fileName = avatar.getOriginalFilename();
//
//            person.setAvatar(fileName);
//
//            personValidator.validate(person, bindingResult);
//            personValidator.validate(person, rePassword, bindingResult);
//
//            if (bindingResult.hasErrors()) {
//                return "auth/registration";
//            }
//
//            Person person1 = personService.register(person);
//            File uploadDir = new File(uploadPath + "/images/user/" + person1.getId());
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
////            System.out.println(uploadDir);
//            try {
//                avatar.transferTo(new File(uploadDir + "/" + fileName));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//        System.out.println(person);
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



    @GetMapping("/exit")
    public String confirmLogout(Model model) {

        return "auth/exit";
    }

    @GetMapping("/signupImg")
    public String loadImage(@ModelAttribute("person") Person person){
        return "auth/loadImage";
    }

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
