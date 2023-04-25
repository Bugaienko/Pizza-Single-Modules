package balu.pizzarest.pizzaproject.controllers.interfases;

import balu.pizzarest.pizzaproject.dto.*;
import balu.pizzarest.pizzaproject.dto.responsesModel.AdminOnlyResponse403;
import balu.pizzarest.pizzaproject.dto.responsesModel.BadDataResponse404;
import balu.pizzarest.pizzaproject.dto.responsesModel.UpdateUserResponse200;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-16T14:31:40.830807727Z[GMT]")
@Validated
public interface AdminControllerInterface {


    @Operation(summary = "Create new Cafe", description = "create new cafe", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CafeDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied"),

            @ApiResponse(responseCode = "404", description = "Request failed - No items")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/api/admin/cafe/add",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<CafeDTO> createCafe(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BodyAddCafe body);


    @Operation(summary = "Add pizza to menu (admin only)", description = "Add pizza to cafes menu", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),

            @ApiResponse(responseCode = "403", description = "Admin only method! Access denied"),

            @ApiResponse(responseCode = "406", description = "Bad request parametrs")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/api/admin/cafe/menu/add/{cafeId}/{pizzaId}")
    ResponseEntity<Map<String, String>> addToMenu(@Parameter(in = ParameterIn.PATH, description = "cafe id", required = true, schema = @Schema()) @PathVariable("cafeId") int cafeId, @Parameter(in = ParameterIn.PATH, description = "pizza id", required = true, schema = @Schema()) @PathVariable("pizzaId") int pizzaId);


    @Operation(summary = "Remove pizza from menu (admin only)", description = "Remove pizza from cafes menu", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),

            @ApiResponse(responseCode = "403", description = "Admin only method! Access denied"),

            @ApiResponse(responseCode = "404", description = "Bad request parametr")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/api/admin/cafe/menu/remove/{cafeId}/{pizzaId}")
    ResponseEntity<Map<String, String>> removeFromMenu(@Parameter(in = ParameterIn.PATH, description = "cafe id", required = true, schema = @Schema()) @PathVariable("cafeId") int cafeId, @Parameter(in = ParameterIn.PATH, description = "pizza id", required = true, schema = @Schema()) @PathVariable("pizzaId") int pizzaId);


    @Operation(summary = "Add ingredient (admin only)", description = "Add ingredient", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied"),

            @ApiResponse(responseCode = "404", description = "Request failed - bad data")})
    @PostMapping(value = "/ingredients/add", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<IngredientDTO> addIngredient(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody BodeAddIngredient body);

// New ================================

    @Operation(summary = "Add ingredients to pizza (Admin only)", description = "Add ingredients", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PizzaDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = BadDataResponse404.class)))})
    @PostMapping(value = "/api/admin/pizza/addIngredients/{pizzaId}", produces = {"application/json", "apllication/json"}, consumes = {"application/json"})
    ResponseEntity<PizzaDTO> addInredient(@Parameter(in = ParameterIn.PATH, description = "pizza id", required = true, schema = @Schema()) @PathVariable("pizzaId") int pizzaId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody List<Integer> body);


    @Operation(summary = "Remove ingredient from pizza (Admin only)", description = "Remove ingredient", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PizzaDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = BadDataResponse404.class)))})
    @PostMapping(value = "/api/admin/pizza/removeIngredient/{pizzaId}/{id}", produces = {"application/json", "application/json"})
    ResponseEntity<PizzaDTO> removeIngredient(@Parameter(in = ParameterIn.PATH, description = "pizza id", required = true, schema = @Schema()) @PathVariable("pizzaId") int pizzaId, @Parameter(in = ParameterIn.PATH, description = "ingredient id", required = true, schema = @Schema()) @PathVariable("id") int id);

    @Operation(summary = "Edit users info (Admin only)", description = "Edit user ifo", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Admin" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserResponse200.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/admin/users/edit/{id}", produces = { "application/json", "apllication/json" }, consumes = { "application/json" })
    ResponseEntity<Map<String, String>> apiAdminUsersEditIdPost(@Parameter(in = ParameterIn.PATH, description = "user id", required=true, schema=@Schema()) @PathVariable("id") int id, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody BodyUserEdit body, BindingResult bindingResult);



    @Operation(summary = "Change base of pizza (Admin only)", description = "Change base", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Admin" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PizzaDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/admin/pizza/changeBase/{pizzaId}/{id}", produces = { "application/json", "apllication/json" })
    ResponseEntity<PizzaDTO> changeBase(@Parameter(in = ParameterIn.PATH, description = "pizza id", required=true, schema=@Schema()) @PathVariable("pizzaId") int pizzaId, @Parameter(in = ParameterIn.PATH, description = "base id", required=true, schema=@Schema()) @PathVariable("id") int id);


    @Operation(summary = "Create new pizza (Admin only)", description = "Create pizza", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Admin" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PizzaDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "apllication/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/admin/pizza/create", produces = { "application/json", "apllication/json" }, consumes = { "application/json" })
    ResponseEntity<PizzaDTO> createPizza(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody BodyAddPizza body, BindingResult bindingResult);


    @Operation(summary = "Add base (Admin only)", description = "Add base", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Admin" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseDTO.class))),

            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/admin/base/add", produces = { "application/json", "application/json" }, consumes = { "application/json" })
    ResponseEntity<BaseDTO> addNewBase(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @RequestBody @Valid BodyBase body, BindingResult bindingResult);


    @Operation(summary = "Change base (Admin only)", description = "Change base", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Admin" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseDTO.class))),

            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdminOnlyResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Incorrect data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @PostMapping(value = "/api/admin/base/change/{id}", produces = { "application/json", "application/json" }, consumes = { "application/json" })
    ResponseEntity<BaseDTO> changeBaseFields(@Parameter(in = ParameterIn.PATH, description = "base id", required=true, schema=@Schema()) @PathVariable("id") int id, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody BodyBase body, BindingResult bindingResult);






}
