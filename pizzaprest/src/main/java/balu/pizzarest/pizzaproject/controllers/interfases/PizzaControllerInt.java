package balu.pizzarest.pizzaproject.controllers.interfases;

import balu.pizzarest.pizzaproject.dto.PizzaDTO;
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

import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-16T14:31:40.830807727Z[GMT]")
@Validated
public interface PizzaControllerInt {



    @Operation(summary = "Get all users", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Pizza" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PizzaDTO.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied") })
    @GetMapping(value = "/all",
            produces = { "application/json" })
    ResponseEntity<List<PizzaDTO>> getAllPizzas();

    @Operation(summary = "Get pizza", description = "Get one by id", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Pizza" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PizzaDTO.class))),

            @ApiResponse(responseCode = "401", description = "Access denied"),

            @ApiResponse(responseCode = "404", description = "Request failed - No items") })
    @GetMapping (value = "/{id}",
            produces = { "application/json" })
    ResponseEntity<PizzaDTO> getPizza(@Parameter(in = ParameterIn.PATH, description = "record id", required=true, schema=@Schema()) @PathVariable("id") int id);





}
