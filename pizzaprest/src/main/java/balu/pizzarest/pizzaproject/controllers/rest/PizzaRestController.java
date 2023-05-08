package balu.pizzarest.pizzaproject.controllers.rest;

import balu.pizzarest.pizzaproject.controllers.interfases.PizzaControllerInt;
import balu.pizzarest.pizzaproject.dto.BaseDTO;
import balu.pizzarest.pizzaproject.dto.IngredientDTO;
import balu.pizzarest.pizzaproject.dto.PizzaDTO;
import balu.pizzarest.pizzaproject.dto.TypeDTO;
import balu.pizzarest.pizzaproject.models.Base;
import balu.pizzarest.pizzaproject.models.Ingredient;
import balu.pizzarest.pizzaproject.models.Pizza;
import balu.pizzarest.pizzaproject.models.TypeIngredient;
import balu.pizzarest.pizzaproject.services.BaseService;
import balu.pizzarest.pizzaproject.services.IngredientService;
import balu.pizzarest.pizzaproject.services.PizzaService;
import balu.pizzarest.pizzaproject.util.ErrorResponse;
import balu.pizzarest.pizzaproject.util.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergii Bugaienko
 */

@RestController
@RequestMapping("/api/pizza")
public class PizzaRestController implements PizzaControllerInt {

    private final ModelMapper modelMapper;
    private final PizzaService pizzaService;
    private final IngredientService ingredientService;
    private final BaseService baseService;

    @Autowired
    public PizzaRestController(ModelMapper modelMapper, PizzaService pizzaService, IngredientService ingredientService, BaseService baseService) {
        this.modelMapper = modelMapper;
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
        this.baseService = baseService;
    }

    @Override
    @GetMapping(value = "/all")
    public ResponseEntity<List<PizzaDTO>> getAllPizzas() {
        List<Pizza> pizzas = pizzaService.findAll();
//        List<PizzaDTO> pizzaDtoS = new ArrayList<>();
//        for (Pizza pizza : pizzas){
//            pizzaDtoS.add(convertToPizzaDto(pizza));
//        }
        List<PizzaDTO> pppDTO = pizzas.stream().map(this::convertToPizzaDto).collect(Collectors.toList());
        return ResponseEntity.ok(pppDTO);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PizzaDTO> getPizza(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertToPizzaDto(pizzaService.findById(id)));
    }

    private PizzaDTO convertToPizzaDto(Pizza pizza){
//       PizzaDTO pizzaDTO = new PizzaDTO(pizza.getId(), pizza.getName(), pizza.getPrice(), pizza.getImage());
//       pizzaDTO.setBase(convertToBaseDto(pizza.getBase()));
//       List<IngredientDTO> ingrDto = new ArrayList<>();
//       for (Ingredient ing: pizza.getIngredients()){
//           ingrDto.add(convertIngredientToDto(ing));
//       }
//       pizzaDTO.setIngredients(ingrDto);
//       return pizzaDTO;
        return modelMapper.map(pizza, PizzaDTO.class);
    }


    private BaseDTO convertToBaseDto(Base base){
        return modelMapper.map(base, BaseDTO.class);
    }

    private IngredientDTO convertIngredientToDto(Ingredient ingredient){
        IngredientDTO ingredientDTO = new IngredientDTO(ingredient);
        ingredientDTO.setType(convertTypeTpDto(ingredient.getType()));
        return ingredientDTO;
    }

    private TypeDTO convertTypeTpDto(TypeIngredient typeIngredient){
        return modelMapper.map(typeIngredient, TypeDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Object with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }


}
