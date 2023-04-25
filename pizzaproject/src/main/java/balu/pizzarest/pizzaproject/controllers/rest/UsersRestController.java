package balu.pizzarest.pizzaproject.controllers.rest;

import balu.pizzarest.pizzaproject.controllers.interfases.UsersControllerInt;
import balu.pizzarest.pizzaproject.dto.AuthenticationDTO;
import balu.pizzarest.pizzaproject.dto.PersonDTO;
import balu.pizzarest.pizzaproject.dto.PizzaDTO;
import balu.pizzarest.pizzaproject.dto.responsesModel.InlineResponse2002;
import balu.pizzarest.pizzaproject.dto.responsesModel.InlineResponse2003;
import balu.pizzarest.pizzaproject.models.Person;
import balu.pizzarest.pizzaproject.models.Pizza;
import balu.pizzarest.pizzaproject.security.JwtUtil;
import balu.pizzarest.pizzaproject.services.PersonService;
import balu.pizzarest.pizzaproject.services.PizzaService;
import balu.pizzarest.pizzaproject.util.*;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sergii Bugaienko
 */

@RestController
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-15T16:01:23.022+02:00[Europe/Berlin]")
@Validated
@Api(value = "Users", description = "the Users API")
@RequestMapping("/api/users")
public class UsersRestController implements UsersControllerInt {


    private final PersonService personService;
    private final PersonValidator personValidator;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PizzaService pizzaService;
    private final AuthUtil authUtil;


    @Autowired
    public UsersRestController(PersonService personService, PersonValidator personValidator, ModelMapper modelMapper, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PizzaService pizzaService, AuthUtil authUtil) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;

        this.authenticationManager = authenticationManager;
        this.pizzaService = pizzaService;
        this.authUtil = authUtil;
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Object with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreatedException(EntityNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * GET /api/users/all : Get all users
     *
     * @return Successful operation (status code 200)
     *         or Access denied (status code 401)
     */

    @ApiOperation(value = "Get all users", nickname = "getAll", notes = "", response = PersonDTO.class, responseContainer = "List", tags={ "Users", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = PersonDTO.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access denied") })
    @GetMapping(value =  "/all", produces = { "application/json" })
    public ResponseEntity<List<PersonDTO>> getAllUsers() {
        return ResponseEntity.ok(personService.findAll().stream()
                .map(this::convertToDtoPerson).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<PizzaDTO>> getFavorites(int id) {
        //TODO
        Person activePerson = personService.findOne(id);
        List<PizzaDTO> pizzas = activePerson.getFavorites().stream().map(this::convertPizzaToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(pizzas);
    }




    @Hidden
    @PostMapping("/test")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsg.toString());
        }
        Person person = convertToPerson(personDto);
        personService.register(person);
        //отправляем ответ с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/edit")
    public ResponseEntity<Map<String, String>> updateUser(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        Person activePerson = authUtil.getActive();

        Person person = convertToPerson(personDTO);

        personValidator.validate(person, activePerson, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsg.toString());
        }

        personService.update(activePerson, person);
        String token = jwtUtil.generateToken(person.getUsername());


        Map<String, String> map = new HashMap<>();
        map.put("updated data user",
                activePerson.getUsername() + " : " + activePerson.getEmail());
        map.put("jwt-token", token);
        return ResponseEntity.ok(map);
    }

    @Override
    @PostMapping(value = "/addToFav/{id}/{pizzaId}", produces = { "application/json" })
    public ResponseEntity<InlineResponse2002> addToFav(@Parameter(in = ParameterIn.PATH, description = "user id", required=true, schema=@Schema()) @PathVariable("id") int personId, @Parameter(in = ParameterIn.PATH, description = "pizza id", required=true, schema=@Schema()) @PathVariable("pizzaId") int pizzaId) {
        Person person = personService.findOne(personId);
        String responseStr = personService.addPizzaToFav(person, pizzaService.findById(pizzaId));
        return ResponseEntity.ok(new InlineResponse2002(responseStr));
    }

    @Override
    @PostMapping(value = "/removeFromFav/{id}/{pizzaId}", produces = { "application/json" })
    public ResponseEntity<InlineResponse2003> removeFromFavList(int personId, int pizzaId) {
        Person person = personService.findOne(personId);
        String responseStr = personService.removePizzaFromFav(person, pizzaService.findById(pizzaId));
        return ResponseEntity.ok(new InlineResponse2003(responseStr));
    }


    private Person convertToPerson(PersonDTO personDto) {
//        ModelMapper mapper = new ModelMapper(); // Create Bean in config

        return modelMapper.map(personDto, Person.class);

//        return new Person(personDto.getUsername(), personDto.getPassword(), personDto.getEmail(), personDto.getAvatar());
    }

    private PersonDTO convertToDtoPerson(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }


    /**
     * GET /api/users/{id} : Get user
     * Get one by id
     *
     * @param id record id (required)
     * @return Successful operation (status code 200)
     *         or Access denied (status code 401)
     *         or Request failed - No items (status code 404)
     */

    @GetMapping(value = "/{id}", produces = { "application/json" }
    )
    public ResponseEntity<PersonDTO> getUser(@ApiParam(value = "user id", required=true) @PathVariable("id") int id) {
        return ResponseEntity.ok(convertToDtoPerson(personService.findOne(id)));
    }

    @Hidden
    @PostMapping("/registration")
    public Map<String, String> createPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsg.toString());
        }

        personService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());

        Map<String, String> map = new HashMap<>();
        map.put("jwt-token", token);
        return map;

    }

    @Hidden
    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e){
            Map<String, String> map = new HashMap<>();
            map.put("message", "Incorrect credentials");
            return map;
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        Map<String, String> resp = new HashMap<>();
        resp.put("jwt-token", token);
        return resp;

    }


    private PizzaDTO convertPizzaToDTO(Pizza pizza){
        return modelMapper.map(pizza, PizzaDTO.class);
    }



}
