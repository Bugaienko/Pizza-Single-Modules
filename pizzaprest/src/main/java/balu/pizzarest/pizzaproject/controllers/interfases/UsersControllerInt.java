package balu.pizzarest.pizzaproject.controllers.interfases;

import balu.pizzarest.pizzaproject.dto.BodyUserEdit;
import balu.pizzarest.pizzaproject.dto.PersonDTO;
import balu.pizzarest.pizzaproject.dto.PizzaDTO;
import balu.pizzarest.pizzaproject.dto.responsesModel.*;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-16T14:31:40.830807727Z[GMT]")
@Validated
public interface UsersControllerInt {


    @Operation(summary = "Get all users", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied") })
    @GetMapping(value = "/api/users/all",
            produces = { "application/json" })
    ResponseEntity<List<PersonDTO>> getAllUsers();



    @Operation(summary = "Get list of Pizzas", description = "Show list of favorite pizzas", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PizzaDTO.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied"),

            @ApiResponse(responseCode = "404", description = "Bad request parametr") })
    @RequestMapping(value = "/api/users/favorites/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<PizzaDTO>> getFavorites(@Parameter(in = ParameterIn.PATH, description = "record id", required=true, schema=@Schema()) @PathVariable("id") int id);



    @Operation(summary = "Get user", description = "Get one by id", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))),

            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Request failed - No items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @GetMapping(value = "/api/users/{id}",
            produces = { "application/json" })
    ResponseEntity<PersonDTO> getUser(@Parameter(in = ParameterIn.PATH, description = "record id", required=true, schema=@Schema()) @PathVariable("id") int id);


    @Operation(summary = "Change users info", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UpdateUserResponse200.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied"),

            @ApiResponse(responseCode = "404", description = "Invalid data") })
    @RequestMapping(value = "/api/users/edit",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Map<String, String>> updateUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema(implementation = BodyUserEdit.class)) @Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult);



    @Operation(summary = "Add pizza to users Favorites list", description = "Add pizza", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse2002.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Request failed - No items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/users/addToFav/{id}/{pizzaId}", produces = { "application/json" })
    ResponseEntity<InlineResponse2002> addToFav(@Parameter(in = ParameterIn.PATH, description = "user id", required=true, schema=@Schema()) @PathVariable("id") int id, @Parameter(in = ParameterIn.PATH, description = "pizza id", required=true, schema=@Schema()) @PathVariable("pizzaId") int pizzaId);





    @Operation(summary = "Remove pizza from users Favorites list", description = "Remove pizza", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse2003.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Request failed - No items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/users/removeFromFav/{id}/{pizzaId}", produces = { "application/json" })
    ResponseEntity<InlineResponse2003> removeFromFavList(@Parameter(in = ParameterIn.PATH, description = "user id", required=true, schema=@Schema()) @PathVariable("id") int id, @Parameter(in = ParameterIn.PATH, description = "pizza id", required=true, schema=@Schema()) @PathVariable("pizzaId") int pizzaId);



}
