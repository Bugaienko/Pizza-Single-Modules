package balu.pizzarest.pizzaproject.controllers.interfases;

import balu.pizzarest.pizzaproject.dto.IngredientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-16T14:31:40.830807727Z[GMT]")
@Validated
public interface IngredientControllerInt {
    @Operation(summary = "Get all ingredients", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Ingredients" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = IngredientDTO.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied") })
    @GetMapping(value = "/api/ingredients/all",
            produces = { "application/json" })
    ResponseEntity<List<IngredientDTO>> getAllIngredients();


    @Operation(summary = "Get ingredient", description = "Get one by id", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Ingredients" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))),

            @ApiResponse(responseCode = "401", description = "Access denied"),

            @ApiResponse(responseCode = "406", description = "Request failed - No items") })
    @RequestMapping(value = "/api/ingredients/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<IngredientDTO> getIngredient(@Parameter(in = ParameterIn.PATH, description = "record id", required=true, schema=@Schema()) @PathVariable("id") int id);




}
