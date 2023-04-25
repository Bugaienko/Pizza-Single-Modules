package balu.pizzarest.pizzaproject.controllers.rest;

import balu.pizzarest.pizzaproject.controllers.interfases.AuthControllerInt;
import balu.pizzarest.pizzaproject.dto.AuthenticationDTO;
import balu.pizzarest.pizzaproject.dto.PersonDTO;
import balu.pizzarest.pizzaproject.dto.responsesModel.JwtTokenResponse200;
import balu.pizzarest.pizzaproject.models.Person;
import balu.pizzarest.pizzaproject.security.JwtUtil;
import balu.pizzarest.pizzaproject.services.PersonService;
import balu.pizzarest.pizzaproject.util.EntityNotCreatedException;
import balu.pizzarest.pizzaproject.util.ErrorResponse;
import balu.pizzarest.pizzaproject.util.NotFoundException;
import balu.pizzarest.pizzaproject.util.PersonValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

/**
 * @author Sergii Bugaienko
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-15T16:01:23.022+02:00[Europe/Berlin]")
@Validated
@Api(value = "Auth", description = "the Auth API")
@RestController
@RequestMapping("/api/auth")
public class AuthRestController implements AuthControllerInt {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PersonValidator personValidator;
    private final ModelMapper modelMapper;
    private final PersonService personService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PersonValidator personValidator, ModelMapper modelMapper, PersonService personService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.personValidator = personValidator;
        this.modelMapper = modelMapper;

        this.personService = personService;
    }

    /**
     * POST /api/auth/login : Authorization method
     * Get jwt-token
     *
     * @param PersoDTO  (optional)
     * @return successful authorization (status code 200)
     *         or Authentication failed (status code 401)
     */
    @ApiOperation(value = "Authorization method", nickname = "apiAuthLoginPost", notes = "Get jwt-token", response = Object.class, tags={ "Auth", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful authorization", response = JwtTokenResponse200.class),
            @ApiResponse(code = 401, message = "Authentication failed") })
    @PostMapping(
            value = "/login", produces = { "application/json" }, consumes = { "application/json" }
    )
    public ResponseEntity<Map<String, String>> apiAuthLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getUsername(), authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e){
            Map<String, String> map = new HashMap<>();
            map.put("message", "Incorrect credentials");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        Map<String, String> resp = new HashMap<>();
        resp.put("jwt-token", token);
        return ResponseEntity.ok(resp);

    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> apiAuthSignup(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

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
        return ResponseEntity.ok(map);

    }

    private Person convertToPerson(PersonDTO personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    private PersonDTO convertToDtoPerson(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Object with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreatedException(EntityNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
