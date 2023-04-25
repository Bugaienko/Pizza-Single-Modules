package balu.pizzarest.pizzaproject.controllers.rest;

import balu.pizzarest.pizzaproject.controllers.interfases.CafeControllerInt;
import balu.pizzarest.pizzaproject.dto.BodyAddCafe;
import balu.pizzarest.pizzaproject.dto.CafeDTO;
import balu.pizzarest.pizzaproject.dto.PizzaDTO;
import balu.pizzarest.pizzaproject.models.Cafe;
import balu.pizzarest.pizzaproject.models.Pizza;
import balu.pizzarest.pizzaproject.services.CafeService;
import balu.pizzarest.pizzaproject.services.PizzaService;
import balu.pizzarest.pizzaproject.util.ErrorResponse;
import balu.pizzarest.pizzaproject.util.NotFoundException;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergii Bugaienko
 */

@RestController
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-15T16:01:23.022+02:00[Europe/Berlin]")
@Validated
@Api(value = "Cafe", description = "Cafes API")
@RequestMapping("/api/cafe")
public class CafeRestController implements CafeControllerInt {

    private final ModelMapper modelMapper;
    private final CafeService cafeService;
    private final PizzaService pizzaService;
    private final Logger logger = LoggerFactory.getLogger(CafeRestController.class);

    public CafeRestController(ModelMapper modelMapper, CafeService cafeService, PizzaService pizzaService) {
        this.modelMapper = modelMapper;
        this.cafeService = cafeService;
        this.pizzaService = pizzaService;
    }


    @Override
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CafeDTO>> getAllCafes() {
        return ResponseEntity.ok(cafeService.findAll().stream().map(this::convertCafeToDTO).collect(Collectors.toList()));
    }

    @Override
    @GetMapping(value = "/{cafeId}", produces = {"application/json"})
    public ResponseEntity<CafeDTO> getCafe(@Parameter(in = ParameterIn.PATH, description = "cafe id", required = true, schema = @Schema()) @PathVariable("cafeId") int cafeId) {
        return ResponseEntity.ok(convertCafeToDTO(cafeService.findById(cafeId)));
    }


    @Override
    @GetMapping(value = "/menu/{cafeId}", produces = { "application/json" })
    public ResponseEntity<List<PizzaDTO>> getMenu(int cafeId) {
        Cafe cafe = cafeService.findById(cafeId);
        return ResponseEntity.ok(pizzaService.findByCafes(cafe).stream().map(this::convertPizzaToDto).collect(Collectors.toList()));
    }


    private CafeDTO convertCafeToDTO(Cafe cafe) {
        return modelMapper.map(cafe, CafeDTO.class);
    }

    private Cafe convertToCafeFromBody(BodyAddCafe addBody) {
        return modelMapper.map(addBody, Cafe.class);
    }

    private PizzaDTO convertPizzaToDto(Pizza pizza){
        return modelMapper.map(pizza, PizzaDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Object with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
