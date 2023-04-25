package balu.pizzarest.pizzaproject.controllers.rest;

import balu.pizzarest.pizzaproject.controllers.interfases.AdminControllerInterface;
import balu.pizzarest.pizzaproject.dto.*;
import balu.pizzarest.pizzaproject.models.*;
import balu.pizzarest.pizzaproject.security.JwtUtil;
import balu.pizzarest.pizzaproject.services.*;
import balu.pizzarest.pizzaproject.util.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RestController
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-15T16:01:23.022+02:00[Europe/Berlin]")
@Validated
@Api(value = "Admin", description = "Admin API")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminRestController implements AdminControllerInterface {

    private final ModelMapper modelMapper;
    private final CafeService cafeService;
    private final PizzaService pizzaService;
    private final TypeService typeService;
    private final PersonService personService;
    private final PersonValidator personValidator;
    private final IngredientService ingredientService;
    private final PizzaValidator pizzaValidator;
    private final BaseValidator baseValidator;
    private final BaseService baseService;
    private final JwtUtil jwtUtil;
    private final Logger logger = LoggerFactory.getLogger(CafeRestController.class);

    public AdminRestController(ModelMapper modelMapper, CafeService cafeService, PizzaService pizzaService, TypeService typeService, PersonService personService, PersonValidator personValidator, IngredientService ingredientService, PizzaValidator pizzaValidator, BaseValidator baseValidator, BaseService baseService, JwtUtil jwtUtil) {
        this.modelMapper = modelMapper;
        this.cafeService = cafeService;
        this.pizzaService = pizzaService;
        this.typeService = typeService;
        this.personService = personService;
        this.personValidator = personValidator;
        this.ingredientService = ingredientService;
        this.pizzaValidator = pizzaValidator;
        this.baseValidator = baseValidator;
        this.baseService = baseService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cafe/add", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<CafeDTO> createCafe(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BodyAddCafe body) {
        Cafe newCafe = convertToCafeFromBody(body);
        cafeService.create(newCafe);
        logger.info("Create new cafe {}", newCafe.getTitle());
        return ResponseEntity.ok(convertCafeToDTO(newCafe));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cafe/menu/add/{cafeId}/{pizzaId}")
    public ResponseEntity<Map<String, String>> addToMenu(@Parameter(in = ParameterIn.PATH, description = "cafe id", required = true, schema = @Schema()) @PathVariable("cafeId") int cafeId, @Parameter(in = ParameterIn.PATH, description = "pizza id", required = true, schema = @Schema()) @PathVariable("pizzaId") int pizzaId) {
        Cafe cafe = cafeService.findById(cafeId);
        Pizza pizza = pizzaService.findById(pizzaId);
        Map<String, String> map = new HashMap<>();
        if (!cafe.getPizzas().contains(pizza)) {
            cafe.getPizzas().add(pizza);
            cafeService.save(cafe);
            logger.info("Add pizaa id{} to cafe id{}", pizza.getId(), cafe.getId());
            map.put("Was added new piza id=" + pizza.getId(), "to Cafe id=" + cafe.getId());
            return ResponseEntity.ok(map);
        } else {
            map.put("Pziza id=" + pizza.getId(), "already on the Cafe id=" + cafe.getId() + " menu");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cafe/menu/remove/{cafeId}/{pizzaId}")
    public ResponseEntity<Map<String, String>> removeFromMenu(@Parameter(in = ParameterIn.PATH, description = "cafe id", required = true, schema = @Schema()) @PathVariable("cafeId") int cafeId, @Parameter(in = ParameterIn.PATH, description = "pizza id", required = true, schema = @Schema()) @PathVariable("pizzaId") int pizzaId) {
        Cafe cafe = cafeService.findById(cafeId);
        Pizza pizza = pizzaService.findById(pizzaId);
        Map<String, String> map = new HashMap<>();
        if (cafe.getPizzas().contains(pizza)) {
            cafe.getPizzas().remove(pizza);
            cafeService.save(cafe);
            logger.info("Pizaa id{} was deleted from cafe menu id{}", pizza.getId(), cafe.getId());
            map.put("Was deleted pizza id=" + pizza.getId(), "from Cafe id=" + cafe.getId());
            return ResponseEntity.ok(map);
        } else {
            map.put("Pziza id=" + pizza.getId(), "not found on the Cafe id=" + cafe.getId() + " menu");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @Override
    @PostMapping(value = "/ingredients/add", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<IngredientDTO> addIngredient(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BodeAddIngredient body) {
        TypeIngredient type = typeService.findById(body.getType_id());
        Ingredient ingredient = convertBodyToIngredient(body);
        ingredient.setType(type);
        //TODO save to BD
        ingredientService.create(ingredient);
        logger.info("Create new ingredient {}", ingredient.getName());
        return ResponseEntity.ok(convertIngredirntToDto(ingredient));
    }

    @Override
    @PostMapping(value = "/pizza/addIngredients/{pizzaId}")
    public ResponseEntity<PizzaDTO> addInredient(int pizzaId, List<Integer> ingredintsIdList) {
        Pizza pizza = pizzaService.findById(pizzaId);
        pizza = pizzaService.addIngredientsList(pizza, ingredintsIdList);
        return ResponseEntity.ok(convertPizzaToDto(pizza));
    }

    @Override
    @PostMapping(value = "/pizza/removeIngredient/{pizzaId}/{id}")
    public ResponseEntity<PizzaDTO> removeIngredient(int pizzaId, int ingredientId) {
        Pizza pizza = pizzaService.findById(pizzaId);
        pizza = pizzaService.removeIngredientFromPizza(pizza, ingredientId);
        return ResponseEntity.ok(convertPizzaToDto(pizza));
    }


    @Override
    @PostMapping(value = "/users/edit/{id}")
    public ResponseEntity<Map<String, String>> apiAdminUsersEditIdPost(int id, BodyUserEdit body, BindingResult bindingResult) {
        Person activePerson = personService.findOne(id);

        Person person = convertToPersonFromBody(body);

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
    @PostMapping(value = "/pizza/changeBase/{pizzaId}/{id}")
    public ResponseEntity<PizzaDTO> changeBase(int pizzaId, int baseId) {
        Pizza pizza = pizzaService.findById(pizzaId);
        pizza = pizzaService.changeBase(pizza, baseId);
        return ResponseEntity.ok(convertPizzaToDto(pizza));
    }

    @Override
    @PostMapping(value = "/pizza/create")
    public ResponseEntity<PizzaDTO> createPizza(BodyAddPizza body, BindingResult bindingResult) {
        Pizza pizza = convertToPizzaFromBody(body);

        pizzaValidator.validate(pizza, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PizzaNotCreatedException(errorMsg.toString());
        }


        pizza = pizzaService.createNewFromBody(pizza, body.getBaseId());
        return ResponseEntity.ok(convertPizzaToDto(pizza));
    }

    @Override
    @PostMapping(value = "/base/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {"application/json"})
    public ResponseEntity<BaseDTO> addNewBase(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @RequestBody @Valid BodyBase body, BindingResult bindingResult) {
//        System.out.println("base/add " + body.getSize() + " " + body.getName() + " " + body.getPrice());

        Base newBase = convertToBaseFromBody(body);

        baseValidator.validate(newBase, bindingResult);

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
        BaseDTO baseDTO = convertBaseToDTO(baseService.create(newBase));

        return ResponseEntity.ok(baseDTO);
    }

    @Override
    @PostMapping(value = "/base/change/{id}", produces = {"application/json", "application/json"}, consumes = {"application/json"})
    public ResponseEntity<BaseDTO> changeBaseFields(@Parameter(in = ParameterIn.PATH, description = "base id", required = true, schema = @Schema()) @PathVariable("id") int id, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BodyBase body, BindingResult bindingResult) {
        System.out.println("base/change" + body.getSize() + " " + body.getName() + " " + body.getPrice());
        Base baseForUpdate = baseService.findById(id);
        Base newBase = convertToBaseFromBody(body);

        baseValidator.validate(newBase, bindingResult);

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

        baseForUpdate = baseService.update(baseForUpdate, newBase);
        return ResponseEntity.ok(convertBaseToDTO(baseForUpdate));
    }


    private BaseDTO convertBaseToDTO(Base base) {
        return modelMapper.map(base, BaseDTO.class);
    }

    private Base convertToBaseFromBody(BodyBase body) {
        return modelMapper.map(body, Base.class);
    }

    private Pizza convertToPizzaFromBody(BodyAddPizza body) {
        Pizza pizza = new Pizza(body.getName(), body.getPrice(), body.getImage());
        return pizza;
    }


    private CafeDTO convertCafeToDTO(Cafe cafe) {
        return modelMapper.map(cafe, CafeDTO.class);
    }

    private Cafe convertToCafeFromBody(BodyAddCafe addBody) {
        return modelMapper.map(addBody, Cafe.class);
    }

    private PizzaDTO convertPizzaToDto(Pizza pizza) {
        return modelMapper.map(pizza, PizzaDTO.class);
    }

    private Ingredient convertBodyToIngredient(BodeAddIngredient body) {
        return modelMapper.map(body, Ingredient.class);
    }

    private IngredientDTO convertIngredirntToDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDTO.class);
    }

    private Person convertToPersonFromBody(BodyUserEdit body) {
        return modelMapper.map(body, Person.class);
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

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreatedException(PizzaNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
