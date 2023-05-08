package balu.pizzarest.pizzaproject.controllers.interfases;

import balu.pizzarest.pizzaproject.dto.AuthenticationDTO;
import balu.pizzarest.pizzaproject.dto.PersonDTO;
import balu.pizzarest.pizzaproject.dto.responsesModel.JwtTokenResponse200;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-16T14:31:40.830807727Z[GMT]")
@Validated
public interface AuthControllerInt {
    @Operation(summary = "Authorization method", description = "Get jwt-token", tags={ "Auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful authorization", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenResponse200.class))),

            @ApiResponse(responseCode = "401", description = "Authentication failed") })
    @PostMapping(value = "/api/auth/login",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<Map<String, String>> apiAuthLogin(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody AuthenticationDTO authenticationDTO);

    @Operation(summary = "Registration new user", description = "Get jwt-token", tags={ "Auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succsessful create new user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenResponse200.class))) })
    @PostMapping(value = "/api/auth/signup",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<Map<String, String>> apiAuthSignup(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult);



}
