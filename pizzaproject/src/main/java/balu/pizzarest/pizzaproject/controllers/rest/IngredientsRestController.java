package balu.pizzarest.pizzaproject.controllers.rest;

import balu.pizzarest.pizzaproject.controllers.interfases.IngredientControllerInt;
import balu.pizzarest.pizzaproject.dto.IngredientDTO;
import balu.pizzarest.pizzaproject.models.Ingredient;
import balu.pizzarest.pizzaproject.services.IngredientService;
import balu.pizzarest.pizzaproject.util.ErrorResponse;
import balu.pizzarest.pizzaproject.util.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergii Bugaienko
 */

@RestController
@RequestMapping("/api/ingredients")
@Validated
public class IngredientsRestController implements IngredientControllerInt {

    private final ModelMapper modelMapper;
    private final IngredientService ingredientService;

    @Autowired
    public IngredientsRestController(ModelMapper modelMapper, IngredientService ingredientService) {
        this.modelMapper = modelMapper;
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Get all ingredients", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Ingredients" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = IngredientDTO.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied") })
    @Override
    @GetMapping("/all") public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.findAll().stream().map(this::convertIngredientToDto).collect(Collectors.toList()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable("id") int id) {
        return ResponseEntity.ok(convertIngredientToDto(ingredientService.findById(id)));
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
//        System.out.println("Ingr Exeption");
        ErrorResponse response = new ErrorResponse(
                "Object with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    private IngredientDTO convertIngredientToDto(Ingredient ingredient){
        return modelMapper.map(ingredient, IngredientDTO.class);
    }



}
