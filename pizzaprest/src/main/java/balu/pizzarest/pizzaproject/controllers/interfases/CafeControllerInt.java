package balu.pizzarest.pizzaproject.controllers.interfases;

import balu.pizzarest.pizzaproject.dto.CafeDTO;
import balu.pizzarest.pizzaproject.dto.PizzaDTO;
import balu.pizzarest.pizzaproject.dto.responsesModel.AcssessDeneidedResponse403;
import balu.pizzarest.pizzaproject.dto.responsesModel.BadDataResponse404;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-16T14:31:40.830807727Z[GMT]")
@Validated
public interface CafeControllerInt {


    @Operation(summary = "Get all cafes", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Cafe"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CafeDTO.class)))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AcssessDeneidedResponse403.class)))})
    @GetMapping(value = "/api/cafe/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<CafeDTO>> getAllCafes();


    @Operation(summary = "Get cafe", description = "Get one by id", security = {
            @SecurityRequirement(name = "bearerAuth")}, tags = {"Cafe"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CafeDTO.class))),

            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcssessDeneidedResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Request failed - No items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class)))})
    @GetMapping(value = "/api/cafe/{cafeId}",
            produces = {"application/json"})
    ResponseEntity<CafeDTO> getCafe(@Parameter(in = ParameterIn.PATH, description = "cafe id", required = true, schema = @Schema()) @PathVariable("cafeId") int cafeId);






    @Operation(summary = "Get list of Pizzas", description = "Show list of cafe pizzas", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Cafe" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PizzaDTO.class)))),

            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AcssessDeneidedResponse403.class))),

            @ApiResponse(responseCode = "404", description = "Bad request parametr", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadDataResponse404.class))) })
    @GetMapping(value = "/api/cafe/menu/{cafeId}", produces = { "application/json" })
    ResponseEntity<List<PizzaDTO>> getMenu(@Parameter(in = ParameterIn.PATH, description = "cafe id", required=true, schema=@Schema()) @PathVariable("cafeId") int cafeId);


}
